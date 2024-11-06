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

import com.android.luxevista.Explore;
import com.android.luxevista.R;
import com.android.luxevista.database.ExploreDB;
import com.android.luxevista.database.ServicesDB;
import com.squareup.picasso.Picasso;

public class ExploreDetailsPage extends AppCompatActivity {
    private TextView txtTitle, txtPrice, txtDescription, txtDuration, txtBooking, txtNotes;
    private ImageView coverImage;
    private LinearLayout btnBookNow;
    private ExploreDB db;
    private Explore explore;
    private int exploreId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_explore_details_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new ExploreDB(this);

        txtTitle = findViewById(R.id.txtTitle);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtDescription);
        txtDuration = findViewById(R.id.txtDuration);
        txtBooking = findViewById(R.id.txtBooking);
        txtNotes = findViewById(R.id.txtNotes);
        btnBookNow = findViewById(R.id.btnBookNow);
        coverImage = findViewById(R.id.coverImage);

        exploreId = getIntent().getIntExtra("exploreId", 0);

        setServiceDetails();

        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExploreBookingPage.class);
                intent.putExtra("exploreId", exploreId);
                startActivity(intent);
            }
        });
    }
    private void setServiceDetails(){
        explore = db.getExploreById(exploreId);

        txtTitle.setText(explore.getType());
        txtPrice.setText(String.format("$%s per person", explore.getPrice()));
        txtDescription.setText(explore.getDescription());
        txtDuration.setText(explore.getDuration());
        txtBooking.setText(explore.getBookingDetails());
        txtNotes.setText(explore.getSpecialNote());

        String imageUrl = explore.getCoverImage();

        if (imageUrl.startsWith("drawable/")) {
            String drawableName = imageUrl.replace("drawable/", "").replace(".jpg", "");
            int drawableResId = this.getResources().getIdentifier(drawableName, "drawable", this.getPackageName());

            if (drawableResId != 0) {
                coverImage.setImageResource(drawableResId);
            } else {
                coverImage.setImageResource(R.drawable.home_screen);
            }
        }else{
            Picasso.get()
                    .load(explore.getCoverImage())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.home_screen)
                    .into(coverImage);
        }
    }
}