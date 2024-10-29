package com.android.luxevista;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.android.luxevista.adapter.ImageCarouselAdapter;
import com.android.luxevista.database.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class YourActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ImageCarouselAdapter adapter;
    private List<Uri> imageUriList;
    private RoomDB db;
    private Handler sliderHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your);

        db = new RoomDB(this);
        int id = 2;
        Room room = db.getRoomById(id);

        imageUriList = new ArrayList<>();
        List<String> imagePaths = room.getAdditionalImages();
        for (String path : imagePaths) {
            path = path.replace("[", "").replace("]", "").trim();
            imageUriList.add(Uri.parse(path));
        }

        viewPager = findViewById(R.id.viewPager);
        adapter = new ImageCarouselAdapter(this, imageUriList);
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
