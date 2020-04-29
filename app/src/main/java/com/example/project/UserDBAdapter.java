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

import com.example.project.Model.Booking;
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

    private static final String TABLE_BOOKS = "bookingTransaction";

    private static final String COLUMN_BOOK_ID = "bookingID";
    private static final String COLUMN_BOOKING_ID = "id";
    private static final String COLUMN_NAME = "kosName";
    private static final String COLUMN_FACILITY = "kosFacility";
    private static final String COLUMN_PRICE = "kosPrice";
    private static final String COLUMN_DESCRIPTION = "kosDesc";
    private static final String COLUMN_LONGITUDE = "kosLongitude";
    private static final String COLUMN_LATITUDE = "kosLatitude";
    private static final String COLUMN_DATE = "DATE";
    private static final String COLUMN_IMAGE = "image";







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
                + COLUMN_DoB + " TEXT NOT NULL)";

        String CREATE_BOOKING_TABLE = "CREATE TABLE " + TABLE_BOOKS
                + "("+ COLUMN_BOOKING_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BOOK_ID + " TEXT NOT NULL,"
                + COLUMN_ID + " TEXT NOT NULL,"
                + COLUMN_NAME + " TEXT NOT NULL,"
                + COLUMN_FACILITY + " TEXT NOT NULL,"
                + COLUMN_PRICE + " TEXT NOT NULL,"
                + COLUMN_DESCRIPTION + " TEXT NOT NULL,"
                + COLUMN_LONGITUDE + " TEXT NOT NULL,"
                + COLUMN_LATITUDE + " TEXT NOT NULL,"
                + COLUMN_DATE + " TEXT NOT NULL,"
                + COLUMN_IMAGE + " TEXT NOT NULL)";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_BOOKING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_BOOKS);
        onCreate(db);
        Log.w("UserDBAdapter", "Upgrading database from version " + oldVersion + " to "+ newVersion + ". which will destroy all old data");
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

    public void insertBookingTransaction (Booking booking){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BOOK_ID,booking.getBookingID());
        contentValues.put(COLUMN_ID,booking.getUserID());
        contentValues.put(COLUMN_NAME,booking.getKosName());
        contentValues.put(COLUMN_FACILITY,booking.getKosFacility());
        contentValues.put(COLUMN_PRICE,booking.getKosPrice());
        contentValues.put(COLUMN_DESCRIPTION,booking.getKosDescription());
        contentValues.put(COLUMN_LONGITUDE,booking.getKosLongitude());
        contentValues.put(COLUMN_LATITUDE,booking.getKosLatitude());
        contentValues.put(COLUMN_DATE,booking.getBookingDate());
        contentValues.put(COLUMN_IMAGE,booking.getGambarKos());

        db.insert(TABLE_BOOKS,null,contentValues);
    }
    public int getLastCount(){
        String query = "SELECT * FROM " + TABLE_BOOKS;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor==null)
        {
            return 0;
        }
        else
        {
            if(cursor.moveToLast())
            return cursor.getInt(0);
        }
        return 0;
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
    public long getBookingCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db,TABLE_BOOKS);
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
    public List<Booking> allBookings(){
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_BOOKS;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            do{
                Booking booking = new Booking();
                booking.setBookingID(cursor.getString(1));
                booking.setUserID(cursor.getString(2));
                booking.setKosName(cursor.getString(3));
                booking.setKosFacility(cursor.getString(4));
                booking.setKosPrice(cursor.getString(5));
                booking.setKosDescription(cursor.getString(6));
                booking.setKosLongitude(cursor.getString(7));
                booking.setKosLatitude(cursor.getString(8));
                booking.setBookingDate(cursor.getString(9));
                booking.setGambarKos(cursor.getString(10));
                bookings.add(booking);
            }while(cursor.moveToNext());
        }
        db.close();
        return bookings;
    }

    public boolean deleteBooking(String bookingID){
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_BOOKS + " WHERE " + COLUMN_BOOK_ID + " = \"" + bookingID + "\"";
        Cursor cursor = db.rawQuery(query,null);
        Booking booking = new Booking();
        if(cursor.moveToFirst())
        {
            booking.setBookingID(cursor.getString(0));
            db.delete(TABLE_BOOKS,COLUMN_BOOK_ID + " = ?",new String[]{booking.getBookingID()});
            cursor.close();
            result = true;
        }

        return result;
    }

}
