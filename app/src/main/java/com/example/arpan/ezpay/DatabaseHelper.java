package com.example.arpan.ezpay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dbEzPay.db";
    public static final String TAG = "DatabaseHelper";
    public static final String TBLUser = "tblUser";
    public static final String ID = "ID";
    public static final String FirstName = "FirstName";
    public static final String LastName = "LastName";
    public static final String Email = "Email";
    public static final String Password = "Password";
    public static final String RememberMe = "RememberMe";

    public static final String TBLPaymentType = "tblPaymentType";
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE TABLE IF NOT EXISTS " + TBLUser + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FirstName TEXT, LastName TEXT, Email TEXT, Password TEXT)");
        String createTable = "CREATE TABLE " + TBLUser + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+ FirstName +" TEXT, "+ LastName +" TEXT, "+ Email +" TEXT, "+ Password +" TEXT, "+ RememberMe +" TEXT)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TBLUser); //Drop older table if exists
        onCreate(db);
    }


    public boolean addData(String fname, String lname, String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FirstName, fname);
        contentValues.put(LastName, lname);
        contentValues.put(Email, email);
        contentValues.put(Password, pass);
        contentValues.put(RememberMe, "No");

        Log.d(TAG, "addData: Adding " + fname +", "+ lname+","+ email+" and"+pass+ " to " + TBLUser);

        long result = db.insert(TBLUser, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateRememberMe(String email, String pass, String rememberMe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(FirstName, fname);
        //contentValues.put(LastName, lname);
        //contentValues.put(Email, email);
        //contentValues.put(Password, pass);
        contentValues.put(RememberMe, rememberMe);

        Log.d(TAG, "updateData: Updating "+rememberMe+ " to " + TBLUser);

        long result = db.update(TBLUser,contentValues,DatabaseHelper.Email + "=? AND " + DatabaseHelper.Password + "=?", new String[]{email, pass});

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TBLUser;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

}