package com.android.luxevista.userPages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.luxevista.R;

public class ServicesPageFragment extends Fragment {

    private TextView txttitle;
    public static String title = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services_page, container, false);

        txttitle = view.findViewById(R.id.serviceTitle);
        txttitle.setText(title);

        return  view;
    }
}