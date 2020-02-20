package com.example.seminarhall.booking;

import android.os.Bundle;

import com.example.seminarhall.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;


public class Reserve extends AppCompatActivity implements FragmentCalendar.OnFragmentInteractionListener{
    private static final String TAG = "Reserve Class";
    private SectionPageAdapter msectionPageAdapter;
    private ViewPager mViewPager;
    public static String mString="Select Date First";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        Log.d(TAG, "onCreate: ");

        msectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        setUpViewPager();
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment fragment = ((SectionPageAdapter) mViewPager.getAdapter()).getFragment(position);
                try {
                    FragmentTime t = (FragmentTime) fragment;
                    t.onResume();
                } catch (Exception e) {

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void setUpViewPager()
    {
        Log.d(TAG, "setUpViewPager: ");
        FragmentTime fragmentTime = FragmentTime.newInstance(mString);
        msectionPageAdapter.addFragment(new FragmentCalendar(), "Tab 1");
        msectionPageAdapter.addFragment(fragmentTime, "Tab 2");
        msectionPageAdapter.addFragment(new FragmentFinal(), "Tab 3");
        mViewPager.setAdapter(msectionPageAdapter);

    }

    @Override
    public void onFragmentInteraction(String sendBackText) {
        mString=sendBackText;
        msectionPageAdapter.notifyDataSetChanged();
    }


}