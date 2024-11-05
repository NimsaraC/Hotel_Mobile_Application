package com.android.luxevista.userPages;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.luxevista.Booking;
import com.android.luxevista.MainActivity;
import com.android.luxevista.R;
import com.android.luxevista.Room;
import com.android.luxevista.SharedPreference;
import com.android.luxevista.database.BookingDB;
import com.android.luxevista.database.RoomDB;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class RoomBookingView extends AppCompatActivity {

    private TextView txtBookingId, txtRoomType, txtDate, txtCheckInDate, txtCheckInTime, txtCheckOutDate, txtCheckOutTime, txtRoomId, Price;
    private ImageView barcodeImageView;
    private LinearLayout btnCancel, btnBack, btnProfile;
    private BookingDB bookingDB;
    private RoomDB roomDB;
    private Booking booking;
    private Room room;
    private int bookingId, roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_room_booking_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        roomDB = new RoomDB(this);
        bookingDB = new BookingDB(this);

        bookingId = getIntent().getIntExtra("bookingId", 0);
        booking = bookingDB.getBookingById(bookingId);
        roomId = Integer.parseInt(booking.getRoomId());
        room = roomDB.getRoomById(roomId);

        barcodeImageView = findViewById(R.id.barcodeImageView);

        findViews();
        setRoomData();
        cancelButton();
        navButtons();

    }

    private void navButtons() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoomBookingView.this, ProfilePage.class);
                startActivity(intent);
            }
        });
    }

    private void cancelButton() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RoomBookingView.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to cancel this booking?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreference sharedPreference = new SharedPreference();
                        sharedPreference.clearPref(RoomBookingView.this);
                        booking.setBookingStatus("canceled");
                        bookingDB.editBookingById(bookingId, booking);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    private void setRoomData() {
        txtBookingId.setText(String.format("Booking Id: %s", booking.getBookingId()));
        txtRoomType.setText(room.getRoomType());
        txtDate.setText(booking.getBookingDate());
        txtCheckInDate.setText(booking.getCheckInDate());
        txtCheckInTime.setText(room.getCheckInTime());
        txtCheckOutDate.setText(booking.getCheckOutDate());
        txtCheckOutTime.setText(room.getCheckOutTime());
        double fee = (booking.getTaxes() * booking.getTotalPrice())/100;
        txtRoomId.setText(String.format("Room Id: %s", room.getRoomId()));
        double finalPrice = booking.getTotalPrice() + fee;
        Price.setText(String.valueOf("$ " + finalPrice));

        String barcodeContent = "BookingId: "+ booking.getBookingId();

        Bitmap barcodeBitmap = generateBarcode(barcodeContent);

        if (barcodeBitmap != null) {
            barcodeImageView.setImageBitmap(barcodeBitmap);
        } else {
            Toast.makeText(this, "Failed to generate barcode", Toast.LENGTH_SHORT).show();
        }
    }

    private void findViews() {
        txtBookingId = findViewById(R.id.txtBookingId);
        txtRoomType = findViewById(R.id.txtRoomType);
        txtDate = findViewById(R.id.txtDate);
        txtCheckInDate = findViewById(R.id.txtCheckInDate);
        txtCheckInTime = findViewById(R.id.txtCheckInTime);
        txtCheckOutDate = findViewById(R.id.txtCheckOutDate);
        txtCheckOutTime = findViewById(R.id.txtCheckOutTime);
        txtRoomId = findViewById(R.id.txtRoomId);
        Price = findViewById(R.id.Price);
        btnCancel = findViewById(R.id.btnCancel);
        btnBack = findViewById(R.id.btnBack);
        btnProfile = findViewById(R.id.btnProfile);

    }

    public Bitmap generateBarcode(String content) {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            return barcodeEncoder.encodeBitmap(content, BarcodeFormat.CODE_128, 600, 300);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

}