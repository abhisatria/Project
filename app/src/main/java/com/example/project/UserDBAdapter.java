package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.project.Model.User;

public class UserDBAdapter extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME = "kostDB.db";
    private static final String TABLE_USERS = "users";

    private static final String COLUMN_ID = "userID";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PHONE_NUMBER = "phoneNumber";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_DoB = "dateOfBirth";

    SQLiteDatabase db;

    public UserDBAdapter(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS
                + "("+ COLUMN_ID+ " TEXT NOT NULL PRIMARY KEY,"
                + COLUMN_USERNAME + " TEXT NOT NULL,"
                + COLUMN_PASSWORD + " TEXT NOT NULL,"
                + COLUMN_PHONE_NUMBER + " TEXT NOT NULL,"
                + COLUMN_GENDER + " TEXT NOT NULL,"
                + COLUMN_DoB + " TEXT NOT NULL" +")";

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_USERS);
        onCreate(db);
        Log.w("UserDBAdapter", "Upgrading database from version " + oldVersion + " to "+ newVersion + ". which will destroy all old data");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db = this.getWritableDatabase();
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    public void insertUser(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID,user.getUserID());
        contentValues.put(COLUMN_USERNAME,user.getUsername());
        contentValues.put(COLUMN_PASSWORD,user.getPassword());
        contentValues.put(COLUMN_GENDER,user.getGender());
        contentValues.put(COLUMN_PHONE_NUMBER,user.getPhoneNumber());
        contentValues.put(COLUMN_DoB,user.getDateOfBirth());

        db.insert(TABLE_USERS,null,contentValues);
    }

    public User getUser(String userID)
    {
        String query = "SELECT * FROM "+ TABLE_USERS + " WHERE " + COLUMN_ID + " = \"" + userID + "\"";
        Cursor cursor = db.rawQuery(query,null);
        User user = new User();
        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            user.setUserID(cursor.getString(0));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setGender(cursor.getString(3));
            user.setPhoneNumber(cursor.getString(4));
            user.setDateOfBirth(cursor.getString(5));
            cursor.close();
        }
        else{
            user = null;
        }
        return user;
    }

    public Cursor getAllUsers(){
        Cursor cursor = db.query(TABLE_USERS,new String[]{COLUMN_ID,COLUMN_USERNAME,COLUMN_PASSWORD,COLUMN_GENDER,COLUMN_PHONE_NUMBER,COLUMN_DoB},null,null,null,null,null);
        return cursor;
    }

}
