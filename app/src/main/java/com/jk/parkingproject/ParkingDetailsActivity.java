package com.jk.parkingproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jk.parkingproject.databinding.ActivityParkingDetailsBinding;
import com.jk.parkingproject.helpers.LocationHelper;
import com.jk.parkingproject.models.Parking;
import com.jk.parkingproject.viewmodels.ParkingViewModel;

public class ParkingDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    ActivityParkingDetailsBinding binding;
    Parking currentParking;
    ParkingViewModel parkingViewModel;
    LocationHelper locationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityParkingDetailsBinding.inflate(getLayoutInflater());
        View view = this.binding.getRoot();
        setContentView(view);

        getSupportActionBar().hide();

        currentParking = (Parking) getIntent().getSerializableExtra("currentParking");
        parkingViewModel = ParkingViewModel.getInstance(getApplication());
        locationHelper = LocationHelper.getInstance();

        loadParkingInfoDetails();

        this.binding.btnDeleteParkingParkingDetails.setOnClickListener(this);
        this.binding.btnEditParkingParkingDetails.setOnClickListener(this);
    }

    private void loadParkingInfoDetails() {

        this.binding.tvCarNumberParkingDetails.setText(currentParking.getCarPlateNumber());
        this.binding.tvBuildingCodeParkingDetails.setText(currentParking.getBuildingCode());
        this.binding.tvHouseSuiteNumberParkingDetails.setText(currentParking.getHostSuiteNumber());
        this.binding.tvNoOfHoursParkingDetails.setText(currentParking.getNoOfHours());
        this.binding.tvDateAndTimeOfParkingParkingDetails.setText(currentParking.getDateOfParking().toString());
        Location currentLocation = new Location("");
        currentLocation.setLatitude(currentParking.getLatitude());
        currentLocation.setLongitude(currentParking.getLongitude());
        this.binding.tvParkingLocationParkingDetails.setText(locationHelper.getAddress(this, currentLocation));

    }

    private void deleteParking() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Parking");
        builder.setMessage("Are you sure you want to delete this parking?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        parkingViewModel.deleteParking(currentParking.getId());
                        currentParking = null;
                        Toast.makeText(ParkingDetailsActivity.this, "Parking deleted successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });

        builder.show();

    }

    private void editParking() {

        Intent intent = new Intent(ParkingDetailsActivity.this, AddNewParking.class);
        intent.putExtra("currentParking", currentParking);
        startActivity(intent);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnEditParking_ParkingDetails:
                editParking();
                break;
            case R.id.btnDeleteParking_ParkingDetails:
                deleteParking();
                break;
        }
    }
}