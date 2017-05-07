package thegenuinegourav.moneyorder;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MessageSentListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int resultCode = this.getResultCode();
        boolean successfullySent = resultCode == Activity.RESULT_OK;
        //That boolean up there indicates the status of the message
        MainActivity.getAppContext().unregisterReceiver(this);
        //Notice how I get the app context again here and unregister this broadcast
        //receiver to clear it from the system since it won't be used again
    }
}
