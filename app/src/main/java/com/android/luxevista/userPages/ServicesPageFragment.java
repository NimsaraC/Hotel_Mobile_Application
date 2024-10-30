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

import com.android.luxevista.LuxeService;
import com.android.luxevista.R;
import com.android.luxevista.Room;
import com.android.luxevista.adapter.HomePageRoomAdapter;
import com.android.luxevista.adapter.HomePageServiceAdapter;

import java.util.List;

public class ServicesPageFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomePageServiceAdapter adapter;
    private Context context;
    static List<LuxeService> serviceList;
    private TextView txttitle;
    public static String title = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services_page, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new HomePageServiceAdapter(getContext(), serviceList);
        recyclerView.setAdapter(adapter);

        txttitle = view.findViewById(R.id.serviceTitle);
        txttitle.setText(title);

        return  view;
    }
}