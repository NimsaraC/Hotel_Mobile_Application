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
import com.android.luxevista.LuxeService;
import com.android.luxevista.R;
import com.android.luxevista.SharedPreference;
import com.android.luxevista.database.BookingDB;
import com.android.luxevista.database.ServicesDB;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class DiningBookingView extends AppCompatActivity {
    private TextView txtBookingId, txtTitle, txtServiceType, txtDate, txtGuests, txtPrice, txtServiceId;
    private LinearLayout btnCancel, btnBack, btnProfile;
    private ImageView barcodeImageView;
    private BookingDB bookingDB;
    private ServicesDB servicesDB;
    private Booking booking;
    private LuxeService service;
    private int bookingId, serviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dining_booking_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        servicesDB = new ServicesDB(this);
        bookingDB = new BookingDB(this);

        bookingId = getIntent().getIntExtra("bookingId", 0);
        booking = bookingDB.getBookingById(bookingId);
        serviceId = Integer.parseInt(booking.getServiceId());
        service = servicesDB.getServiceById(serviceId);

        findViews();
        setServiceData();
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
                Intent intent = new Intent(DiningBookingView.this, ProfilePage.class);
                startActivity(intent);
            }
        });
    }
    private void cancelButton() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DiningBookingView.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to cancel this booking?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreference sharedPreference = new SharedPreference();
                        sharedPreference.clearPref(DiningBookingView.this);
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

    private void setServiceData() {
        txtBookingId.setText(String.format("Booking Id: %s", booking.getBookingId()));
        txtServiceType.setText(service.getServiceType());
        txtDate.setText(booking.getBookingDate());
        txtGuests.setText(String.format(booking.getNumberOfGuests()));
        double total = service.getPrice();
        double tax = (total * booking.getTaxes())/100;
        double totalPrice = total + tax;
        txtPrice.setText(String.format("$ %.2f", totalPrice));
        txtServiceId.setText(String.format("Service Id: %s", service.getId()));
        if(booking.getBookingTitle().equals("Explore")){
            txtTitle.setText("Explore Reservation");
        }else{
            txtTitle.setText("Dining Reservation");
        }

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
        txtServiceType = findViewById(R.id.txtServiceType);
        txtDate = findViewById(R.id.txtDate);
        txtGuests = findViewById(R.id.txtGuests);
        txtPrice = findViewById(R.id.txtPrice);
        txtServiceId = findViewById(R.id.txtServiceId);
        btnCancel = findViewById(R.id.btnCancel);
        btnBack = findViewById(R.id.btnBack);
        btnProfile = findViewById(R.id.btnProfile);
        barcodeImageView = findViewById(R.id.barcodeImageView);
        txtTitle = findViewById(R.id.textView);
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