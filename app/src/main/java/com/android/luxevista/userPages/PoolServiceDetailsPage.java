package com.android.luxevista.userPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.luxevista.LuxeService;
import com.android.luxevista.R;
import com.android.luxevista.database.ServicesDB;
import com.squareup.picasso.Picasso;

public class PoolServiceDetailsPage extends AppCompatActivity {
    private TextView txtTitle, txtPrice, txtHalfPrice, txtDescription, txtCapacity, txtAmenities, txtBooking, txtCancel;
    private LinearLayout btnBookNow;
    private ImageView coverImage;
    private ServicesDB db;
    private LuxeService service;
    private int serviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pool_service_details_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new ServicesDB(this);

        txtTitle = findViewById(R.id.txtTitle);
        txtPrice = findViewById(R.id.txtPrice);
        txtHalfPrice = findViewById(R.id.txtHalfPrice);
        txtCapacity = findViewById(R.id.txtCapacity);
        txtAmenities = findViewById(R.id.txtAmenities);
        txtDescription = findViewById(R.id.txtDescription);
        txtBooking = findViewById(R.id.txtBooking);
        txtCancel = findViewById(R.id.txtCancel);
        coverImage = findViewById(R.id.coverImage);
        btnBookNow = findViewById(R.id.btnBookNow);

        serviceId = getIntent().getIntExtra("serviceId", 0);

        setServiceDetails();

        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PoolServiceDetailsPage.this, PoolServiceBookingPage.class);
                intent.putExtra("serviceId", serviceId);
                startActivity(intent);
            }
        });
    }
    private void setServiceDetails(){
        service = db.getServiceById(serviceId);

        double price = service.getPrice();

        txtTitle.setText(service.getServiceTitle());
        txtHalfPrice.setText(String.format("$%s for half-day", price/2));
        txtPrice.setText(String.format("$%s for full day", price));
        txtDescription.setText(service.getServiceDescription());
        txtCapacity.setText(String.format("Up to %s guests", service.getCapacity()));
        txtAmenities.setText(service.getAmenities());
        txtBooking.setText(service.getBookingInstruction());
        txtCancel.setText(service.getCancellationPolicy());

        Picasso.get()
                .load(service.getCoverImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.home_screen)
                .into(coverImage);
    }
}