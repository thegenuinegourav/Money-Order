package thegenuinegourav.moneyorder;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import static thegenuinegourav.moneyorder.DBHelper.WALLET_COLUMN_CASH;

public class IncomingSms extends BroadcastReceiver {

    private DBHelper mydb ;
    private String SENT_SMS_FLAG = "message";

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {


        SmsManager smsManager = SmsManager.getDefault();
        Intent in = new Intent(SENT_SMS_FLAG);
        PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0,
                in, 0);
        if(MainActivity.getAppContext() == null)
        {
            PackageManager pm = context.getPackageManager();
            Intent launchIntent = pm.getLaunchIntentForPackage("thegenuinegourav.moneyorder");
            context.startActivity(launchIntent);
        }
        else
        {
            MainActivity.getAppContext().registerReceiver(
                    new MessageSentListener(),
                    new IntentFilter(SENT_SMS_FLAG));

        }


        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        mydb = new DBHelper(context);

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum;
                    if(phoneNumber.substring(0,3).equals("+91"))
                        senderNum = phoneNumber.substring(3);
                    else
                        senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    String[] stringArray = message.split("\\s+");
                    String receiverNum = null,amount = null;
                    if(stringArray[0].equals("MO") || stringArray[0].equals("mo") || stringArray[0].equals("Mo"))
                    {
                        if(stringArray[1]!=null) receiverNum = stringArray[1];
                        if(stringArray[2]!=null) amount = stringArray[2];

                        if(!mydb.isPhonePresent(senderNum))
                        {
                            if(mydb.insertWallet(senderNum,String.valueOf(1000))){
                                smsManager.sendTextMessage(senderNum, null,
                                        "Thanks for using MoneyOrder! " +
                                                "Your wallet is credited with 1000 rupees", sentIntent, null);
                            }else{
                                smsManager.sendTextMessage(senderNum, null,
                                        "There is a problem creating your account on " +
                                                "MoneyOrder! Try again later.", sentIntent, null);
                            }
                        }

                        int cashofsender=0,cashofrec=0,sender_id,rec_id;
                        sender_id = mydb.getWalletId(senderNum);
                        Cursor sender = mydb.getWalletData(sender_id);
                        if (sender.moveToFirst())
                            cashofsender = Integer.parseInt(sender.getString(sender.getColumnIndex(WALLET_COLUMN_CASH)));
                        sender.close();
                        if(Integer.parseInt(amount) < cashofsender) {
                            //insert Into Transaction Database
                            if (mydb.insertContact(senderNum, receiverNum, amount)) {
                                Toast.makeText(context, "Transaction made successfully",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Transaction was unsuccessful",
                                        Toast.LENGTH_SHORT).show();
                            }

                            int new_amount = cashofsender - Integer.parseInt(amount);
                            if (mydb.updateWallet(sender_id, senderNum,
                                    String.valueOf(new_amount))) {
                                smsManager.sendTextMessage(senderNum, null,
                                        "Transaction made successfully! " +
                                                "Your account is debited with " + amount + " rupees. Send to "+ receiverNum + ". Your Current Balance: "
                                                + String.valueOf(new_amount), sentIntent, null);
                            } else {
                                smsManager.sendTextMessage(senderNum, null,
                                        "Transaction was unsuccessful. " +
                                                "Try again later. Current Balance: " + cashofsender, sentIntent, null);
                            }

                            if (!mydb.isPhonePresent(receiverNum)) {
                                if (mydb.insertWallet(receiverNum, String.valueOf(1000))) {
                                    smsManager.sendTextMessage(receiverNum, null,
                                            "Thanks for using MoneyOrder! " +
                                                    "Your wallet is credited with 1000 rupees", sentIntent, null);
                                } else {
                                    smsManager.sendTextMessage(receiverNum, null,
                                            "There is a problem creating your account on " +
                                                    "MoneyOrder! Try again later.", sentIntent, null);
                                }

                            }

                            rec_id = mydb.getWalletId(receiverNum);
                            Cursor rec = mydb.getWalletData(rec_id);
                            if (rec.moveToFirst())
                                cashofrec = Integer.parseInt(rec.getString(rec.getColumnIndex(WALLET_COLUMN_CASH)));
                            rec.close();

                            new_amount = cashofrec + Integer.parseInt(amount);
                            if (mydb.updateWallet(rec_id, receiverNum,
                                    String.valueOf(new_amount))) {
                                smsManager.sendTextMessage(receiverNum, null,
                                        "Transaction made successfully! " +
                                                "Your account is credited with " + amount + " rupees. Send by " + senderNum + ". Current Balance: "
                                                + String.valueOf(new_amount), sentIntent, null);
                            } else {
                                smsManager.sendTextMessage(receiverNum, null,
                                        "Transaction was unsuccessful. " +
                                                "Try again later. Current Balance: "+ cashofrec, sentIntent, null);
                            }
                        }
                        else
                        {
                            smsManager.sendTextMessage(senderNum, null,
                                    "Transaction was unsuccessful. " +
                                            "You dont have enough cash to complete this transaction. Current Balance: " +
                                            cashofsender, sentIntent, null);
                        }

                    }
                    }

                } // end for loop
             // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }

        context.sendBroadcast(new Intent("UPDATE_UI"));
    }

}