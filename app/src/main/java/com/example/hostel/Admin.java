package com.example.hostel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class Admin extends AppCompatActivity {
    MeowBottomNavigation mbn;
    private int model_item_id = 1;
    SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "Login";
    private static final String KEY_EMAIL = "email_a";

    private String admin_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //getemail
        sharedPreferences = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);

        admin_email = sharedPreferences.getString(KEY_EMAIL, null);
        if (admin_email == null) {
            startActivity(new Intent(Admin.this, Login.class));
            finish();
        }

        mbn = findViewById(R.id.bottomNavigationView);
        mbn.add(new MeowBottomNavigation.Model(1, R.drawable.baseline_home_24));
        mbn.add(new MeowBottomNavigation.Model(2, R.drawable.rupee));
        mbn.add(new MeowBottomNavigation.Model(3, R.drawable.email));
        mbn.add(new MeowBottomNavigation.Model(4, R.drawable.gallery));
        mbn.add(new MeowBottomNavigation.Model(5, R.drawable.dp));

        mbn.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

            }
        });
        mbn.show(1, true);
        loadFragment(new HomeFragment());
        mbn.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                model_item_id = item.getId();
                if (model_item_id == 1) {
                    HomeFragment homeFragment = new HomeFragment();
                    loadFragment(homeFragment);
                } else if (model_item_id == 2) {
                    loadFragment(new FeeFragment());
                } else if (item.getId() == 3) {
                    loadFragment(new RequestFragment());
                } else if (model_item_id == 4) {
                    loadFragment(new GalleryFragment());

                } else if (model_item_id == 5) {
                    loadFragment(new ProfileFragment());
                }
            }
        });
        mbn.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });
    }

    // Send data From Parent Activity to chil Fragment
    public Bundle getMyData() {
        Bundle bundle = new Bundle();
        bundle.putString("email_key", admin_email);
        return bundle;
    }

    public void loadFragment(Fragment fm) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frame_layout, fm);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (model_item_id == 1) {
            super.onBackPressed();
            finish();

        } else {
            mbn.show(1, true);
            loadFragment(new HomeFragment());
            model_item_id = 1;

        }

    }

}