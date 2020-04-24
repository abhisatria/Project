package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.project.Model.User;
import com.example.project.Storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

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

    //    "Create Table users(userid TEXT NOT NULL PRIMARY KEY,username"
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS
                + "("+ COLUMN_ID+ " TEXT NOT NULL,"
                + COLUMN_USERNAME + " TEXT NOT NULL,"
                + COLUMN_PASSWORD + " TEXT NOT NULL,"
                + COLUMN_PHONE_NUMBER + " TEXT NOT NULL,"
                + COLUMN_GENDER + " TEXT NOT NULL,"
                + COLUMN_DoB + " TEXT NOT NULL)";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_USERS);
//        onCreate(db);
//        Log.w("UserDBAdapter", "Upgrading database from version " + oldVersion + " to "+ newVersion + ". which will destroy all old data");
    }

    public void onOpen(){
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

    public long getUsersCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db,TABLE_USERS);
        db.close();
        return count;
    }

    public boolean isUsernameExist(String username)
    {
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?";
        String[] whereArgs = {username};

        db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);
        db.close();

        int count = cursor.getCount();

        cursor.close();

        return count >= 1;
    }

    public List<User> allUsers(){
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_USERS;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            do{
                User user = new User();
                user.setUserID(cursor.getString(0));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setGender(cursor.getString(3));
                user.setPhoneNumber(cursor.getString(4));
                user.setDateOfBirth(cursor.getString(5));
                users.add(user);
            }while(cursor.moveToNext());
        }
        db.close();
        return users;
    }

}
