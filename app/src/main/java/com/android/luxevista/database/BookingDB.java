package com.android.luxevista.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.luxevista.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BookingDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Booking";
    private static final String BOOKING_ID = "id";
    private static final String BOOKING_TITLE = "bookingTitle";
    private static final String BOOKING_TYPE = "bookingType";
    private static final String CHECKIN_DATE = "checkInDate";
    private static final String CHECKOUT_DATE = "checkOutDate";
    private static final String BOOKING_DATE = "bookingDate";
    private static final String BOOKING_STATUS = "bookingStatus";
    private static final String SPECIAL_REQUESTS = "specialRequests";
    private static final String TOTAL_PRICE = "totalPrice";
    private static final String TAXES = "taxes";
    private static final String GUEST_NAME = "guestName";
    private static final String GUEST_EMAIL = "guestEmail";
    private static final String GUEST_PHONE = "guestPhone";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BOOKING_TITLE + " TEXT NOT NULL, " +
                    BOOKING_TYPE + " TEXT NOT NULL, " +
                    CHECKIN_DATE + " TEXT NOT NULL, " +
                    CHECKOUT_DATE + " TEXT NOT NULL, " +
                    BOOKING_DATE + " TEXT NOT NULL, " +
                    BOOKING_STATUS + " TEXT NOT NULL, " +
                    SPECIAL_REQUESTS + " TEXT, " +
                    TOTAL_PRICE + " REAL NOT NULL, " +
                    TAXES + " REAL NOT NULL, " +
                    GUEST_NAME + " TEXT NOT NULL, " +
                    GUEST_EMAIL + " TEXT NOT NULL, " +
                    GUEST_PHONE + " TEXT NOT NULL" +
                    ");";

    public BookingDB(Context context) {
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

    public Long insertBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOKING_TITLE, booking.getBookingTitle());
        values.put(BOOKING_TYPE, booking.getBookingType());
        values.put(CHECKIN_DATE, booking.getCheckInDate());
        values.put(CHECKOUT_DATE, booking.getCheckOutDate());
        values.put(BOOKING_DATE, booking.getBookingDate());
        values.put(BOOKING_STATUS, booking.getBookingStatus());
        values.put(SPECIAL_REQUESTS, booking.getSpecialRequests());
        values.put(TOTAL_PRICE, booking.getTotalPrice());
        values.put(TAXES, booking.getTaxes());
        values.put(GUEST_NAME, booking.getGuestName());
        values.put(GUEST_EMAIL, booking.getGuestEmail());
        values.put(GUEST_PHONE, booking.getGuestPhone());

        Long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public Booking getBookingById(int bookingId) {
        Booking booking = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + BOOKING_ID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(bookingId)});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                booking = new Booking(
                        cursor.getInt(cursor.getColumnIndexOrThrow(BOOKING_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BOOKING_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BOOKING_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(CHECKIN_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(CHECKOUT_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BOOKING_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BOOKING_STATUS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(SPECIAL_REQUESTS)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(TOTAL_PRICE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(TAXES)),
                        cursor.getString(cursor.getColumnIndexOrThrow(GUEST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(GUEST_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(GUEST_PHONE))
                );
            }
            cursor.close();
        }

        db.close();
        return booking;
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookingList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Booking booking = new Booking(
                            cursor.getInt(cursor.getColumnIndexOrThrow(BOOKING_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(BOOKING_TITLE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(BOOKING_TYPE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(CHECKIN_DATE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(CHECKOUT_DATE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(BOOKING_DATE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(BOOKING_STATUS)),
                            cursor.getString(cursor.getColumnIndexOrThrow(SPECIAL_REQUESTS)),
                            cursor.getDouble(cursor.getColumnIndexOrThrow(TOTAL_PRICE)),
                            cursor.getDouble(cursor.getColumnIndexOrThrow(TAXES)),
                            cursor.getString(cursor.getColumnIndexOrThrow(GUEST_NAME)),
                            cursor.getString(cursor.getColumnIndexOrThrow(GUEST_EMAIL)),
                            cursor.getString(cursor.getColumnIndexOrThrow(GUEST_PHONE))
                    );

                    bookingList.add(booking);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        db.close();
        return bookingList;
    }
}
