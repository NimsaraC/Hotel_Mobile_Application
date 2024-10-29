package com.android.luxevista.userPages;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.luxevista.R;
import com.android.luxevista.Room;
import com.android.luxevista.database.RoomDB;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class RoomDetailsPage extends AppCompatActivity {

    private TextView txtRoomType, txtRoomPrice, txtRoomSize, txtRoomView, txtRoomOccupancy, txtRoomAmenities, txtRoomServices, txtCheckIn, txtRoomCheckOut, txtRoomCancellation, txtRoomSmoking;
    private CalendarView calendarView;
    private ImageView coverImage;
    private LinearLayout btnBookNow;
    private RoomDB roomDB;
    private Room room;
    private int roomId = 0;
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
        calendarView = findViewById(R.id.calendarView);
        coverImage = findViewById(R.id.coverImage);
        btnBookNow = findViewById(R.id.btnBookNow);

        setData();

    }
    private void setData() {
        roomId = getIntent().getIntExtra("roomId", 0);
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

}