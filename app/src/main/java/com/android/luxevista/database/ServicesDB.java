package com.android.luxevista.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.luxevista.LuxeService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ServicesDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ServicesDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Services";
    private static final String SERVICE_ID = "id";
    private static final String SERVICE_TITLE = "serviceTitle";
    private static final String SERVICE_TYPE = "serviceType";
    private static final String SERVICE_DESCRIPTION = "serviceDescription";
    private static final String DURATION = "duration";
    private static final String PRICE = "price";
    private static final String CUISINE = "cuisine";
    private static final String RESERVATION = "reservation";
    private static final String CAPACITY = "capacity";
    private static final String AMENITIES = "amenities";
    private static final String BOOKING_INSTRUCTION = "bookingInstruction";
    private static final String CANCELLATION_POLICY = "cancellationPolicy";
    private static final String COVER_IMAGE = "coverImage";
    private static final String ADDITIONAL_IMAGES = "additionalImages";

    // Table creation statement
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SERVICE_TITLE + " TEXT NOT NULL, " +
                    SERVICE_TYPE + " TEXT NOT NULL, " +
                    SERVICE_DESCRIPTION + " TEXT, " +
                    DURATION + " TEXT, " +
                    PRICE + " REAL, " +
                    CUISINE + " TEXT, " +
                    RESERVATION + " TEXT, " +
                    CAPACITY + " TEXT, " +
                    AMENITIES + " TEXT, " +
                    BOOKING_INSTRUCTION + " TEXT, " +
                    CANCELLATION_POLICY + " TEXT, " +
                    COVER_IMAGE + " TEXT, " +
                    ADDITIONAL_IMAGES + " TEXT" +
                    ");";

    public ServicesDB(Context context) {
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

    // Method to insert a service
    public Long insertService(LuxeService service) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SERVICE_TITLE, service.getServiceTitle());
        values.put(SERVICE_TYPE, service.getServiceType());
        values.put(SERVICE_DESCRIPTION, service.getServiceDescription());
        values.put(DURATION, service.getDuration());
        values.put(PRICE, service.getPrice());
        values.put(CUISINE, service.getCuisine());
        values.put(RESERVATION, service.getReservation());
        values.put(CAPACITY, service.getCapacity());
        values.put(AMENITIES, service.getAmenities());
        values.put(BOOKING_INSTRUCTION, service.getBookingInstruction());
        values.put(CANCELLATION_POLICY, service.getCancellationPolicy());
        values.put(COVER_IMAGE, service.getCoverImage());

        // Convert additional images list to JSON
        if (service.getAdditionalImages() != null) {
            String jsonImages = new Gson().toJson(service.getAdditionalImages());
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

    public LuxeService getServiceById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        LuxeService service = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + SERVICE_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            List<String> additionalImages = new ArrayList<>();
            String imagesJson = cursor.getString(cursor.getColumnIndexOrThrow(ADDITIONAL_IMAGES));
            if (imagesJson != null && !imagesJson.isEmpty()) {
                additionalImages = new Gson().fromJson(imagesJson, ArrayList.class);
            }

            service = new LuxeService(
                    cursor.getInt(cursor.getColumnIndexOrThrow(SERVICE_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_TYPE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DURATION)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(PRICE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(CUISINE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(RESERVATION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(CAPACITY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(AMENITIES)),
                    cursor.getString(cursor.getColumnIndexOrThrow(BOOKING_INSTRUCTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(CANCELLATION_POLICY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COVER_IMAGE)),
                    additionalImages
            );
        }

        cursor.close();
        db.close();
        return service;
    }

    // Method to get all services
    public List<LuxeService> getAllServices() {
        List<LuxeService> serviceList = new ArrayList<>();
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

                LuxeService service = new LuxeService(
                        cursor.getInt(cursor.getColumnIndexOrThrow(SERVICE_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DURATION)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(CUISINE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(RESERVATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(CAPACITY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(AMENITIES)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BOOKING_INSTRUCTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(CANCELLATION_POLICY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COVER_IMAGE)),
                        additionalImages
                );

                serviceList.add(service);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return serviceList;
    }
}
