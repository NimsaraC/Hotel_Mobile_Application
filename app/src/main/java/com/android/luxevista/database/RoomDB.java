package com.android.luxevista.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.luxevista.Room;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RoomDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Room";
    private static final String ROOM_ID = "id";
    private static final String ROOM_TYPE = "roomType";
    private static final String DESCRIPTION = "description";
    private static final String ROOM_SIZE = "roomSize";
    private static final String BED_TYPE = "bedType";
    private static final String VIEW = "roomView";
    private static final String OCCUPANCY = "occupancy";
    private static final String AMENITIES = "amenities";
    private static final String ADDITIONAL_SERVICES = "additionalServices";
    private static final String CHECKIN_TIME = "checkInTime";
    private static final String CHECKOUT_TIME = "checkOutTime";
    private static final String CANCELLATION_POLICY = "cancellationPolicy";
    private static final String NO_SMOKING_POLICY = "noSmokingPolicy";
    private static final String RATE = "rate";
    private static final String COVER_IMAGE = "coverImage";
    private static final String ADDITIONAL_IMAGES = "additionalImages";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ROOM_TYPE + " TEXT NOT NULL, " +
                    DESCRIPTION + " TEXT, " +
                    ROOM_SIZE + " TEXT, " +
                    BED_TYPE + " TEXT, " +
                    VIEW + " TEXT, " +
                    OCCUPANCY + " INTEGER, " +
                    AMENITIES + " TEXT, " +
                    ADDITIONAL_SERVICES + " TEXT, " +
                    CHECKIN_TIME + " TEXT, " +
                    CHECKOUT_TIME + " TEXT, " +
                    CANCELLATION_POLICY + " TEXT, " +
                    NO_SMOKING_POLICY + " TEXT, " +
                    RATE + " REAL, " +
                    COVER_IMAGE + " TEXT, " +
                    ADDITIONAL_IMAGES + " TEXT" +
                    ");";

    public RoomDB(Context context) {
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
    public Long insertRoom(Room room) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
        values.put(ROOM_TYPE, room.getRoomType());
        values.put(DESCRIPTION, room.getDescription());
        values.put(ROOM_SIZE, room.getRoomSize());
        values.put(BED_TYPE, room.getBedType());
        values.put(VIEW, room.getView());
        values.put(OCCUPANCY, room.getOccupancy());
        values.put(AMENITIES, room.getAmenities());
        values.put(ADDITIONAL_SERVICES, room.getAdditionalServices());
        values.put(CHECKIN_TIME, room.getCheckInTime());
        values.put(CHECKOUT_TIME, room.getCheckOutTime());
        values.put(CANCELLATION_POLICY, room.getCancellationPolicy());
        values.put(NO_SMOKING_POLICY, room.getNoSmokingPolicy());
        values.put(RATE, room.getRate());
        values.put(COVER_IMAGE, room.getCoverImage());
        values.put(ADDITIONAL_IMAGES, room.getAdditionalImages().toString());

    Long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
}
    public Room getRoomById(int roomId) {
        Room room = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ROOM_ID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(roomId)});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                room = new Room();
                room.setRoomId(cursor.getInt(cursor.getColumnIndexOrThrow(ROOM_ID)));
                room.setRoomType(cursor.getString(cursor.getColumnIndexOrThrow(ROOM_TYPE)));
                room.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                room.setRoomSize(cursor.getString(cursor.getColumnIndexOrThrow(ROOM_SIZE)));
                room.setBedType(cursor.getString(cursor.getColumnIndexOrThrow(BED_TYPE)));
                room.setView(cursor.getString(cursor.getColumnIndexOrThrow(VIEW)));
                room.setOccupancy(cursor.getInt(cursor.getColumnIndexOrThrow(OCCUPANCY)));
                room.setAmenities(cursor.getString(cursor.getColumnIndexOrThrow(AMENITIES)));
                room.setAdditionalServices(cursor.getString(cursor.getColumnIndexOrThrow(ADDITIONAL_SERVICES)));
                room.setCheckInTime(cursor.getString(cursor.getColumnIndexOrThrow(CHECKIN_TIME)));
                room.setCheckOutTime(cursor.getString(cursor.getColumnIndexOrThrow(CHECKOUT_TIME)));
                room.setCancellationPolicy(cursor.getString(cursor.getColumnIndexOrThrow(CANCELLATION_POLICY)));
                room.setNoSmokingPolicy(cursor.getString(cursor.getColumnIndexOrThrow(NO_SMOKING_POLICY)));
                room.setRate(cursor.getDouble(cursor.getColumnIndexOrThrow(RATE)));
                room.setCoverImage(cursor.getString(cursor.getColumnIndexOrThrow(COVER_IMAGE)));
                String additionalImagesString = cursor.getString(cursor.getColumnIndexOrThrow(ADDITIONAL_IMAGES));
                room.setAdditionalImages(new ArrayList<>(Arrays.asList(additionalImagesString.split(","))));
            }
            cursor.close();
        }

        db.close();
        return room;
    }

public List<Room> getAllRooms() {
    List<Room> roomList = new ArrayList<>();

    String query = "SELECT * FROM " + TABLE_NAME;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(query, null);

    if (cursor != null) {
        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setRoomId(cursor.getInt(cursor.getColumnIndexOrThrow(ROOM_ID)));
                room.setRoomType(cursor.getString(cursor.getColumnIndexOrThrow(ROOM_TYPE)));
                room.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                room.setRoomSize(cursor.getString(cursor.getColumnIndexOrThrow(ROOM_SIZE)));
                room.setBedType(cursor.getString(cursor.getColumnIndexOrThrow(BED_TYPE)));
                room.setView(cursor.getString(cursor.getColumnIndexOrThrow(VIEW)));
                room.setOccupancy(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(OCCUPANCY))));
                room.setAmenities(cursor.getString(cursor.getColumnIndexOrThrow(AMENITIES)));
                room.setAdditionalServices(cursor.getString(cursor.getColumnIndexOrThrow(ADDITIONAL_SERVICES)));
                room.setCheckInTime(cursor.getString(cursor.getColumnIndexOrThrow(CHECKIN_TIME)));
                room.setCheckOutTime(cursor.getString(cursor.getColumnIndexOrThrow(CHECKOUT_TIME)));
                room.setCancellationPolicy(cursor.getString(cursor.getColumnIndexOrThrow(CANCELLATION_POLICY)));
                room.setNoSmokingPolicy(cursor.getString(cursor.getColumnIndexOrThrow(NO_SMOKING_POLICY)));
                room.setRate(cursor.getDouble(cursor.getColumnIndexOrThrow(RATE)));
                room.setCoverImage(cursor.getString(cursor.getColumnIndexOrThrow(COVER_IMAGE)));
                String additionalImagesString = cursor.getString(cursor.getColumnIndexOrThrow(ADDITIONAL_IMAGES));
                room.setAdditionalImages(new ArrayList<>(Arrays.asList(additionalImagesString.split(","))));

                roomList.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    db.close();
    return roomList;
}

    private void insertDummyData(SQLiteDatabase db){
        ContentValues values = new ContentValues();

        values.put(ROOM_TYPE, "Ocean View");
        values.put(DESCRIPTION, "Spacious suite with panoramic ocean views, private balcony, and luxurious seating area.");
        values.put(ROOM_SIZE, 800);
        values.put(BED_TYPE, "King-Size Bed");
        values.put(VIEW, "Full oceanfront view");
        values.put(OCCUPANCY, 2);
        values.put(AMENITIES, "Mini bar \npremium coffee machine \nhigh-speed Wi-Fi \nand a rainfall shower.");
        values.put(ADDITIONAL_SERVICES, "Daily housekeeping\nin-room dining \nturndown service.");
        values.put(CHECKIN_TIME, "03:00 PM");
        values.put(CHECKOUT_TIME, "12:00 PM");
        values.put(CANCELLATION_POLICY, "Free cancellation up to 48 hours before arrival.");
        values.put(NO_SMOKING_POLICY, "Non-smoking room");
        values.put(RATE, 450);
        values.put(COVER_IMAGE, "drawable/ocean_view_room");
        values.put(ADDITIONAL_IMAGES, "drawable/ocean_view_room");

        db.insert(TABLE_NAME, null, values);

        values.clear();
        values.put(ROOM_TYPE, "Ocean View");
        values.put(DESCRIPTION, "Elegant room with floor-to-ceiling windows and a breathtaking view of the ocean.");
        values.put(ROOM_SIZE, 500);
        values.put(BED_TYPE, " Queen-Size Bed");
        values.put(VIEW, "Full oceanfront view");
        values.put(OCCUPANCY, 2);
        values.put(AMENITIES, "Wi-Fi\nair conditioning\nespresso machine\nspa-inspired bathroom\nbeach towels.");
        values.put(ADDITIONAL_SERVICES, "24-hour room service\n express laundry \ncomplimentary breakfast.");
        values.put(CHECKIN_TIME, "03:00 PM");
        values.put(CHECKOUT_TIME, "12:00 PM");
        values.put(CANCELLATION_POLICY, "Free cancellation up to 24 hours before arrival.");
        values.put(NO_SMOKING_POLICY, "Non-smoking room");
        values.put(RATE, 400);
        values.put(COVER_IMAGE, "drawable/ocean_view_room");
        values.put(ADDITIONAL_IMAGES, "drawable/ocean_view_room");

        db.insert(TABLE_NAME, null, values);

        values.clear();
        values.put(ROOM_TYPE, "Ocean View");
        values.put(DESCRIPTION, "Relax in this luxurious room with a private patio overlooking the beach.");
        values.put(ROOM_SIZE, 550);
        values.put(BED_TYPE, "King-Size Bed");
        values.put(VIEW, "Full oceanfront view");
        values.put(OCCUPANCY, 2);
        values.put(AMENITIES, "Wi-Fi\n 55” Smart TV\n minibar\n plush bathrobes\nprivate safe");
        values.put(ADDITIONAL_SERVICES, "Daily housekeeping\ncomplimentary breakfast \nvalet parking");
        values.put(CHECKIN_TIME, "02:00 PM");
        values.put(CHECKOUT_TIME, "12:00 AM");
        values.put(CANCELLATION_POLICY, "Free cancellation up to 72 hours before arrival.");
        values.put(NO_SMOKING_POLICY, "Non-smoking room");
        values.put(RATE, 400);
        values.put(COVER_IMAGE, "drawable/ocean_view_room");
        values.put(ADDITIONAL_IMAGES, "drawable/ocean_view_room");

        db.insert(TABLE_NAME, null, values);

        values.clear();
        values.put(ROOM_TYPE, "Family");
        values.put(DESCRIPTION, "Spacious suite with a garden view, ideal for families.");
        values.put(ROOM_SIZE, 900);
        values.put(BED_TYPE, "One King-Size Bed and Two Twin Beds");
        values.put(VIEW, "Full oceanfront view");
        values.put(OCCUPANCY, 2);
        values.put(AMENITIES, "Wi-Fi \ntwo TVs\nkitchenette\ndining area.");
        values.put(ADDITIONAL_SERVICES, "Daily housekeeping\nroom service");
        values.put(CHECKIN_TIME, "03:00 PM");
        values.put(CHECKOUT_TIME, "12:00 AM");
        values.put(CANCELLATION_POLICY, "Free cancellation up to 48 hours before arrival.");
        values.put(NO_SMOKING_POLICY, "Non-smoking room");
        values.put(RATE, 500);
        values.put(COVER_IMAGE, "drawable/family_room");
        values.put(ADDITIONAL_IMAGES, "drawable/family_room");

        db.insert(TABLE_NAME, null, values);

        values.clear();
        values.put(ROOM_TYPE, "Family");
        values.put(DESCRIPTION, "Spacious two-bedroom suite with modern amenities.");
        values.put(ROOM_SIZE, 1200);
        values.put(BED_TYPE, "King-Size Bed and Queen-Size Bed");
        values.put(VIEW, "Full oceanfront view");
        values.put(OCCUPANCY, 5);
        values.put(AMENITIES, "Wi-Fi\nmultiple TVs\nfull kitchen\nprivate balcony.");
        values.put(ADDITIONAL_SERVICES, "Daily housekeeping\nturndown service\nkids’ amenities");
        values.put(CHECKIN_TIME, "03:00 PM");
        values.put(CHECKOUT_TIME, "12:00 AM");
        values.put(CANCELLATION_POLICY, "Free cancellation up to 72 hours before arrival.");
        values.put(NO_SMOKING_POLICY, "Non-smoking room");
        values.put(RATE, 550);
        values.put(COVER_IMAGE, "drawable/family_room");
        values.put(ADDITIONAL_IMAGES, "drawable/family_room");

        db.insert(TABLE_NAME, null, values);

        values.clear();
        values.put(ROOM_TYPE, "Deluxe Garden");
        values.put(DESCRIPTION, "Elegant room with a city view and modern design.");
        values.put(ROOM_SIZE, 500);
        values.put(BED_TYPE, "King-Size Bed");
        values.put(VIEW, "Partial garden and pool view");
        values.put(OCCUPANCY, 2);
        values.put(AMENITIES, "Wi-Fin\nflat-screen TV\nminibar\ncoffee maker");
        values.put(ADDITIONAL_SERVICES, "Daily housekeeping\nroom service");
        values.put(CHECKIN_TIME, "03:00 PM");
        values.put(CHECKOUT_TIME, "12:00 AM");
        values.put(CANCELLATION_POLICY, "Free cancellation up to 24 hours before arrival.");
        values.put(NO_SMOKING_POLICY, "Non-smoking room");
        values.put(RATE, 300);
        values.put(COVER_IMAGE, "drawable/deluxe_room");
        values.put(ADDITIONAL_IMAGES, "drawable/deluxe_room");

        db.insert(TABLE_NAME, null, values);

        values.clear();
        values.put(ROOM_TYPE, "Deluxe Garden");
        values.put(DESCRIPTION, "Perfect for friends or small families, this room offers modern decor and two plush double beds.");
        values.put(ROOM_SIZE, 450);
        values.put(BED_TYPE, "Two Double Beds");
        values.put(VIEW, "Garden view");
        values.put(OCCUPANCY, 4);
        values.put(AMENITIES, "Wi-Fin\nflat-screen TV\nminibar\ncoffee maker");
        values.put(ADDITIONAL_SERVICES, "Daily housekeeping\nroom service");
        values.put(CHECKIN_TIME, "02:00 PM");
        values.put(CHECKOUT_TIME, "12:00 AM");
        values.put(CANCELLATION_POLICY, "Free cancellation up to 24 hours before arrival.");
        values.put(NO_SMOKING_POLICY, "Non-smoking room");
        values.put(RATE, 320);
        values.put(COVER_IMAGE, "drawable/deluxe_room");
        values.put(ADDITIONAL_IMAGES, "drawable/deluxe_room");

        db.insert(TABLE_NAME, null, values);

    }




}
