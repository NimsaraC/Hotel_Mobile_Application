package com.android.luxevista.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.luxevista.LuxeService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
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
        insertDummyData(db);
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
        values.put(ADDITIONAL_IMAGES, service.getAdditionalImages().toString());

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

            service = new LuxeService();
            service.setId(cursor.getInt(cursor.getColumnIndexOrThrow(SERVICE_ID)));
            service.setServiceTitle(cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_TITLE)));
            service.setServiceType(cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_TYPE)));
            service.setServiceDescription(cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_DESCRIPTION)));
            service.setDuration(cursor.getString(cursor.getColumnIndexOrThrow(DURATION)));
            service.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(PRICE)));
            service.setCuisine(cursor.getString(cursor.getColumnIndexOrThrow(CUISINE)));
            service.setReservation(cursor.getString(cursor.getColumnIndexOrThrow(RESERVATION)));
            service.setCapacity(cursor.getString(cursor.getColumnIndexOrThrow(CAPACITY)));
            service.setAmenities(cursor.getString(cursor.getColumnIndexOrThrow(AMENITIES)));
            service.setBookingInstruction(cursor.getString(cursor.getColumnIndexOrThrow(BOOKING_INSTRUCTION)));
            service.setCancellationPolicy(cursor.getString(cursor.getColumnIndexOrThrow(CANCELLATION_POLICY)));
            service.setCoverImage(cursor.getString(cursor.getColumnIndexOrThrow(COVER_IMAGE)));
            String additionalImagesString = cursor.getString(cursor.getColumnIndexOrThrow(ADDITIONAL_IMAGES));
            service.setAdditionalImages(new ArrayList<>(Arrays.asList(additionalImagesString.split(","))));
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

        if(cursor != null){
            if (cursor.moveToFirst()) {

                do{
                    LuxeService service = new LuxeService();
                    service.setId(cursor.getInt(cursor.getColumnIndexOrThrow(SERVICE_ID)));
                    service.setServiceTitle(cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_TITLE)));
                    service.setServiceType(cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_TYPE)));
                    service.setServiceDescription(cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_DESCRIPTION)));
                    service.setDuration(cursor.getString(cursor.getColumnIndexOrThrow(DURATION)));
                    service.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(PRICE)));
                    service.setCuisine(cursor.getString(cursor.getColumnIndexOrThrow(CUISINE)));
                    service.setReservation(cursor.getString(cursor.getColumnIndexOrThrow(RESERVATION)));
                    service.setCapacity(cursor.getString(cursor.getColumnIndexOrThrow(CAPACITY)));
                    service.setAmenities(cursor.getString(cursor.getColumnIndexOrThrow(AMENITIES)));
                    service.setBookingInstruction(cursor.getString(cursor.getColumnIndexOrThrow(BOOKING_INSTRUCTION)));
                    service.setCancellationPolicy(cursor.getString(cursor.getColumnIndexOrThrow(CANCELLATION_POLICY)));
                    service.setCoverImage(cursor.getString(cursor.getColumnIndexOrThrow(COVER_IMAGE)));
                    String additionalImagesString = cursor.getString(cursor.getColumnIndexOrThrow(ADDITIONAL_IMAGES));
                    service.setAdditionalImages(new ArrayList<>(Arrays.asList(additionalImagesString.split(","))));

                    serviceList.add(service);
                }while (cursor.moveToNext());
            }cursor.close();
        }

        db.close();

        return serviceList;
    }

    public int editeServiceById(int id, LuxeService service){
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

        if (service.getAdditionalImages() != null) {
            String jsonImages = new Gson().toJson(service.getAdditionalImages());
            values.put(ADDITIONAL_IMAGES, jsonImages);
        } else {
            values.put(ADDITIONAL_IMAGES, "[]");
        }

        int rowsAffected = db.update(TABLE_NAME, values, SERVICE_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    private void insertDummyData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        //Spa service
        values.put(SERVICE_TITLE,"Spa service");
        values.put(SERVICE_TYPE, "Signature Relaxation");
        values.put(SERVICE_DESCRIPTION, "A calming full-body massage using essential oils tailored to relieve tension and promote relaxation.");
        values.put(DURATION, "60 minutes");
        values.put(PRICE, 120);
        values.put(BOOKING_INSTRUCTION, "Use the app to select your desired treatment and available time slots. Advanced booking is recommended for optimal scheduling.");
        values.put(CANCELLATION_POLICY, "Cancellations must be made 24 hours in advance to avoid a fee.");
        values.put(COVER_IMAGE, "drawable/spa_service");
        values.put(ADDITIONAL_IMAGES, "drawable/spa_service");

        db.insert(TABLE_NAME, null, values);

        values.clear();
        values.put(SERVICE_TITLE,"Spa service");
        values.put(SERVICE_TYPE, "Revitalizing Facial");
        values.put(SERVICE_DESCRIPTION, "A luxurious facial treatment that cleanses, exfoliates, and hydrates the skin, leaving you with a radiant glow.");
        values.put(DURATION, "60 minutes");
        values.put(PRICE, 100);
        values.put(BOOKING_INSTRUCTION, "Use the app to select your desired treatment and available time slots. Advanced booking is recommended for optimal scheduling.");
        values.put(CANCELLATION_POLICY, "Cancellations must be made 24 hours in advance to avoid a fee.");
        values.put(COVER_IMAGE, "drawable/spa_service");
        values.put(ADDITIONAL_IMAGES, "drawable/spa_service");

        db.insert(TABLE_NAME, null, values);

        //Dining Service
        values.clear();
        values.put(SERVICE_TITLE, "Dining service");
        values.put(SERVICE_TYPE, "Seaside Grill");
        values.put(SERVICE_DESCRIPTION, "An oceanfront dining experience featuring the freshest seafood and grill options, paired with stunning views.");
        values.put(CUISINE, "Seafood and Grill");
        values.put(RESERVATION, "6 PM - 10 PM");
        values.put(BOOKING_INSTRUCTION, "Use the app to make reservations for guaranteed seating and to inform about any dietary preferences.");
        values.put(CANCELLATION_POLICY, "Cancellations must be made 12 hours in advance to avoid a fee.");
        values.put(COVER_IMAGE, "drawable/dining_service");
        values.put(ADDITIONAL_IMAGES, "drawable/dining_service");

        db.insert(TABLE_NAME, null, values);

        values.clear();
        values.put(SERVICE_TITLE, "Dining service");
        values.put(SERVICE_TYPE, "Sky Lounge");
        values.put(SERVICE_DESCRIPTION, "A rooftop bar offering contemporary international cuisine with panoramic sunset views and live music.");
        values.put(CUISINE, "Contemporary International");
        values.put(RESERVATION, "5 PM - 11 PM");
        values.put(BOOKING_INSTRUCTION, "Use the app to make reservations for guaranteed seating and to inform about any dietary preferences.");
        values.put(CANCELLATION_POLICY, "Cancellations must be made 12 hours in advance to avoid a fee.");
        values.put(COVER_IMAGE, "drawable/dining_service");
        values.put(ADDITIONAL_IMAGES, "drawable/dining_service");

        db.insert(TABLE_NAME, null, values);

        //Pool Service
        values.clear();
        values.put(SERVICE_TITLE, "Pool service");
        values.put(SERVICE_TYPE, "Luxury Cabana");
        values.put(SERVICE_DESCRIPTION, "An upscale cabana providing enhanced seating, complimentary refreshments, and dedicated server for your convenience.");
        values.put(PRICE, 250);
        values.put(CAPACITY, 6);
        values.put(AMENITIES, "Enhanced seating, complimentary fruit platter, and dedicated server.");
        values.put(BOOKING_INSTRUCTION, "Use the app to secure your luxury cabana and enjoy personalized service.");
        values.put(CANCELLATION_POLICY, "Cancellations must be made 24 hours in advance to avoid a fee.");
        values.put(COVER_IMAGE, "drawable/pool_service");
        values.put(ADDITIONAL_IMAGES, "drawable/pool_service");

        db.insert(TABLE_NAME, null, values);

        values.clear();
        values.put(SERVICE_TITLE, "Pool service");
        values.put(SERVICE_TYPE, "Standard Cabana");
        values.put(SERVICE_DESCRIPTION, "A comfortable cabana with shaded seating, perfect for relaxing poolside while enjoying personalized service.");
        values.put(PRICE, 150);
        values.put(CAPACITY, 4);
        values.put(AMENITIES, "Comfortable seating, shaded area, and a mini-fridge stocked with beverages.");
        values.put(BOOKING_INSTRUCTION, "Use the app to secure your luxury cabana and enjoy personalized service.");
        values.put(CANCELLATION_POLICY, "Cancellations must be made 24 hours in advance to avoid a fee.");
        values.put(COVER_IMAGE, "drawable/pool_service");
        values.put(ADDITIONAL_IMAGES, "drawable/pool_service");

        db.insert(TABLE_NAME, null, values);
    }
}
