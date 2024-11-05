package com.android.luxevista.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.luxevista.Explore;
import com.google.gson.Gson;

import java.util.ArrayList;
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

        if (explore.getAdditionalImages() != null) {
            String jsonImages = new Gson().toJson(explore.getAdditionalImages());
            values.put(ADDITIONAL_IMAGES, jsonImages);
        } else {
            values.put(ADDITIONAL_IMAGES, "[]");
        }

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
            List<String> additionalImages = new ArrayList<>();
            String imagesJson = cursor.getString(cursor.getColumnIndexOrThrow(ADDITIONAL_IMAGES));
            if (imagesJson != null && !imagesJson.isEmpty()) {
                additionalImages = new Gson().fromJson(imagesJson, ArrayList.class);
            }

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
                List<String> additionalImages = new ArrayList<>();
                String imagesJson = cursor.getString(cursor.getColumnIndexOrThrow(ADDITIONAL_IMAGES));
                if (imagesJson != null && !imagesJson.isEmpty()) {
                    additionalImages = new Gson().fromJson(imagesJson, ArrayList.class);
                }

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
        if (explore.getAdditionalImages() != null) {
            String jsonImages = new Gson().toJson(explore.getAdditionalImages());
            values.put(ADDITIONAL_IMAGES, jsonImages);
        } else {
            values.put(ADDITIONAL_IMAGES, "[]");
        }
        int rowsAffected = db.update(TABLE_NAME, values, ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }
}
