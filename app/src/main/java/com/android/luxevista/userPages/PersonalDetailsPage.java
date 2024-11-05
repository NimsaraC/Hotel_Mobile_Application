package com.android.luxevista.userPages;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.luxevista.R;
import com.android.luxevista.User;
import com.android.luxevista.adminPages.AddRoomPage;
import com.android.luxevista.database.UserDB;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class PersonalDetailsPage extends AppCompatActivity {
    private EditText edtName, edtUsername, edtEmail, edtPhone;
    private TextView txtBirthDay, txtAddress;
    private Spinner spinnerGender;
    private ImageView profilePic, bntAddImage;
    private LinearLayout btnSave, btnBack, btnProfile;
    private UserDB userDB;
    private User user, newUser;
    private int userID;
    private String newImageUrl;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal_details_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userDB = new UserDB(this);
        userID = getIntent().getIntExtra("userId", 0);
        user = userDB.getUserById(userID);

        findViews();
        setUserDetails();
        setSpinner();
        setProfilePic();
        setAddress();
        setBirth();
        saveData();
        navButtons();

    }

    private void setBirth() {
        txtBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PersonalDetailsPage.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                                txtBirthDay.setText(selectedDate);
                                user.setBirthDay(selectedDate);
                                userDB.editUserById(userID, user);
                            }
                        },
                        year, month, day
                );

                datePickerDialog.show();
            }
        });

    }
    private void navButtons() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    private void setAddress() {
        txtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalDetailsPage.this, UserAddressDetails.class);
                intent.putExtra("userId", userID);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveData() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtName.getText().toString().isEmpty() || edtUsername.getText().toString().isEmpty() || edtEmail.getText().toString().isEmpty()){
                    Toast.makeText(PersonalDetailsPage.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                newUser = new User(
                        edtName.getText().toString(),
                        edtUsername.getText().toString(),
                        edtEmail.getText().toString(),
                        user.getPassword(),
                        edtPhone.getText().toString(),
                        newImageUrl,
                        spinnerGender.getSelectedItem().toString(),
                        user.getBirthDay()
                );
                userDB.editUserById(userID, newUser);
                Toast.makeText(PersonalDetailsPage.this, "User updated successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PersonalDetailsPage.this, ProfilePage.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void setProfilePic() {
        if (user.getImageUrl() != null) {
            profilePic.setImageURI(Uri.parse(user.getImageUrl()));
        }else{
            profilePic.setImageResource(R.drawable.not_set);
        }

        bntAddImage.setOnClickListener(v -> pickImage());
    }


    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                profilePic.setImageURI(selectedImageUri);
                user.setImageUrl(selectedImageUri.toString());
                newImageUrl = selectedImageUri.toString();
                saveImageToInternalStorage(selectedImageUri);
            }
        }
    }


    private String saveImageToInternalStorage(Uri imageUri) {
        if (imageUri == null) return "";

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            String filename = UUID.randomUUID().toString() + ".jpg";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/LuxeVista");

                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (uri != null) {
                    try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    }
                    return uri.toString();
                }
            } else {
                File file = new File(getFilesDir(), filename);
                try (OutputStream outputStream = new FileOutputStream(file)) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                }
                return file.getAbsolutePath();
            }

        } catch (IOException e) {
            Toast.makeText(this, "Failed to save image.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return "";
    }

    private void setSpinner() {
        String[] genderTypes = new String[]{
                "Male",
                "Female",
                "Other"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_selected_item,
                genderTypes
        );

        adapter.setDropDownViewResource(R.layout.spinner_selected_item_list);
        spinnerGender.setAdapter(adapter);

        if(user.getGender() != null){
            spinnerGender.setSelection(adapter.getPosition(user.getGender()));
        }
    }

    private void setUserDetails() {
        edtName.setText(user.getName());
        edtUsername.setText(user.getUsername());
        edtEmail.setText(user.getEmail());
        if(user.getPhone() != null){
            edtPhone.setText(user.getPhone());
        }
        if(user.getAddress() != null){
            txtAddress.setText("Edit Address");
        }
        if(user.getBirthDay() != null){
            txtBirthDay.setText(user.getBirthDay());
        }else{
            txtBirthDay.setText("Set BirthDay");
        }
        if(user.getImageUrl() != null){
            Picasso.get()
                    .load(user.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.home_screen)
                    .into(profilePic);
        }

    }

    private void findViews(){
        edtName = findViewById(R.id.edtName);
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        txtBirthDay = findViewById(R.id.txtBirthDay);
        txtAddress = findViewById(R.id.txtAddress);
        spinnerGender = findViewById(R.id.spinnerGender);
        profilePic = findViewById(R.id.profilePic);
        bntAddImage = findViewById(R.id.bntAddImage);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        btnProfile = findViewById(R.id.btnProfile);
    }
}