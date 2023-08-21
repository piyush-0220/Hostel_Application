package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class viewPager_user_fragmentAdapter extends FragmentPagerAdapter {

    public viewPager_user_fragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new user_leave_fragment();
        } else if (position == 1) {
            return new user_service_fragment();
        } else
            return new user_support_fragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Leave";
        } else if (position == 1) {
            return "Service";
        } else
            return "Support";
    }

}
