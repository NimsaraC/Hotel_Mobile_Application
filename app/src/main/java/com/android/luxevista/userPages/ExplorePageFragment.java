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

import com.android.luxevista.Explore;
import com.android.luxevista.LuxeService;
import com.android.luxevista.R;
import com.android.luxevista.adapter.HomePageExploreAdapter;
import com.android.luxevista.adapter.HomePageServiceAdapter;

import java.util.List;

public class ExplorePageFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomePageExploreAdapter adapter;
    private Context context;
    static List<Explore> exploreList;
    private TextView txttitle;
    public static String title = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore_page, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new HomePageExploreAdapter(context, exploreList);
        recyclerView.setAdapter(adapter);

        txttitle = view.findViewById(R.id.exploreTitle);
        txttitle.setText(title);

        return  view;
    }
}