package thegenuinegourav.moneyorder;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDatabase.db";
    public static final String TRANS_TABLE_NAME = "transact";
    public static final String TRANS_COLUMN_SENDER = "sender";
    public static final String TRANS_COLUMN_RECEIVER = "receiver";
    public static final String TRANS_COLUMN_AMOUNT = "amount";

    public static final String WALLET_TABLE_NAME = "wallet";
    public static final String WALLET_COLUMN_NUMBER = "number";
    public static final String WALLET_COLUMN_CASH = "cash";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table transact " +
                        "(id integer primary key, sender text,receiver text,amount text)"
        );
        db.execSQL(
                "create table wallet " +
                        "(id integer primary key, number text,cash text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS transact");
        db.execSQL("DROP TABLE IF EXISTS wallet");
        onCreate(db);
    }

    public boolean insertContact (String sender, String receiver, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sender", sender);
        contentValues.put("receiver", receiver);
        contentValues.put("amount", amount);
        db.insert("transact", null, contentValues);
        return true;
    }

    public boolean insertWallet (String number, String cash) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("number", number);
        contentValues.put("cash", cash);
        db.insert("wallet", null, contentValues);
        return true;
    }

    public Cursor getTransData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from transact where id="+id+"", null );
        return res;
    }

    public Cursor getWalletData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from wallet where id="+id+"", null );
        return res;
    }

    public int getWalletId(String  number) throws SQLException
    {
        SQLiteDatabase db = this.getReadableDatabase();
        long recc=0;
        String rec=null;
        Cursor mCursor = db.rawQuery(
                "SELECT id  FROM  wallet WHERE number= '"+number+"'" , null);
        if (mCursor != null)
        {
            mCursor.moveToFirst();
            recc=mCursor.getLong(0);
            rec=String.valueOf(recc);
        }
        return Integer.parseInt(rec);
    }

    public boolean isPhonePresent(String phone) {
        ArrayList arrayList = getAllWallets();
        for(int i=0;i<arrayList.size();i+=2)
            if(arrayList.get(i).equals(phone))
                return true;

        return false;
    }


    public boolean updateWallet (Integer id, String number, String cash) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("number", number);
        contentValues.put("cash", cash);
        db.update("wallet", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteTrans (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("transact",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public Integer deleteWallet (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("wallet",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllTransactions() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from transact", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(TRANS_COLUMN_SENDER)));
            array_list.add(res.getString(res.getColumnIndex(TRANS_COLUMN_RECEIVER)));
            array_list.add(res.getString(res.getColumnIndex(TRANS_COLUMN_AMOUNT)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllWallets() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from wallet", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(WALLET_COLUMN_NUMBER)));
            array_list.add(res.getString(res.getColumnIndex(WALLET_COLUMN_CASH)));
            res.moveToNext();
        }
        return array_list;
    }




}