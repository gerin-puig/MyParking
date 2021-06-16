package com.jk.parkingproject.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.jk.parkingproject.databinding.ActivityAddNewParkingBinding;
import com.jk.parkingproject.databinding.SearchLocationBinding;
import com.jk.parkingproject.helpers.LocationHelper;
import com.jk.parkingproject.models.Parking;
import com.jk.parkingproject.shared.ParkingSharedPrefs;
import com.jk.parkingproject.viewmodels.ParkingViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class AddNewParking extends AppCompatActivity {

    ActivityAddNewParkingBinding binding;
    ParkingSharedPrefs sharedPreferences;
    private LocationHelper locationHelper;
    private String TAG = "QWERTY";
    private Location lastLocation;
    ArrayAdapter<String> noOfHoursAdapter;

//    String[] carsList =  {"Tesla - CD12 AQ8238", "Benz - RD007 BNZ143", "Hummer EV - RUDE BST"};
    String[] noOfHours = {"less than an hour", "less than 4 hours", "less than 12 hours", "24 hours"};

    private Parking newParking = new Parking();
    private ParkingViewModel parkingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide action bar
        getSupportActionBar().hide();

        this.binding = ActivityAddNewParkingBinding.inflate(getLayoutInflater());
        View view = this.binding.getRoot();
        setContentView(view);
        this.parkingViewModel = ParkingViewModel.getInstance(this.getApplication());
        this.locationHelper = LocationHelper.getInstance();

        noOfHoursAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, noOfHours);
        noOfHoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.binding.spinnerNoOfHours.setAdapter(noOfHoursAdapter);

        if(getIntent().getSerializableExtra("currentParking") != null){

            newParking = (Parking) getIntent().getSerializableExtra("currentParking");
            loadUpdateParkingDetailsOnScreen();

        }
        else{

//            loadAddParkingDetailsOnScreen();
            sharedPreferences = new ParkingSharedPrefs(getApplicationContext());
            this.parkingViewModel.getCarByUsername(sharedPreferences.getCurrentUser());
            getCurrentUserCarPlateNumber();
//            newParking = new Parking();
        }

        this.binding.imgbtnSearchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showInputDialog();

            }
        });

        this.binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateData()){
                    // validation check passed

                    if(newParking.getEmail() != null){
                        //
                        updateParkingInFirebase();
                        Toast.makeText(AddNewParking.this, "Parking updated", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        saveParkingToFirebase();
                        Toast.makeText(AddNewParking.this, "Parking added", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });

        this.binding.imgbtnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                locationHelper.checkPermissions(AddNewParking.this);

                if(locationHelper.isLocationPermissionsGranted){
                    // permission granted
//                    lastLocation = locationHelper.getLastLocation(AddNewParking.this);

                    locationHelper.getLastLocation(AddNewParking.this).observe(AddNewParking.this, new Observer<Location>() {
                        @Override
                        public void onChanged(Location location) {
                           if(location != null){
                                lastLocation = location;
                                binding.tvParkingLocation.setText(locationHelper.getAddress(AddNewParking.this, lastLocation));
                                Log.e(TAG, "onClick: SUCCESS!! Last location received");
                            }
                            else{
                                Log.e(TAG, "onClick: No last location received");
                            }

                        }
                    });
                }
            }
        });

    }



    private void loadUpdateParkingDetailsOnScreen() {

        this.binding.etCarPlateNumber.setText(newParking.getCarPlateNumber());
        this.binding.etBuildingCode.setText(newParking.getBuildingCode());
        this.binding.etSuiteNumber.setText(newParking.getHostSuiteNumber());
        int position = noOfHoursAdapter.getPosition(newParking.getNoOfHours());
        this.binding.spinnerNoOfHours.setSelection(position);
        this.lastLocation = new Location("");
        lastLocation.setLatitude(newParking.getLatitude());
        lastLocation.setLongitude(newParking.getLongitude());
        this.binding.tvParkingLocation.setText(locationHelper.getAddress(this, lastLocation));
        this.binding.btnSave.setText("Update");
    }

    private void getCurrentUserCarPlateNumber() {

        this.parkingViewModel.currentUserCarPlateNUmber.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                binding.etCarPlateNumber.setText(s);

            }
        });
    }

    private Boolean validateData(){

        Boolean isValid = true;

        if(this.binding.etCarPlateNumber.getText().toString().trim().length() < 2 || this.binding.etCarPlateNumber.getText().toString().trim().length() > 8){

            this.binding.etCarPlateNumber.setError("Car plate number should be 2 to 8 characters long");
            isValid = false;
        }

        else if(this.binding.etBuildingCode.getText().toString().trim().isEmpty()){

            this.binding.etBuildingCode.setError("Enter a building code");
            isValid = false;

        }

        else if(this.binding.etBuildingCode.getText().toString().trim().length() != 5){
            Snackbar.make(this, this.binding.getRoot(), "Building code should be exactly 5 characters", Snackbar.LENGTH_SHORT).show();
            isValid = false;
        }

        else if(this.binding.etSuiteNumber.getText().toString().trim().isEmpty()){
            this.binding.etSuiteNumber.setError("Enter a suite number");
            isValid = false;
        }

        else if(this.binding.etSuiteNumber.getText().toString().trim().length() < 2 || this.binding.etSuiteNumber.getText().toString().trim().length() > 5 ){
            Snackbar.make(this, this.binding.getRoot(), "Suit number code should be 2 to 5 characters long", Snackbar.LENGTH_SHORT).show();
            isValid = false;
        }

        else if(this.lastLocation == null){
            this.binding.tvParkingLocation.setError("");
            Snackbar.make(this, this.binding.getRoot(), "Please select the location of parking", Snackbar.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    private void updateParkingInFirebase(){

        this.newParking.setCarPlateNumber(this.binding.etCarPlateNumber.getText().toString());
        this.newParking.setBuildingCode(this.binding.etBuildingCode.getText().toString());
        this.newParking.setHostSuiteNumber(this.binding.etSuiteNumber.getText().toString());
        this.newParking.setNoOfHours(this.binding.spinnerNoOfHours.getSelectedItem().toString());
        this.newParking.setLatitude(this.lastLocation.getLatitude());
        this.newParking.setLongitude(this.lastLocation.getLongitude());
        this.parkingViewModel.updateParking(newParking);

    }

    private void saveParkingToFirebase(){

        this.newParking.setEmail(this.sharedPreferences.getCurrentUser());
        this.newParking.setCarPlateNumber(this.binding.etCarPlateNumber.getText().toString());
        this.newParking.setBuildingCode(this.binding.etBuildingCode.getText().toString());
        this.newParking.setHostSuiteNumber(this.binding.etSuiteNumber.getText().toString());
        this.newParking.setNoOfHours(this.binding.spinnerNoOfHours.getSelectedItem().toString());
        this.newParking.setLatitude(this.lastLocation.getLatitude());
        this.newParking.setLongitude(this.lastLocation.getLongitude());
        this.newParking.setDateOfParking(Calendar.getInstance().getTime());
        this.parkingViewModel.addParking(newParking);

    }

    protected void showInputDialog() {

        SearchLocationBinding searchLocationBinding = SearchLocationBinding.inflate(getLayoutInflater());
        View view = searchLocationBinding.getRoot();
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddNewParking.this);
        alertDialogBuilder.setView(view);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if(!searchLocationBinding.etSearchLocation.getText().toString().trim().isEmpty()){
                            lastLocation = locationHelper.getLocation(getApplicationContext(), searchLocationBinding.etSearchLocation.getText().toString());
                            binding.tvParkingLocation.setText(locationHelper.getAddress(getApplicationContext(), lastLocation));
                        }
                        else{
                            searchLocationBinding.etSearchLocation.setError("Please enter a location");
                        }

                    }

                });
        alertDialogBuilder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }

        });
        AlertDialog b = alertDialogBuilder.create();
        b.show();
        
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == locationHelper.LOCATION_REQUEST_CODE){
            this.locationHelper.isLocationPermissionsGranted = (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);
        }

        if(this.locationHelper.isLocationPermissionsGranted){
            Log.e(TAG, "onRequestPermissionsResult: PERMISSION IS GRANTED " );
        }
    }
}