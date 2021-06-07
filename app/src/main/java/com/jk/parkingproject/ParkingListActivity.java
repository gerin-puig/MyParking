package com.jk.parkingproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.jk.parkingproject.ui.main.SectionsPagerAdapter;

public class ParkingListActivity extends AppCompatActivity {

    private String currentUserEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: hide action bar
        setContentView(R.layout.activity_parking_list);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // set selected tabs color
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#FF0000"));
        tabs.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));

        FloatingActionButton fab = findViewById(R.id.fab);

        ParkingSharedPrefs psp = new ParkingSharedPrefs(this);
        currentUserEmail = psp.getCurrentUser();

        //currentUserEmail = getIntent().getStringExtra("email");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParkingListActivity.this, AddNewParking.class);
                //intent.putExtra("email", currentUserEmail);
                startActivity(intent);
            }
        });


    }
}