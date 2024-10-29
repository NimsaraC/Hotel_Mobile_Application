package com.android.luxevista.adminPages;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.luxevista.LuxeService;
import com.android.luxevista.R;
import com.android.luxevista.adapter.ImageAdapter;
import com.android.luxevista.database.ServicesDB;
import com.android.luxevista.userPages.HomePage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddDiningServicePage extends AppCompatActivity {

    private EditText edtBooking, edtDescription, edtCuisine, edtReservation, edtCancellationPolicy;
    private Spinner serviceType;
    private RecyclerView recyclerView;
    private List<String> imageUris;
    private TextView btnAddCoverImage, btnAddImages;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_IMAGES_REQUEST = 2;
    private Uri imageUri;
    private ImageView imageViewPreview;
    private ImageAdapter imageAdapter;
    private LuxeService lService;
    private ServicesDB db;
    private LinearLayout btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_dining_service_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new ServicesDB(this);
        edtDescription = findViewById(R.id.edtDescription);
        edtCuisine = findViewById(R.id.edtCuisine);
        edtCancellationPolicy = findViewById(R.id.edtCancellation);
        edtReservation = findViewById(R.id.edtReservation);
        edtBooking = findViewById(R.id.edtBooking);

        serviceType = findViewById(R.id.spinnerServiceType);

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

        setupSpinner();
        addButton();
    }
    private void addButton(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addServiceData();
            }
        });
    }

    private void addServiceData(){
        if(edtDescription.getText().toString().isEmpty() || edtReservation.getText().toString().isEmpty()
                || edtCuisine.getText().toString().isEmpty() || edtCancellationPolicy.getText().toString().isEmpty()
                || edtBooking.getText().toString().isEmpty()){
            Toast.makeText(AddDiningServicePage.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        String sType = serviceType.getSelectedItem().toString();
        String description = edtDescription.getText().toString();
        String cuisine = edtCuisine.getText().toString();
        String cancellationPolicy = edtCancellationPolicy.getText().toString();
        String reservation = edtReservation.getText().toString();
        String booking = edtBooking.getText().toString();
        String title = "Dining service";
        String image = imageUris.get(0);
        List<String> images = imageUris;

        lService = new LuxeService(
                title,
                sType,
                description,
                cuisine,
                reservation,
                images,
                image,
                cancellationPolicy,
                booking
        );
        Long result = db.insertService(lService);
        if (result != -1) {
            Toast.makeText(getApplicationContext(), "Service added successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), HomePage.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Service addition failed", Toast.LENGTH_SHORT).show();
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
                return file.getAbsolutePath();  // Return the internal file path
            }

        } catch (IOException e) {
            Toast.makeText(this, "Failed to save image.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return "";
    }

    private void setupSpinner() {
        String[] serviceTypes = new String[]{
                "Seaside Grill",
                "Sky Lounge"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_selected_item,
                serviceTypes
        );

        adapter.setDropDownViewResource(R.layout.spinner_selected_item_list);
        serviceType.setAdapter(adapter);
    }
}