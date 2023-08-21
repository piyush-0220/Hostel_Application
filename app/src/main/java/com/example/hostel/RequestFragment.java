package com.example.hostel;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;


public class RequestFragment extends Fragment {

    private Toolbar toolbar;
    private TextView title_toolbar_tv;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public RequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        settoolbar(view);
        tabLayout = view.findViewById(R.id.tab);
        viewPager = view.findViewById(R.id.viewpager);

        ViewpagerAdapter adapter = new ViewpagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }


    private void settoolbar(View view) {
        toolbar = view.findViewById(R.id.toolbar1);
        title_toolbar_tv = view.findViewById(R.id.title_toolbar);
        title_toolbar_tv.setText("Requests");
    }
}