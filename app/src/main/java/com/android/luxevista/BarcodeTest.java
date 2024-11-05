package com.android.luxevista;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class BarcodeTest extends AppCompatActivity {
    String barcodeContent = "Tharushsa"; // Replace with the data you want to encode
    Bitmap barcodeBitmap = generateBarcode(barcodeContent);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_barcode_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView barcodeImageView = findViewById(R.id.barcodeImageView);

        if (barcodeBitmap != null) {
            barcodeImageView.setImageBitmap(barcodeBitmap);
        } else {
            Toast.makeText(this, "Failed to generate barcode", Toast.LENGTH_SHORT).show();
        }
    }

    public Bitmap generateBarcode(String content) {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            // Generates a bitmap of the barcode based on the content and format
            return barcodeEncoder.encodeBitmap(content, BarcodeFormat.CODE_128, 600, 300);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}