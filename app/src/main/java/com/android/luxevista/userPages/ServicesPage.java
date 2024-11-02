package com.android.luxevista.userPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.luxevista.LuxeService;
import com.android.luxevista.R;
import com.android.luxevista.database.ServicesDB;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ServicesPage extends AppCompatActivity {

    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private LinearLayout navRooms, navServices, navExplore, navProfile, btnProfile;
    private LuxeService service;
    private ServicesDB db;
    private List<LuxeService> serviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_services_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new ServicesDB(this);
        serviceList = db.getAllServices();

        tabLayout = findViewById(R.id.tabLayoutService);
        frameLayout = findViewById(R.id.frameServicePage);

        navServices = findViewById(R.id.linearLayoutServices);
        navExplore = findViewById(R.id.linearLayoutExplore);
        navRooms = findViewById(R.id.linearLayoutRooms);
        navProfile = findViewById(R.id.linearLayoutProfile);
        btnProfile = findViewById(R.id.btnProfile);

        bottomNav();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fragmentImplementation(new ServicesPageFragment(),"Spa & Wellness", getServices("Spa service"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition() == 0){
                    fragmentImplementation(new ServicesPageFragment(), "Spa & Wellness", getServices("Spa service"));
                }
                if(tab.getPosition() == 1){
                    fragmentImplementation(new ServicesPageFragment(), "Dining Reservations", getServices("Dining service"));
                }
                if(tab.getPosition() == 2){
                    fragmentImplementation(new ServicesPageFragment(), "Poolside Cabanas", getServices("Pool service"));
                    //Toast.makeText(HomePage.this, "Room3", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private List<LuxeService> getServices(String type){
        List<LuxeService> selectedServices = new ArrayList<>();
        for (LuxeService service : serviceList){
            if (service.getServiceTitle().equals(type)){
                selectedServices.add(service);
            }
        }

        return selectedServices;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    private void fragmentImplementation(Fragment fragment, String title, List<LuxeService> selectedServices){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameServicePage, fragment);
        fragmentTransaction.commit();
        ServicesPageFragment.title = title;
        ServicesPageFragment.serviceList = selectedServices;
    }

    private void bottomNav(){
        navRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });
        navExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExplorePage.class);
                startActivity(intent);
            }
        });
        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookingPage.class);
                startActivity(intent);
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intent);
            }
        });
    }
}