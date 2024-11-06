package com.android.luxevista;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.Manifest;

import com.android.luxevista.adminPages.AddDiningServicePage;
import com.android.luxevista.adminPages.AddPoolServicePage;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn = findViewById(R.id.button1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

    }
}