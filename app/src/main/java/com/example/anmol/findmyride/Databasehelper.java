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
    private static final String COLUMN_PHONE = "phone";
    //  private static final String COLUMN_NAME="name";
    // private static final String COLUMN_UNAME="uname";
    //private static final String COLUMN_EMAIL="email";
    //private static final String COLUMN_PASSWORD="password";
    // private static final String COULMN_SEATS="seat";
    SQLiteDatabase db;
    private static final String TABLE_CREATE = "create table contacts (phone text not null);";

    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
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

    public String display()

    {

        String phone = "";
        SQLiteDatabase db = getWritableDatabase();
        // db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select phone from " + TABLE_NAME, null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            if ((res.getString(res.getColumnIndex("phone")) != null))
            {
                phone = res.getString(res.getColumnIndex("phone"));
                //   res.moveToNext();

                // return phone;
            }
            res.moveToNext();
        }
        db.close();
        return phone;
    }
}