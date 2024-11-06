package com.android.luxevista.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.luxevista.Explore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExploreDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ExploreDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Explore";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String TYPE = "type";
    private static final String DESCRIPTION = "description";
    private static final String DURATION = "duration";
    private static final String PRICE = "price";
    private static final String BOOKING_DETAILS = "bookingDetails";
    private static final String SPECIAL_NOTE = "specialNote";
    private static final String COVER_IMAGE = "coverImage";
    private static final String ADDITIONAL_IMAGES = "additionalImages";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TITLE + " TEXT NOT NULL, " +
                    TYPE + " TEXT, " +
                    DESCRIPTION + " TEXT, " +
                    DURATION + " TEXT, " +
                    PRICE + " REAL, " +
                    BOOKING_DETAILS + " TEXT, " +
                    SPECIAL_NOTE + " TEXT, " +
                    COVER_IMAGE + " TEXT, " +
                    ADDITIONAL_IMAGES + " TEXT" +
                    ");";

    public ExploreDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        insertDummyData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Long insertExplore(Explore explore) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, explore.getTitle());
        values.put(TYPE, explore.getType());
        values.put(DESCRIPTION, explore.getDescription());
        values.put(DURATION, explore.getDuration());
        values.put(PRICE, explore.getPrice());
        values.put(BOOKING_DETAILS, explore.getBookingDetails());
        values.put(SPECIAL_NOTE, explore.getSpecialNote());
        values.put(COVER_IMAGE, explore.getCoverImage());
        values.put(ADDITIONAL_IMAGES, explore.getAdditionalImages().toString());

        Long result = -1L;
        try {
            result = db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return result;
    }

    public Explore getExploreById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Explore explore = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            List<String> additionalImages = new ArrayList<>(Arrays.asList(cursor.getString(cursor.getColumnIndexOrThrow(ADDITIONAL_IMAGES)).split(",")));
            explore = new Explore(
                    cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TYPE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DURATION)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(PRICE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(BOOKING_DETAILS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(SPECIAL_NOTE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COVER_IMAGE)),
                    additionalImages
            );
        }

        cursor.close();
        db.close();
        return explore;
    }

    public List<Explore> getAllExplores() {
        List<Explore> exploreList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                List<String> additionalImages = new ArrayList<>(Arrays.asList(cursor.getString(cursor.getColumnIndexOrThrow(ADDITIONAL_IMAGES)).split(",")));

                Explore explore = new Explore(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DURATION)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BOOKING_DETAILS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(SPECIAL_NOTE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COVER_IMAGE)),
                        additionalImages
                );

                exploreList.add(explore);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return exploreList;
    }
    public int editeExploreById(int id, Explore explore){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TITLE, explore.getTitle());
        values.put(TYPE, explore.getType());
        values.put(DESCRIPTION, explore.getDescription());
        values.put(DURATION, explore.getDuration());
        values.put(PRICE, explore.getPrice());
        values.put(BOOKING_DETAILS, explore.getBookingDetails());
        values.put(SPECIAL_NOTE, explore.getSpecialNote());
        values.put(COVER_IMAGE, explore.getCoverImage());
        values.put(ADDITIONAL_IMAGES, explore.getAdditionalImages().toString());
        int rowsAffected = db.update(TABLE_NAME, values, ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    private void insertDummyData(SQLiteDatabase db){
        ContentValues values = new ContentValues();

        values.put(TITLE, "Local Attractions");
        values.put(TYPE, "Historic Coastal Village Tour");
        values.put(DESCRIPTION, "Explore the charm of the nearby coastal village with guided tours of historical landmarks, local artisan shops, and scenic viewpoints.");
        values.put(DURATION, 2);
        values.put(PRICE, 60);
        values.put(BOOKING_DETAILS, "Book your tour through the app to reserve your spot. Select your preferred tour time and group size.");
        values.put(SPECIAL_NOTE, "Cancellations must be made 24 hours in advance for a full refund.");
        values.put(COVER_IMAGE, "drawable/ocean_view_room");
        values.put(ADDITIONAL_IMAGES, "drawable/local_explore");

        db.insert(TABLE_NAME, null, values);

        values.clear();
        values.put(TITLE, "Water Activities");
        values.put(TYPE, "Sunset Catamaran Cruise");
        values.put(DESCRIPTION, "Set sail on a luxury catamaran to enjoy the breathtaking sunset views, complete with refreshments and live music on board.");
        values.put(DURATION, 1.5);
        values.put(PRICE, 80);
        values.put(BOOKING_DETAILS, "Reserve your cruise time through the app. Early booking is recommended as spaces are limited.");
        values.put(SPECIAL_NOTE, "Cancellations must be made 24 hours in advance for a full refund.");
        values.put(COVER_IMAGE, "drawable/water_explore");
        values.put(ADDITIONAL_IMAGES, "drawable/water_explore");

        db.insert(TABLE_NAME, null, values);

        values.clear();
        values.put(TITLE, "Nightlife Experiences");
        values.put(TYPE, "Beachfront Bonfire Party");
        values.put(DESCRIPTION, "Join an exclusive beachfront bonfire with music, drinks, and dance under the stars—a perfect way to unwind and mingle.");
        values.put(DURATION, 4);
        values.put(PRICE, 50);
        values.put(BOOKING_DETAILS, "Secure your spot via the app. Early booking is advised due to limited space.");
        values.put(SPECIAL_NOTE, "Cancellations must be made 12 hours in advance for a full refund.");
        values.put(COVER_IMAGE, "drawable/nightlife_explore");
        values.put(ADDITIONAL_IMAGES, "drawable/nightlife_explore");

        db.insert(TABLE_NAME, null, values);


    }
}
