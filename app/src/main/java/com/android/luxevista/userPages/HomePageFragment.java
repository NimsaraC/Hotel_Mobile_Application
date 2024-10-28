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

import com.android.luxevista.R;
import com.android.luxevista.Room;
import com.android.luxevista.adapter.HomePageRoomAdapter;

import java.util.List;

public class HomePageFragment extends Fragment {

    private TextView txttitle;
    private RecyclerView recyclerView;
    private HomePageRoomAdapter adapter;
    public static String title = "";
    private Context context;
    static List<Room> roomList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new HomePageRoomAdapter(context, roomList);
        recyclerView.setAdapter(adapter);

        txttitle = view.findViewById(R.id.roomTitle);
        txttitle.setText(title);

        return  view;

    }
}