package com.android.luxevista.database;

import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.luxevista.User;

public class UserDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "User";
    private static final String USER_ID = "id";
    private static final String USER_NAME = "name";
    private static final String USER_USERNAME = "username";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_PHONE = "phone";
    private static final String USER_ADDRESS = "address";
    private static final String USER_CITY = "city";
    private static final String USER_POSTALCODE = "postalCode";
    private static final String USER_COUNTY = "county";
    private static final String USER_LOGINSTATUS = "loginStatus";
    private static final String USER_IMAGEURL = "imageUrl";
    private static final String USER_GENDER = "gender";
    private static final String USER_BIRTHDAY = "birthday";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    USER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_NAME +" TEXT NOT NULL, " +
                    USER_USERNAME +" TEXT UNIQUE NOT NULL, " +
                    USER_EMAIL + " TEXT UNIQUE NOT NULL, " +
                    USER_PASSWORD + " TEXT NOT NULL, " +
                    USER_PHONE + " TEXT, " +
                    USER_ADDRESS + " TEXT, " +
                    USER_CITY + " TEXT, " +
                    USER_POSTALCODE + " TEXT, " +
                    USER_COUNTY + " TEXT, " +
                    USER_LOGINSTATUS + " BOOLEAN DEFAULT 0, " +
                    USER_IMAGEURL + " TEXT, " +
                    USER_GENDER +" TEXT," +
                    USER_BIRTHDAY + " TEXT" +
                    ");";

    public UserDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Long insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, user.getName());
        values.put(USER_USERNAME, user.getUsername());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_PASSWORD, user.getPassword());
        Long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }
    public int editUserById(int id, User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_NAME, user.getName());
        values.put(USER_USERNAME, user.getUsername());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_PHONE, user.getPhone());
        values.put(USER_ADDRESS, user.getAddress());
        values.put(USER_CITY, user.getCity());
        values.put(USER_POSTALCODE, user.getPostalCode());
        values.put(USER_COUNTY, user.getCounty());
        values.put(USER_LOGINSTATUS, user.getLoginStatus());
        values.put(USER_IMAGEURL, user.getImageUrl());
        values.put(USER_GENDER, user.getGender());
        values.put(USER_BIRTHDAY, user.getBirthDay());

        int rowsAffected = db.update(TABLE_NAME, values, USER_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }
    public int userLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        int id = 0;
        String query = "SELECT * FROM User WHERE Email = ? AND Password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});
        if (cursor.moveToFirst()){
            id = cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID));
        }
        cursor.close();
        db.close();
        return id;
    }

    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String query = "SELECT * FROM User WHERE " + USER_ID +" = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_PASSWORD)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_ADDRESS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_CITY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_POSTALCODE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_COUNTY)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(USER_LOGINSTATUS)) == 1,
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_IMAGEURL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_GENDER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_BIRTHDAY))
            );
        }

        cursor.close();
        db.close();
        return user;
    }

}
