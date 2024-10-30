package com.android.luxevista.userPages;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.luxevista.Booking;
import com.android.luxevista.R;
import com.android.luxevista.Room;
import com.android.luxevista.User;
import com.android.luxevista.adminPages.AddRoomPage;
import com.android.luxevista.database.BookingDB;
import com.android.luxevista.database.RoomDB;
import com.android.luxevista.database.UserDB;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class RoomBookingPage extends AppCompatActivity {
    private TextView edtCheckIn, edtCheckOut, txtRoomType, txtBedType, txtRoomSize, txtRoomPrice, txtNumberOfNights, txtCancel, txtTotalPrice, txtFee, txtFinalPrice;
    private CalendarView calendarView;
    private EditText  edtName, edtEmail, edtPhone, edtSpecialReq;
    private Calendar checkInCalendar, checkOutCalendar;
    private CheckBox checkbox;
    private LinearLayout btnPayNow, btnBack, btnProfile;
    private ImageView coverImage;
    private RoomDB roomDB;
    private UserDB userDB;
    private BookingDB bookingDB;
    private Room room;
    private Booking booking;
    private User user;
    private int roomId = 0, userId =0;
    private boolean isCheckInSelected = false, isChecked = false;
    private double totalPrice =0, finalPrice=0, tax =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_room_booking_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // TODO: 10/31/2024 need to set userId to userdata and booking add db
        roomDB = new RoomDB(this);
        bookingDB = new BookingDB(this);
        userDB = new UserDB(this);

        roomId = getIntent().getIntExtra("roomId",0);

        checkInCalendar = Calendar.getInstance();
        checkOutCalendar = Calendar.getInstance();

        findComponents();
        setRoomDetails();
        setGuestDetails();
        pickDates();
        payNowButton();

    }

    private void pickDates(){
        edtCheckIn.setOnClickListener(v -> showDatePickerDialog(checkInCalendar, edtCheckIn, true));

        edtCheckOut.setOnClickListener(v -> {
            checkOutCalendar.setTime(checkInCalendar.getTime());
            checkOutCalendar.add(Calendar.DAY_OF_YEAR, 1);
            showDatePickerDialog(checkOutCalendar, edtCheckOut, false);
        });
    }
    private void showDatePickerDialog(Calendar calendar, TextView textView, boolean isCheckIn) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    textView.setText(sdf.format(calendar.getTime()));

                    if (isCheckIn) {
                        isCheckInSelected = true;
                        checkOutCalendar.setTime(calendar.getTime());
                        checkOutCalendar.add(Calendar.DAY_OF_YEAR, 1);
                        edtCheckOut.setText("Check-Out");
                        txtNumberOfNights.setText("0 Nights");
                    } else {
                        calculateAndDisplayNights();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void calculateAndDisplayNights() {
        room = roomDB.getRoomById(roomId);

        if (isCheckInSelected && edtCheckOut.getText().toString().contains("-")) {
            long diffInMillis = checkOutCalendar.getTimeInMillis() - checkInCalendar.getTimeInMillis();
            long nights = TimeUnit.MILLISECONDS.toDays(diffInMillis);
            txtNumberOfNights.setText(nights + (nights == 1 ? " Night" : " Nights"));
            totalPrice = room.getRate()* nights;
            tax = (totalPrice*6)/100;
            finalPrice = totalPrice + tax;
            txtTotalPrice.setText(String.format("$%s", totalPrice));
            txtFee.setText(String.format("$%s", tax));
            txtFinalPrice.setText(String.format("$%s", finalPrice));

        }
    }
    private void setGuestDetails(){
        user = userDB.getUserById(1);

        edtName.setText(user.getName());
        edtEmail.setText(user.getEmail());
        if(user.getPhone() != null){
            edtPhone.setText(user.getPhone());
        }
    }
    private void setRoomDetails(){

        room = roomDB.getRoomById(roomId);

        txtRoomPrice.setText(String.format("$%s /Night", room.getRate()));
        txtRoomType.setText(room.getRoomType());
        txtBedType.setText(room.getBedType());
        txtRoomSize.setText(room.getRoomSize());
        txtCancel.setText(room.getCancellationPolicy());

        Picasso.get()
                .load(room.getCoverImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.home_screen)
                .into(coverImage);

    }
    private void payNowButton(){
        isChecked = checkbox.isChecked();
        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtName.getText().toString().isEmpty()||edtEmail.getText().toString().isEmpty()||
                        edtPhone.getText().toString().isEmpty()|| edtCheckIn.getText().toString().isEmpty()||
                        edtCheckOut.getText().toString().isEmpty()){
                    Toast.makeText(RoomBookingPage.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!checkbox.isChecked()){
                    Toast.makeText(RoomBookingPage.this, "Please Accept the therms and condition", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(RoomBookingPage.this, "done", Toast.LENGTH_SHORT).show();
                String request = "No Special Request";
                if(!edtSpecialReq.getText().toString().isEmpty()){
                    request = edtSpecialReq.getText().toString();
                }

                booking = new Booking(
                        "room",
                        txtRoomType.getText().toString(),
                        edtCheckIn.getText().toString(),
                        edtCheckOut.getText().toString(),
                        edtCheckIn.getText().toString(),
                        "confirmed",
                        request,
                        (totalPrice + tax),
                        tax,
                        edtName.getText().toString(),
                        edtEmail.getText().toString(),
                        edtPhone.getText().toString(),
                        "1"
                );

                Long result = bookingDB.insertBooking(booking);
                if (result != -1) {
                    Toast.makeText(getApplicationContext(), "Booking successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Booking failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void findComponents(){
        txtRoomPrice = findViewById(R.id.txtRoomPrice);
        txtRoomType = findViewById(R.id.txtRoomType);
        txtBedType = findViewById(R.id.txtBedType);
        txtRoomSize = findViewById(R.id.txtRoomSize);
        txtNumberOfNights = findViewById(R.id.txtNumberOfNights);
        txtCancel = findViewById(R.id.txtCancel);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        txtFee = findViewById(R.id.txtFee);
        txtFinalPrice = findViewById(R.id.txtFinalPrice);
        calendarView = findViewById(R.id.calendarView);
        edtCheckIn = findViewById(R.id.edtCheckIn);
        edtCheckOut = findViewById(R.id.edtCheckOut);
        edtSpecialReq = findViewById(R.id.edtSpecialReq);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        checkbox = findViewById(R.id.checkbox);
        btnPayNow = findViewById(R.id.btnPayNow);
        btnBack = findViewById(R.id.btnBack);
        btnProfile = findViewById(R.id.btnProfile);
        coverImage = findViewById(R.id.coverImage);
    }
}