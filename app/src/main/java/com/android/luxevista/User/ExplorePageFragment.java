package com.android.luxevista.User;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.luxevista.R;

public class ExplorePageFragment extends Fragment {

    private TextView txttitle;
    public static String title = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore_page, container, false);

        txttitle = view.findViewById(R.id.exploreTitle);
        txttitle.setText(title);

        return  view;
    }
}