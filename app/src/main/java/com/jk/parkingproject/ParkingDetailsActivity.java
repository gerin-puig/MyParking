package com.jk.parkingproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jk.parkingproject.databinding.ActivityParkingDetailsBinding;

public class ParkingDetailsActivity extends AppCompatActivity {

    ActivityParkingDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityParkingDetailsBinding.inflate(getLayoutInflater());
        View view = this.binding.getRoot();
        setContentView(view);

        loadParkingInfoDetails();
    }

    private void loadParkingInfoDetails() {

        this.binding.tvCarNumberParkingDetails.setText("");
        this.binding.tvBuildingCodeParkingDetails.setText("");
        this.binding.tvHouseSuiteNumberParkingDetails.setText("");
        this.binding.tvNoOfHoursParkingDetails.setText("");
        this.binding.tvDateAndTimeOfParkingParkingDetails.setText("");
        this.binding.tvParkingLocationParkingDetails.setText("");

    }
}