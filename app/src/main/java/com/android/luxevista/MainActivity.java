package com.android.luxevista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.luxevista.adminPages.AddRoomPage;
import com.android.luxevista.adminPages.AddSpaServicePage;
import com.android.luxevista.adminPages.AdminHomePageTemp;
import com.android.luxevista.userPages.HomePage;
import com.android.luxevista.userPages.Login;
import com.android.luxevista.userPages.PoolServiceDetailsPage;
import com.android.luxevista.userPages.RoomBookingPage;
import com.android.luxevista.userPages.RoomDetailsPage;
import com.android.luxevista.userPages.SpaServiceDetailsPage;

public class MainActivity extends AppCompatActivity {

    private TextView btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn = findViewById(R.id.button1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomePage.class);
                startActivity(intent);
            }
        });

    }
}