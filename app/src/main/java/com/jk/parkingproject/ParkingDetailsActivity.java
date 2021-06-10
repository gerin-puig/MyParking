package com.jk.parkingproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jk.parkingproject.databinding.ActivityParkingDetailsBinding;
import com.jk.parkingproject.models.Parking;

public class ParkingDetailsActivity extends AppCompatActivity {

    ActivityParkingDetailsBinding binding;
    Parking currentParking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityParkingDetailsBinding.inflate(getLayoutInflater());
        View view = this.binding.getRoot();
        setContentView(view);

        getSupportActionBar().hide();

        currentParking = (Parking) getIntent().getSerializableExtra("currentParking");

        loadParkingInfoDetails();
    }

    private void loadParkingInfoDetails() {

        this.binding.tvCarNumberParkingDetails.setText(currentParking.getCarPlateNumber());
        this.binding.tvBuildingCodeParkingDetails.setText(currentParking.getBuildingCode());
        this.binding.tvHouseSuiteNumberParkingDetails.setText(currentParking.getHostSuiteNumber());
        this.binding.tvNoOfHoursParkingDetails.setText(currentParking.getNoOfHours());
        this.binding.tvDateAndTimeOfParkingParkingDetails.setText(currentParking.getDateOfParking()+""+currentParking.getTimeOfParking());
        this.binding.tvParkingLocationParkingDetails.setText("");

    }
}