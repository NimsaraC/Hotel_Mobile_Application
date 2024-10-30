package com.android.luxevista.userPages;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.luxevista.Booking;
import com.android.luxevista.R;
import com.android.luxevista.Room;
import com.android.luxevista.User;
import com.android.luxevista.database.BookingDB;
import com.android.luxevista.database.RoomDB;
import com.android.luxevista.database.UserDB;

public class RoomBookingPage extends AppCompatActivity {
    private TextView txtRoomType, txtBedType, txtRoomSize, txtRoomPrice, txtNumberOfNights, txtCancel, txtTotalPrice, txtFee, txtFinalPrice;
    private CalendarView calendarView;
    private EditText edtCheckIn, edtCheckOut, edtName, edtEmail, edtPhone;
    private CheckBox checkbox;
    private LinearLayout btnPayNow, btnBack, btnProfile;
    private ImageView coverImage;
    private RoomDB roomDB;
    private UserDB userDB;
    private BookingDB bookingDB;
    private Room room;
    private Booking booking;
    private User user;

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

        roomDB = new RoomDB(this);
        bookingDB = new BookingDB(this);
        userDB = new UserDB(this);

        findComponents();


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