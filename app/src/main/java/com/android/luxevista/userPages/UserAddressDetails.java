package com.android.luxevista.userPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.luxevista.R;
import com.android.luxevista.User;
import com.android.luxevista.database.UserDB;

public class UserAddressDetails extends AppCompatActivity {
    private EditText edtAddress, edtCity, edtPostal;
    private Spinner spinnerCountry;
    private LinearLayout btnSave, btnBack, btnProfile;
    private UserDB userDB;
    private User user;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_address_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userId = getIntent().getIntExtra("userId", 0);
        userDB = new UserDB(this);
        user = userDB.getUserById(userId);

        edtAddress = findViewById(R.id.edtAddress);
        edtCity = findViewById(R.id.edtCity);
        edtPostal = findViewById(R.id.edtPostal);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        btnProfile = findViewById(R.id.btnProfile);

        setAddress();
        saveAddress();
        setSpinner();
        navButtons();

    }

    private void navButtons() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PersonalDetailsPage.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                finish();
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setSpinner() {
        String[] countries = getResources().getStringArray(R.array.countries_array);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_selected_item,
                countries
        );

        adapter.setDropDownViewResource(R.layout.spinner_selected_item_list);
        spinnerCountry.setAdapter(adapter);
        if (user.getCounty() != null){
            spinnerCountry.setSelection(adapter.getPosition(user.getCounty()));
        }

    }

    private void setAddress() {
        if(user.getAddress() != null){
            edtAddress.setText(user.getAddress());
            edtCity.setText(user.getCity());
            edtPostal.setText(user.getPostalCode());
        }
            
    }

    private void saveAddress() {

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setAddress(edtAddress.getText().toString());
                user.setCity(edtCity.getText().toString());
                user.setPostalCode(edtPostal.getText().toString());
                user.setCounty(spinnerCountry.getSelectedItem().toString());
                userDB.editUserById(userId, user);
                Toast.makeText(UserAddressDetails.this, "Address updated successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), PersonalDetailsPage.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, PersonalDetailsPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }
}