package com.android.luxevista.userPages;

import static com.android.luxevista.SharedPreference.USER_ID;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.luxevista.MainActivity;
import com.android.luxevista.R;
import com.android.luxevista.SharedPreference;
import com.android.luxevista.User;
import com.android.luxevista.database.UserDB;
import com.squareup.picasso.Picasso;

public class ProfilePage extends AppCompatActivity {

    private LinearLayout navRooms, navServices, navExplore, navBooking, btnSignout, btnProfile;
    private TextView profileName, profileEmail;
    private ImageView profilePic;
    private UserDB db;
    private User user;
    private Switch darkModeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new UserDB(this);
        SharedPreference sharedPreference = new SharedPreference();
        int userID = sharedPreference.GetInt(this, USER_ID);

        navServices = findViewById(R.id.linearLayoutServices);
        navExplore = findViewById(R.id.linearLayoutExplore);
        navRooms = findViewById(R.id.linearLayoutRooms);
        navBooking = findViewById(R.id.linearLayoutProfile);

        btnSignout = findViewById(R.id.btnSignout);
        btnProfile = findViewById(R.id.btnProfile);

        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profilePic = findViewById(R.id.profilePic);

        setUserData(userID);
        bottomNav();
        signOut();
        buttons(userID);
        themeChange();
    }

    private void themeChange() {
        darkModeSwitch = findViewById(R.id.darkModeSwitch);

        // Load saved dark mode preference
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);
        darkModeSwitch.setChecked(isDarkMode);
        setDarkMode(isDarkMode);

        // Listen for toggle changes
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save preference
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isDarkMode", isChecked);
            editor.apply();

            // Apply the theme change
            setDarkMode(isChecked);
        });
    }
    private void setDarkMode(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void buttons(int userID) {
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePage.this, PersonalDetailsPage.class);
                intent.putExtra("userId", userID);
                startActivity(intent);
            }
        });
    }

    private void signOut() {
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Use the activity context instead of getApplicationContext()
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePage.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to log out?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreference sharedPreference = new SharedPreference();
                        sharedPreference.clearPref(ProfilePage.this);
                        Intent intent = new Intent(ProfilePage.this, MainActivity.class);
                        startActivity(intent);
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

    private void setUserData(int userID){
        user = db.getUserById(userID);
        profileName.setText(user.getUsername());
        profileEmail.setText(user.getEmail());
        if(user.getImageUrl() != null){
            Picasso.get()
                    .load(user.getImageUrl())
                    .placeholder(R.drawable.home_screen)
                    .error(R.drawable.ic_launcher_background)
                    .into(profilePic);
        }else {
            profilePic.setImageResource(R.drawable.not_set);
        }
    }
    private void bottomNav(){
        navServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ServicesPage.class);
                startActivity(intent);
                finish();
            }
        });
        navExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExplorePage.class);
                startActivity(intent);
                finish();
            }
        });
        navBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookingPage.class);
                startActivity(intent);
                finish();
            }
        });
        navRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}