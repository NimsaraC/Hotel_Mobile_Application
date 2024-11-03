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

import com.android.luxevista.R;
import com.android.luxevista.Room;
import com.android.luxevista.adapter.ImageCarouselAdapter;
import com.android.luxevista.database.RoomDB;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePage extends AppCompatActivity {


    // Banner
    private ViewPager2 viewPager;
    private ImageCarouselAdapter adapter;
    private Handler sliderHandler = new Handler(Looper.getMainLooper());

    //Main

    private TabLayout tabLayout;
    private LinearLayout navRooms, navServices, navExplore, navProfile, btnProfile;
    private boolean doubleBackToExitPressedOnce = false;
    private RoomDB roomDB;
    private Room room;
    private List<Room> roomList;
    private List<Room> selectedRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        roomDB = new RoomDB(this);

        tabLayout = findViewById(R.id.tabLayout);

        navServices = findViewById(R.id.linearLayoutServices);
        navExplore = findViewById(R.id.linearLayoutExplore);
        navRooms = findViewById(R.id.linearLayoutRooms);
        navProfile = findViewById(R.id.linearLayoutProfile);
        btnProfile = findViewById(R.id.btnProfile);

        bottomNav();
        banners();

        roomList = roomDB.getAllRooms();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fragmentImplementation(new HomePageFragment(),"Ocean View", getRooms("Ocean View"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition() == 0){
                    fragmentImplementation(new HomePageFragment(), "Ocean View", getRooms("Ocean View"));
                }
                if(tab.getPosition() == 1){
                    fragmentImplementation(new HomePageFragment(), "Deluxe Garden", getRooms("Deluxe Garden"));
                }
                if(tab.getPosition() == 2){
                    fragmentImplementation(new HomePageFragment(), "Family", getRooms("Family"));
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

    private List<Room> getRooms(String type) {
        List<Room> roomList = roomDB.getAllRooms();
        List<Room> selectedRooms = new ArrayList<>();

        for (Room room : roomList) {
            if (room.getRoomType().equals(type)) {
                selectedRooms.add(room);
            }
        }

        return selectedRooms;
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    private void fragmentImplementation(Fragment fragment, String title, List<Room> selectedRooms){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameHomepage, fragment);
        fragmentTransaction.commit();
        HomePageFragment.title = title;
        HomePageFragment.roomList = selectedRooms;
    }

    private void bottomNav(){
        navServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ServicesPage.class);
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
                "banner_1",
                "banner_2",
                "banner_3"
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