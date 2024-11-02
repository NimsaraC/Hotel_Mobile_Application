package com.android.luxevista.userPages;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.android.luxevista.Booking;
import com.android.luxevista.EventDecorator;
import com.android.luxevista.R;
import com.android.luxevista.Room;
import com.android.luxevista.adapter.ImageCarouselAdapter;
import com.android.luxevista.database.BookingDB;
import com.android.luxevista.database.RoomDB;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class RoomDetailsPage extends AppCompatActivity {

    private TextView txtRoomType, txtRoomPrice, txtRoomSize, txtRoomView, txtRoomOccupancy, txtRoomAmenities, txtRoomServices, txtCheckIn, txtRoomCheckOut, txtRoomCancellation, txtRoomSmoking;
    private ImageView coverImage;
    private LinearLayout btnBookNow, btnProfile;
    private RoomDB roomDB;
    private Room room;
    private int roomId = 0;
    private BookingDB bookingDB;
    private List<Booking> bookings;
    private List<String> bookingDates;
    private ViewPager2 viewPager;
    private ImageCarouselAdapter adapter;
    private List<Uri> imageUriList;
    private Handler sliderHandler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_room_details_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        roomDB = new RoomDB(this);
        roomId = getIntent().getIntExtra("roomId", 0);


        txtRoomType = findViewById(R.id.txtRoomType);
        txtRoomPrice = findViewById(R.id.txtRoomPrice);
        txtRoomSize = findViewById(R.id.txtRoomSize);
        txtRoomView = findViewById(R.id.txtRoomView);
        txtRoomOccupancy = findViewById(R.id.txtRoomOccupancy);
        txtRoomAmenities = findViewById(R.id.txtRoomAmenities);
        txtRoomServices = findViewById(R.id.txtRoomServices);
        txtCheckIn = findViewById(R.id.txtCheckIn);
        txtRoomCheckOut = findViewById(R.id.txtRoomCheckOut);
        txtRoomCancellation = findViewById(R.id.txtRoomCancellation);
        txtRoomSmoking = findViewById(R.id.txtRoomSmoking);
        coverImage = findViewById(R.id.coverImage);
        btnBookNow = findViewById(R.id.btnBookNow);
        btnProfile = findViewById(R.id.btnProfile);

        setData();
        bookButton();
        setCalendar();
        imageSlider();

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intent);
            }
        });
    }
    private void imageSlider(){
        imageUriList = new ArrayList<>();
        List<String> imagePaths = room.getAdditionalImages();
        for (String path : imagePaths) {
            path = path.replace("[", "").replace("]", "").trim();
            imageUriList.add(Uri.parse(path));
        }

        viewPager = findViewById(R.id.viewPager);
        adapter = new ImageCarouselAdapter(this, imageUriList);
        viewPager.setAdapter(adapter);

        startAutoSlide();
    }
    private void setCalendar(){
        bookingDB = new BookingDB(this);
        bookings = bookingDB.getAllBookingsByRoomId(roomId);
        bookingDates = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Booking booking : bookings) {
            String checkInDateStr = booking.getCheckInDate();
            String checkOutDateStr = booking.getCheckOutDate();

            LocalDate checkInDate = LocalDate.parse(checkInDateStr, formatter);
            LocalDate checkOutDate = LocalDate.parse(checkOutDateStr, formatter);

            for (LocalDate date = checkInDate; !date.isAfter(checkOutDate); date = date.plusDays(1)) {
                bookingDates.add(date.format(formatter));
            }
        }

        MaterialCalendarView calendarView = findViewById(R.id.calendarView);

        HashSet<CalendarDay> dates = new HashSet<>();
        for (String dateString : bookingDates) {
            String[] parts = dateString.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1;
            int day = Integer.parseInt(parts[2]);
            dates.add(CalendarDay.from(year, month, day));
        }

        calendarView.setEnabled(false);
        calendarView.addDecorator(new EventDecorator(Color.RED, dates));
    }
    private void setData() {
        room = roomDB.getRoomById(roomId);

        txtRoomType.setText(room.getRoomType());
        txtRoomPrice.setText(String.format("$%s", room.getRate()));
        txtRoomSize.setText(room.getRoomSize());
        txtRoomView.setText(room.getView());
        txtRoomOccupancy.setText(String.format("Up to %s guests", room.getOccupancy()));
        txtRoomAmenities.setText(room.getAmenities());
        txtRoomServices.setText(room.getAdditionalServices());
        txtRoomCancellation.setText(room.getCancellationPolicy());

        SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

        try {
            String checkInTimeFormatted = outputFormat.format(inputFormat.parse(room.getCheckInTime()));
            String checkOutTimeFormatted = outputFormat.format(inputFormat.parse(room.getCheckOutTime()));

            txtCheckIn.setText(checkInTimeFormatted);
            txtRoomCheckOut.setText(checkOutTimeFormatted);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Picasso.get()
                .load(room.getCoverImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.home_screen)
                .into(coverImage);
    }
    private void bookButton(){
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RoomBookingPage.class);
                intent.putExtra("roomId", roomId);
                startActivity(intent);
            }
        });
    }
    private void startAutoSlide() {
        sliderHandler.removeCallbacks(sliderRunnable);
        sliderHandler.postDelayed(sliderRunnable, 5000);
    }
    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int nextItem = (viewPager.getCurrentItem() + 1) % adapter.getItemCount();
            viewPager.setCurrentItem(nextItem, true);
            sliderHandler.postDelayed(this, 5000);
        }
    };
    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAutoSlide();
    }
}