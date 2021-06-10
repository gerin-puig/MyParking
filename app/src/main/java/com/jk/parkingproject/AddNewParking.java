package com.jk.parkingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.jk.parkingproject.databinding.ActivityAddNewParkingBinding;
import com.jk.parkingproject.models.Parking;
import com.jk.parkingproject.viewmodels.ParkingViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddNewParking extends AppCompatActivity {

    ActivityAddNewParkingBinding binding;
    ParkingSharedPrefs sharedPreferences;
    List<String> carsList = new ArrayList<>();
    private String TAG = "QWERTY";

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

        sharedPreferences = new ParkingSharedPrefs(getApplicationContext());
        Log.d(TAG, "Current user : "+sharedPreferences.getCurrentUser());
        this.parkingViewModel = ParkingViewModel.getInstance(this.getApplication());

        this.parkingViewModel.getCarByUsername(sharedPreferences.getCurrentUser());

        newParking = new Parking();

        getCurrentUserCarPlateNumber();

        ArrayAdapter<String> noOfHoursAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, noOfHours);
        noOfHoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.binding.spinnerNoOfHours.setAdapter(noOfHoursAdapter);

        this.binding.btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewParking.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        AddNewParking.this.binding.btnSelectDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.date_time_selected)));
                        AddNewParking.this.binding.btnSelectDate.setTextColor(getResources().getColor(R.color.black));
                        AddNewParking.this.binding.btnSelectDate.setText(day+" - "+month+" - "+year);

                        newParking.setDateOfParking(c.getTime());
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        this.binding.btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddNewParking.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                        AddNewParking.this.binding.btnSelectTime.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.date_time_selected)));
                        AddNewParking.this.binding.btnSelectTime.setTextColor(getResources().getColor(R.color.black));

                        AddNewParking.this.binding.btnSelectTime.setText(hour+" : "+minute);
                        newParking.setTimeOfParking(formatTime(c.getTime()));
                    }
                }, mHour, mMinute, true);



                timePickerDialog.show();

            }
        });

        this.binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateData()){
                    // add parking to Firebase

                    saveDataToFirebase();
                    Toast.makeText(AddNewParking.this, "Parking added", Toast.LENGTH_SHORT).show();
                    finish();
                }



            }
        });

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

        else if(this.binding.btnSelectDate.getText().toString().equalsIgnoreCase("select date")){
            Snackbar.make(this, this.binding.getRoot(), "Please provide the date of parking", Snackbar.LENGTH_SHORT).show();
            isValid = false;
        }

        else if(this.binding.btnSelectTime.getText().toString().equalsIgnoreCase("select time")){
            Snackbar.make(this, this.binding.getRoot(), "Please provide the time of parking", Snackbar.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    private void saveDataToFirebase(){

        this.newParking.setEmail(this.sharedPreferences.getCurrentUser());
        this.newParking.setCarPlateNumber(this.binding.etCarPlateNumber.getText().toString());
        this.newParking.setBuildingCode(this.binding.etBuildingCode.getText().toString());
        this.newParking.setHostSuiteNumber(this.binding.etSuiteNumber.getText().toString());
        this.newParking.setNoOfHours(this.binding.spinnerNoOfHours.getSelectedItem().toString());

        this.parkingViewModel.addParking(newParking);

    }

    private Date formatDate(String date){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date d = null;
        try {
            d = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;

    }

    private String formatTime(Date date){

        return DateFormat.getTimeInstance().format(date);


    }

}