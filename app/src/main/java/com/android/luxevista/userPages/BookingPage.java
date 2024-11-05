package com.android.luxevista.userPages;

import static com.android.luxevista.SharedPreference.USER_ID;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.luxevista.Booking;
import com.android.luxevista.DualEventDecorator;
import com.android.luxevista.EventDecorator;
import com.android.luxevista.R;
import com.android.luxevista.SharedPreference;
import com.android.luxevista.TripleEventDecorator;
import com.android.luxevista.database.BookingDB;
import com.google.android.material.tabs.TabLayout;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BookingPage extends AppCompatActivity {
    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    MaterialCalendarView calendarView;
    private LinearLayout navRooms, navServices, navExplore, btnProfile;
    private BookingDB bookingDB;
    private List<Booking> bookings, canceledBookings, completedBookings, upcomingBookings;
    private String userID = "";
    private int userId ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bookingDB = new BookingDB(this);
        bookings = bookingDB.getAllBookingsByUserId(userId);

        SharedPreference sharedPreference = new SharedPreference();
        userId = sharedPreference.GetInt(this, USER_ID);
        userID = String.valueOf(userId);

        tabLayout = findViewById(R.id.tabLayout);
        navServices = findViewById(R.id.linearLayoutServices);
        navExplore = findViewById(R.id.linearLayoutExplore);
        navRooms = findViewById(R.id.linearLayoutRooms);
        btnProfile = findViewById(R.id.btnProfile);

        calendarView = findViewById(R.id.calendarView);

        setCalendar();
        bottomNav();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fragmentImplementation(new BookingPageFragment(),"Upcoming Booking", upcomingBookings);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition() == 0){
                    fragmentImplementation(new BookingPageFragment(), "Upcoming Booking", upcomingBookings);
                }
                if(tab.getPosition() == 1){
                    fragmentImplementation(new BookingPageFragment(), "Completed Booking", completedBookings);
                }
                if(tab.getPosition() == 2){
                    fragmentImplementation(new BookingPageFragment(), "Canceled Booking", canceledBookings);
                    //Toast.makeText(HomePage.this, "Room3", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setCalendar() {
        bookings = bookingDB.getAllBookingsByUserId(userId);

        List<String> bookingDates = new ArrayList<>();
        List<Booking> roomBookings = new ArrayList<>();
        List<Booking> serviceBooking = new ArrayList<>();
        List<Booking> exploreBooking = new ArrayList<>();
        List<String> roomBookingDates = new ArrayList<>();
        List<String> serviceBookingDates = new ArrayList<>();
        List<String> exploreBookingDates = new ArrayList<>();

         upcomingBookings = new ArrayList<>();
        List<String> upcomingBookingDates = new ArrayList<>();
         completedBookings = new ArrayList<>();
        List<String> completedBookingDates = new ArrayList<>();
         canceledBookings = new ArrayList<>();
        List<String> canceledBookingDates = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();

        for (Booking booking : bookings) {

            String bookingDateString = booking.getBookingDate();
            LocalDate bookingDate = LocalDate.parse(bookingDateString, formatter);

            bookingDates.add(bookingDateString);

            if(bookingDate.isAfter(currentDate) || bookingDate.isEqual(currentDate)){
                if(booking.getBookingStatus().equals("confirmed")){

                    if(booking.getRoomId() != null){
                        roomBookings.add(booking);
                        roomBookingDates.add(bookingDateString);
                    }else
                    if(booking.getServiceId() != null){
                        serviceBooking.add(booking);
                        serviceBookingDates.add(bookingDateString);
                    }else
                    if(booking.getExploreId() != null){
                        exploreBooking.add(booking);
                        exploreBookingDates.add(bookingDateString);
                    }
                }


                if(booking.getBookingStatus().equals("confirmed")){
                    upcomingBookingDates.add(bookingDateString);
                    upcomingBookings.add(booking);
                }
                if(booking.getBookingStatus().equals("completed")){
                    completedBookingDates.add(bookingDateString);
                    completedBookings.add(booking);
                }
                if(booking.getBookingStatus().equals("canceled")){
                    canceledBookingDates.add(bookingDateString);
                    canceledBookings.add(booking);
                }
            }

        }

        HashSet<CalendarDay> bookingDays = convertToCalendarDays(bookingDates);

        HashSet<CalendarDay> roomBookingDays = convertToCalendarDays(roomBookingDates);
        HashSet<CalendarDay> serviceBookingDays = convertToCalendarDays(serviceBookingDates);
        HashSet<CalendarDay> exploreBookingDays = convertToCalendarDays(exploreBookingDates);

        HashSet<CalendarDay> Triple = new HashSet<>(bookingDays);
        Triple.retainAll(roomBookingDays);
        Triple.retainAll(serviceBookingDays);
        Triple.retainAll(exploreBookingDays);

        roomBookingDays.removeAll(Triple);
        serviceBookingDays.removeAll(Triple);
        exploreBookingDays.removeAll(Triple);

        HashSet<CalendarDay> roomAndService = new HashSet<>(bookingDays);
        roomAndService.retainAll(roomBookingDays);
        roomAndService.retainAll(serviceBookingDays);

        roomBookingDays.removeAll(roomAndService);
        serviceBookingDays.removeAll(roomAndService);

        HashSet<CalendarDay> roomAndExplore = new HashSet<>(bookingDays);
        roomAndExplore.retainAll(roomBookingDays);
        roomAndExplore.retainAll(exploreBookingDays);

        roomBookingDays.removeAll(roomAndExplore);
        exploreBookingDays.removeAll(roomAndExplore);

        HashSet<CalendarDay> serviceAndExplore = new HashSet<>(bookingDays);
        serviceAndExplore.retainAll(serviceBookingDays);
        serviceAndExplore.retainAll(exploreBookingDays);

        serviceBookingDays.removeAll(serviceAndExplore);
        exploreBookingDays.removeAll(serviceAndExplore);


        calendarView.setEnabled(false);
        calendarView.addDecorator(new TripleEventDecorator(Color.RED, Color.GREEN, Color.BLUE, Triple));
        calendarView.addDecorator(new DualEventDecorator(Color.RED, Color.GREEN, roomAndService));
        calendarView.addDecorator(new DualEventDecorator(Color.RED, Color.BLUE, roomAndExplore));
        calendarView.addDecorator(new DualEventDecorator(Color.GREEN, Color.BLUE, serviceAndExplore));
        calendarView.addDecorator(new EventDecorator(Color.RED, roomBookingDays));
        calendarView.addDecorator(new EventDecorator(Color.GREEN, serviceBookingDays));
        calendarView.addDecorator(new EventDecorator(Color.BLUE, exploreBookingDays));
    }
    private HashSet<CalendarDay> convertToCalendarDays(List<String> dateStrings) {
        HashSet<CalendarDay> calendarDays = new HashSet<>();

        for (String dateString : dateStrings) {
            String[] parts = dateString.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1;
            int day = Integer.parseInt(parts[2]);
            calendarDays.add(CalendarDay.from(year, month, day));
        }

        return calendarDays;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    private void fragmentImplementation(Fragment fragment, String title, List<Booking> bookings){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameBookingPage, fragment);
        fragmentTransaction.commit();
        BookingPageFragment.title = title;
        BookingPageFragment.bookingList = bookings;
    }
    private void bottomNav(){
        navServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ServicesPage.class);
                startActivity(intent);
            }
        });
        navExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExplorePage.class);
                startActivity(intent);
            }
        });
        navRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intent);
            }
        });
    }
}

