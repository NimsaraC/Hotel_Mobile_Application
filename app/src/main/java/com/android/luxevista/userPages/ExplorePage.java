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

import com.android.luxevista.Explore;
import com.android.luxevista.R;
import com.android.luxevista.database.ExploreDB;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ExplorePage extends AppCompatActivity {

    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private LinearLayout navRooms, navServices, navProfile, btnProfile;
    private Explore explore;
    private ExploreDB db;
    private List<Explore> exploreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_explore_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        db = new ExploreDB(this);
        exploreList = db.getAllExplores();

        tabLayout = findViewById(R.id.tabLayoutService);
        frameLayout = findViewById(R.id.frameExplorePage);

        navServices = findViewById(R.id.linearLayoutServices);
        navRooms = findViewById(R.id.linearLayoutRooms);
        navProfile = findViewById(R.id.linearLayoutProfile);
        btnProfile = findViewById(R.id.btnProfile);

        bottomNav();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fragmentImplementation(new ExplorePageFragment(),"Local Attractions", getExploreList("Local Attractions"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition() == 0){
                    fragmentImplementation(new ExplorePageFragment(), "Local Attractions", getExploreList("Local Attractions"));
                }
                if(tab.getPosition() == 1){
                    fragmentImplementation(new ExplorePageFragment(), "Water Activities", getExploreList("Water Activities"));
                }
                if(tab.getPosition() == 2){
                    fragmentImplementation(new ExplorePageFragment(), "Dining & Nightlife", getExploreList("Dining & Nightlife"));
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
    private List<Explore> getExploreList(String type){
        List<Explore> selectedExplore = new ArrayList<>();
        for(Explore explore : exploreList){
            if(explore.getTitle().equals(type)){
                selectedExplore.add(explore);
            }
        }
        return selectedExplore;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    private void fragmentImplementation(Fragment fragment, String title, List<Explore> exploreList){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameExplorePage, fragment);
        fragmentTransaction.commit();
        ExplorePageFragment.title = title;
        ExplorePageFragment.exploreList = exploreList;
    }

    private void bottomNav(){
        navRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });
        navServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ServicesPage.class);
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