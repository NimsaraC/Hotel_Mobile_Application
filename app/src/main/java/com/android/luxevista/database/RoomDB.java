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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /*
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

        if (room.getAdditionalImages() != null) {
            String jsonImages = new Gson().toJson(room.getAdditionalImages());
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
    }*/
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
/*
    public Room getRoomById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Room room = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ROOM_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            List<String> additionalImages = new ArrayList<>();
            String[] images = cursor.getString(cursor.getColumnIndexOrThrow(ADDITIONAL_IMAGES)).split(",");
            for (String img : images) {
                additionalImages.add(img.trim());
            }

            room = new Room(
                    cursor.getString(cursor.getColumnIndexOrThrow(ROOM_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ROOM_TYPE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ROOM_SIZE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(BED_TYPE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(VIEW)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(OCCUPANCY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(AMENITIES)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ADDITIONAL_SERVICES)),
                    cursor.getString(cursor.getColumnIndexOrThrow(CHECKIN_TIME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(CHECKOUT_TIME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(CANCELLATION_POLICY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(NO_SMOKING_POLICY)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(RATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COVER_IMAGE)),
                    additionalImages
            );
        }

        cursor.close();
        db.close();
        return room;
    }*/

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

    /*
    public List<Room> getAllRooms() {
        List<Room> roomList = new ArrayList<>();
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

                Room room = new Room(
                        cursor.getString(cursor.getColumnIndexOrThrow(ROOM_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ROOM_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ROOM_SIZE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BED_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(VIEW)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(OCCUPANCY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(AMENITIES)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ADDITIONAL_SERVICES)),
                        cursor.getString(cursor.getColumnIndexOrThrow(CHECKIN_TIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(CHECKOUT_TIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(CANCELLATION_POLICY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(NO_SMOKING_POLICY)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(RATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COVER_IMAGE)),
                        additionalImages
                );

                roomList.add(room);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return roomList;
    }*/
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


}
