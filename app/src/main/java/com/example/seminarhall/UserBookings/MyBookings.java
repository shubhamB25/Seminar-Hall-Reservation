package com.example.seminarhall.UserBookings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.seminarhall.LogIn.SignIn;
import com.example.seminarhall.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyBookings extends AppCompatActivity {
    private static final String TAG = "MyBookings";
    private BookingsPageAdapter adapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
        Log.d(TAG, "onCreate: Started");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setTitle("My Bookings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = findViewById(R.id.toolbarText);
        textView.setText("My Bookings");
        setUpViews();
        UpdateUI(FirebaseAuth.getInstance().getCurrentUser());
    }

    private void setUpViews() {
        adapter = new BookingsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        setUpViewPager();
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void setUpViewPager() {
        Log.d(TAG, "setUpViewPager: ");
        adapter.addFragment(new FragmentActive(), "Active Bookings");
        adapter.addFragment(new FragmentClosed(), "Closed Bookings");
        mViewPager.setAdapter(adapter);
    }


    private void UpdateUI(FirebaseUser currentUser) {
        if (currentUser == null) {
            Intent intent = new Intent(getApplicationContext(), SignIn.class);
            startActivity(intent);
        }
    }


}
