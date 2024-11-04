package com.android.luxevista.adminPages;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.luxevista.R;
import com.android.luxevista.Room;
import com.android.luxevista.adapter.ImageAdapter;
import com.android.luxevista.database.RoomDB;
import com.android.luxevista.userPages.HomePage;
import com.android.luxevista.userPages.Login;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class AddRoomPage extends AppCompatActivity {

    private EditText edtCheckIn, edtCheckOut, edtDescription, edtRoomSize, edtView, edtOccupancy, edtAmenities, edtAdditionalService, edtCancellationPolicy, edtPrice;
    private Spinner roomType, bedType;
    private RecyclerView recyclerView;
    private List<String> imageUris;
    private TextView btnAddCoverImage, btnAddImages;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_IMAGES_REQUEST = 2;
    private Uri imageUri;
    private ImageView imageViewPreview;
    private ImageAdapter imageAdapter;
    private Room room;
    private RoomDB db;
    private LinearLayout btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_room_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new RoomDB(this);
        edtCheckIn = findViewById(R.id.edtCheckIn);
        edtCheckOut = findViewById(R.id.edtCheckOut);
        edtDescription = findViewById(R.id.edtDescription);
        edtRoomSize = findViewById(R.id.edtRoomSize);
        edtView = findViewById(R.id.edtView);
        edtOccupancy = findViewById(R.id.edtOccupancy);
        edtAmenities = findViewById(R.id.edtAmenities);
        edtAdditionalService = findViewById(R.id.edtAdditionalServices);
        edtCancellationPolicy = findViewById(R.id.edtCancellation);
        edtPrice = findViewById(R.id.edtRate);

        roomType = findViewById(R.id.spinnerRoomType);
        bedType = findViewById(R.id.spinnerBedType);

        imageViewPreview = findViewById(R.id.ivCoverImage);

        btnAddCoverImage = findViewById(R.id.btnAddCoverImage);
        btnAddImages = findViewById(R.id.btnAddImages);

        imageUris = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView = findViewById(R.id.recyclerView);
        imageAdapter = new ImageAdapter(imageUris);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(imageAdapter);

        btnAddCoverImage.setOnClickListener(v -> pickImage(false));
        btnAddImages.setOnClickListener(v -> pickImage(true));

        btnAdd = findViewById(R.id.btnAdd);

        timeSelect();
        setupSpinner();
        addButton();

    }

    private void addButton(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRoomData();
            }
        });
    }
    private void addRoomData(){

        if(edtDescription.getText().toString().isEmpty()|| edtRoomSize.getText().toString().isEmpty()
                || edtView.getText().toString().isEmpty()&& edtOccupancy.getText().toString().isEmpty() || edtAmenities.getText().toString().isEmpty()
                || edtAdditionalService.getText().toString().isEmpty() || edtCheckIn.getText().toString().isEmpty()|| edtCheckOut.getText().toString().isEmpty()
                ||edtCancellationPolicy.getText().toString().isEmpty()
                || edtPrice.getText().toString().isEmpty()){
            Toast.makeText(AddRoomPage.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String rType = roomType.getSelectedItem().toString();
        String Description = edtDescription.getText().toString();
        String roomSize = edtRoomSize.getText().toString();
        String bType = bedType.getSelectedItem().toString();
        String view = edtView.getText().toString();
        int occupancy = Integer.parseInt(edtOccupancy.getText().toString());
        String amenities = edtAmenities.getText().toString();
        String additionalService = edtAdditionalService.getText().toString();
        String checkIn = edtCheckIn.getText().toString();
        String checkOut = edtCheckOut.getText().toString();
        String cancellationPolicy = edtCancellationPolicy.getText().toString();
        double price = Double.parseDouble(edtPrice.getText().toString());
        String image = imageUris.get(0);
        String noSmokingPolicy = "No Smoking";
        List<String> images = imageUris;

        room = new Room(
                rType,
                Description,
                roomSize,
                bType,
                view,
                occupancy,
                amenities,
                additionalService,
                checkIn,
                checkOut,
                cancellationPolicy,
                noSmokingPolicy,
                price,
                image,
                images
        );

        Long result = db.insertRoom(room);
        if (result != -1) {
            Toast.makeText(getApplicationContext(), "Room added successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), HomePage.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Room addition failed", Toast.LENGTH_SHORT).show();
        }
    }
    private void pickImage(boolean isMultiple) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (isMultiple) {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        intent.setType("image/*");
        startActivityForResult(intent, isMultiple ? PICK_IMAGES_REQUEST : PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                imageUris.add(String.valueOf(selectedImageUri));
                imageViewPreview.setImageURI(selectedImageUri);
                imageUri = selectedImageUri;
                saveImageToInternalStorage(selectedImageUri);
            }
        } else if (requestCode == PICK_IMAGES_REQUEST && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imageAdapter.notifyDataSetChanged();
                    saveImageToInternalStorage(imageUri);
                }
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                imageUris.add(String.valueOf(imageUri));
                imageAdapter.notifyDataSetChanged();
                saveImageToInternalStorage(imageUri);
            }
        }
    }
    private String saveImageToInternalStorage(Uri imageUri) {
        if (imageUri == null) return "";

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            String filename = UUID.randomUUID().toString() + ".jpg";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Use MediaStore API for Android 11+ (API 30+)
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
                // Save to internal storage directly for Android 10 and below
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

    private void timeSelect(){
        edtCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddRoomPage.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        edtCheckIn.setText(time);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        edtCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddRoomPage.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        edtCheckOut.setText(time);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });


    }

    private void setupSpinner() {
        String[] bedTypes = new String[]{
                "King-Size Bed",
                "Queen-Size Bed",
                "Double Bed",
                "Twin Bed"
        };
        String[] roomTypes = new String[]{
                "Ocean View",
                "Deluxe Garden",
                "Family"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_selected_item,
                bedTypes
        );
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                this,
                R.layout.spinner_selected_item,
                roomTypes
        );

        adapter.setDropDownViewResource(R.layout.spinner_selected_item_list);
        bedType.setAdapter(adapter);
        adapter2.setDropDownViewResource(R.layout.spinner_selected_item_list);
        roomType.setAdapter(adapter2);
    }
}