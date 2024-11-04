package com.android.luxevista.userPages;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.luxevista.Booking;
import com.android.luxevista.R;
import com.android.luxevista.adapter.BookingPageAdapter;
import com.android.luxevista.adapter.HomePageRoomAdapter;

import java.util.List;

public class BookingPageFragment extends Fragment {

    private TextView txttitle;
    public static String title = "";
    private RecyclerView recyclerView;
    private Context context;
    static List<Booking> bookingList;
    private BookingPageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_page, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new BookingPageAdapter(getContext(), bookingList);
        recyclerView.setAdapter(adapter);

        txttitle = view.findViewById(R.id.bookingTitle);
        txttitle.setText(title);

        return  view;
    }
}