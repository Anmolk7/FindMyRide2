package com.example.anmol.findmyride;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anmol on 2/22/2017.
 */
public class Databasehelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contacts.db";
    private static final String TABLE_NAME = "contacts";
    private static final String coTable = "cordinates";
    private static final String pcoTable = "pCordinates";
    private static final String TABLE_ACCOUNTS="accounts";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_CORDINATES="co";
    private static final String COLUMN_PCORDINATES="perdictCo";
    private static final String COLUMN_BALANCE="balance";

    float balance=0;

    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table contacts (phone text not null);";
    private static final String Table="create table cordinates (co text not null);";
    private static final String pTable="create table pCordinates (perdictCo text not null);";
    private static final String AccTable="create table accounts (balance text not null);";
    //private static final String TABLE_CREATE="create table contacts (phone text not null,"+
      //      "co text not null);";
    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, 13);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(Table);
        db.execSQL(pTable);
        db.execSQL(AccTable);
        this.db = db;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        String query1="DROP TABLE IF EXISTS " + coTable;
        String query2="DROP TABLE IF EXISTS "+ TABLE_ACCOUNTS;
        String query3="DROP TABLE IF EXISTS "+ pcoTable;
        db.execSQL(query);
        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
        this.onCreate(db);
    }

    //  Save s= new Save();
    public void insertPhone(String phone) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHONE, phone);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void insertBalance(float balance)
    {
        db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BALANCE, balance);
        db.insert(TABLE_ACCOUNTS,null,values);
        db.close();
    }
    public void insertCordinates(String co)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CORDINATES, co);
        db.insert(coTable, null, values);
        db.close();
    }
    public void insertPCordinates(String perdictCo)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PCORDINATES, perdictCo);
        db.insert(pcoTable, null, values);
        db.close();
    }

    public String display()
    {
        String phone = "";
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("select phone from " + TABLE_NAME, null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            if ((res.getString(res.getColumnIndex("phone")) != null))
            {
                phone = res.getString(res.getColumnIndex("phone"));
            }
            res.moveToNext();
        }
        db.close();
        return phone;
    }
   public String cordinates()
    {
        String cordinates = "";
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("select co from " + coTable, null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            if ((res.getString(res.getColumnIndex("co")) != null))
            {
                cordinates = res.getString(res.getColumnIndex("co"));
            }
            res.moveToNext();
        }
        db.close();
        return cordinates;
    }
    public String pCordinates()
    {
        String pcordinates = "";
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("select perdictCo from " + pcoTable, null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            if ((res.getString(res.getColumnIndex("perdictCo")) != null))
            {
                pcordinates = res.getString(res.getColumnIndex("perdictCo"));
            }
            res.moveToNext();
        }
        db.close();
        return pcordinates;
    }
    public float getBalance()
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("select balance from "+TABLE_ACCOUNTS , null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            if ((res.getString(res.getColumnIndex("balance")) != null))
            {
                balance =res.getFloat(res.getColumnIndex("balance"));
            }
            res.moveToNext();
        }
        db.close();
        return balance;


//        Cursor cursor=this.getWritableDatabase().rawQuery("select * from accounts",null);
//        while(cursor.moveToNext())
//        {
//            balance=Float.parseFloat(cursor.getString(1));
//        }
//        return balance;
    }

    public void updateBalance(float newBalance, float balance)
    {
//        db=this.getWritableDatabase();
//        ContentValues values=new ContentValues();
//        values.put(COLUMN_BALANCE,newBalance);
//        db.update(TABLE_ACCOUNTS,values, "balance = ?", new String[] { String.valueOf(balance) });
//        return true;
        this.getWritableDatabase().execSQL("update accounts set balance='"+newBalance+"'where balance='"+balance+"'");

    }
}