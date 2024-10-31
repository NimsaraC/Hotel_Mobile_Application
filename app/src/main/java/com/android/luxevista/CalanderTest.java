package com.android.luxevista;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.luxevista.database.BookingDB;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CalanderTest extends AppCompatActivity {

    private BookingDB bookingDB;
    private Booking booking;
    private List<Booking> bookings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calander_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bookingDB = new BookingDB(this);
        bookings = bookingDB.getAllBookings();
        List<String> bookingDates = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Booking booking : bookings) {
            String checkInDateStr = booking.getCheckInDate(); // assuming this returns a String in "yyyy/MM/dd" format
            String checkOutDateStr = booking.getCheckOutDate(); // assuming this returns a String in "yyyy/MM/dd" format

            // Parse the check-in and check-out dates
            LocalDate checkInDate = LocalDate.parse(checkInDateStr, formatter);
            LocalDate checkOutDate = LocalDate.parse(checkOutDateStr, formatter);

            // Add all dates from check-in to check-out inclusive
            for (LocalDate date = checkInDate; !date.isAfter(checkOutDate); date = date.plusDays(1)) {
                bookingDates.add(date.format(formatter));
            }
        }

        MaterialCalendarView calendarView = findViewById(R.id.calendarView);

        List<String> dateList = new ArrayList<>();
        dateList.add("2024-11-05");
        dateList.add("2024-12-25");
        dateList.add("2025-01-01");

        HashSet<CalendarDay> dates = new HashSet<>();
        for (String dateString : bookingDates) {
            String[] parts = dateString.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1; // Month is zero-based
            int day = Integer.parseInt(parts[2]);
            dates.add(CalendarDay.from(year, month, day));
        }

        calendarView.setEnabled(false);
        calendarView.addDecorator(new EventDecorator(Color.RED, dates));
    }
}