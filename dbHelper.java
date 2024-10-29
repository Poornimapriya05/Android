package com.example.dairy;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        // Create tables for user details and login credentials
        DB.execSQL("create Table Userdetails(name TEXT primary key, contact TEXT, dob TEXT)");
        DB.execSQL("create Table UserCredentials(username TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        // Drop old tables and create them again
        DB.execSQL("drop Table if exists Userdetails");
        DB.execSQL("drop Table if exists UserCredentials");
    }

    // Method to insert user data (for registration)
    public Boolean insertuserdata(String name, String contact, String dob) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);
        long result = DB.insert("Userdetails", null, contentValues);
        return result != -1;
    }

    // Method to insert login credentials (for registration)
    public Boolean insertcredentials(String username, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = DB.insert("UserCredentials", null, contentValues);
        return result != -1;
    }

    // Method to check if username and password are valid (for login)
    public Boolean checkcredentials(String username, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from UserCredentials where username = ? and password = ?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

    // Other methods for updating, deleting, and retrieving user data (as you already have)
    public Boolean updateuserdata(String name, String contact, String dob) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);
        @SuppressLint("Recycle") Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.update("Userdetails", contentValues, "name=?", new String[]{name});
            return result != -1;
        } else {
            return false;
        }
    }

    public Boolean deletedata(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Userdetails", "name=?", new String[]{name});
            return result != -1;
        } else {
            return false;
        }
    }

    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from Userdetails", null);
    }
}
