package com.android.luxevista.userPages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.android.luxevista.LuxeService;
import com.android.luxevista.R;
import com.android.luxevista.adapter.ImageCarouselAdapter;
import com.android.luxevista.database.ServicesDB;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServicesPage extends AppCompatActivity {

    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private LinearLayout navRooms, navServices, navExplore, navProfile, btnProfile;
    private LuxeService service;
    private TextView edtSearch;

    private ServicesDB db;
    private List<LuxeService> serviceList;
    private ViewPager2 viewPager;
    private ImageCarouselAdapter adapter;
    private Handler sliderHandler = new Handler(Looper.getMainLooper());

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
        edtSearch = findViewById(R.id.edtSearch);

        edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ServicesPage.this, "Soon", Toast.LENGTH_SHORT).show();
            }
        });

        bottomNav();
        banners();

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
    private void banners(){

        List<String> imagePaths = Arrays.asList(
                "banner_4",
                "banner_5",
                "banner_6"
        );

        List<Uri> newImagePaths = new ArrayList<>();
        for (String imageName : imagePaths) {
            int resourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());
            if (resourceId != 0) {
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + resourceId);
                newImagePaths.add(uri);
            } else {
                Log.e("ResourceError", "Drawable resource not found for name: " + imageName);
            }
        }
        viewPager = findViewById(R.id.viewPager);
        adapter = new ImageCarouselAdapter(this, newImagePaths);
        viewPager.setAdapter(adapter);

        startAutoSlide();
    }

    private void startAutoSlide() {
        sliderHandler.removeCallbacks(sliderRunnable);
        sliderHandler.postDelayed(sliderRunnable, 5000);
    }

    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int nextItem = (viewPager.getCurrentItem() + 1) % adapter.getItemCount();
            viewPager.setCurrentItem(nextItem, true);
            sliderHandler.postDelayed(this, 5000);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAutoSlide();
    }
}