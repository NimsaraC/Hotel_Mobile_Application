package com.android.luxevista.userPages;

import android.os.Bundle;
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

public class SpaServiceDetailsPage extends AppCompatActivity {

    private TextView txtTitle, txtPrice, txtDescription, txtDuration, txtBooking, txtCancel;
    private LinearLayout btnBookNow;
    private ImageView coverImage;
    private ServicesDB db;
    private LuxeService service;
    private int serviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spa_service_details_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new ServicesDB(this);

        txtTitle = findViewById(R.id.txtTitle);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtDescription);
        txtDuration = findViewById(R.id.txtDuration);
        txtBooking = findViewById(R.id.txtBooking);
        txtCancel = findViewById(R.id.txtCancel);
        btnBookNow = findViewById(R.id.btnBookNow);
        coverImage = findViewById(R.id.coverImage);

        serviceId = getIntent().getIntExtra("serviceId", 0);

        setServiceDetails();
    }
    private void setServiceDetails(){
        service = db.getServiceById(serviceId);

        txtTitle.setText(service.getServiceTitle());
        txtPrice.setText(String.format("$%s for 60 minutes", service.getPrice()));
        txtDescription.setText(service.getServiceDescription());
        txtDuration.setText(String.format("%s minutes", service.getDuration()));
        //txtDuration.setText(service.getDuration());
        txtBooking.setText(service.getBookingInstruction());
        txtCancel.setText(service.getCancellationPolicy());

        Picasso.get()
                .load(service.getCoverImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.home_screen)
                .into(coverImage);
    }
}