package com.jk.parkingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.jk.parkingproject.databinding.ActivityEditProfileBinding;
import com.jk.parkingproject.models.ParkingUser;
import com.jk.parkingproject.viewmodels.UserViewModel;

/**
 * Gerin Puig - 101343659
 * Rajdeep Dodiya - 101320088
 */

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    private UserViewModel userViewModel;
    ParkingUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userViewModel = UserViewModel.getInstance(getApplication());

        Intent intent = getIntent();
        user = (ParkingUser) intent.getSerializableExtra("myuser");

        binding.edEmail.setText(user.getEmail());
        binding.editPassword.setText(user.getPassword());
        binding.edFirstname.setText(user.getFirst_name());
        binding.edLastname.setText(user.getLast_name());
        binding.edPlateNumber.setText(user.getPlate_number());
        binding.edPhoneNumber.setText(user.getPhone_number());

        binding.btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUser();
            }
        });
    }

    void UpdateUser(){
        String fname = binding.edFirstname.getText().toString();
        String lname = binding.edLastname.getText().toString();
        String email = binding.edEmail.getText().toString();
        String password = binding.editPassword.getText().toString();
        String pNum = binding.edPhoneNumber.getText().toString();
        String plateNum = binding.edPlateNumber.getText().toString();

        if(!isInputValid(email,password,fname,lname, pNum, plateNum))
            return;

        user.setActive(true);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirst_name(fname);
        user.setLast_name(lname);
        user.setPhone_number(pNum);
        user.setPlate_number(plateNum);

        userViewModel.updateUser(user, this);

    }

    private boolean isInputValid(String email, String password, String fname, String lname, String pNum, String plateNum){
        Boolean isValid = true;
        if (email.isEmpty()){
            binding.edEmail.setError("Email Required.");
            isValid = false;
        }
        if(password.isEmpty()){
            binding.editPassword.setError("Password Required.");
            isValid = false;
        }
        else if(password.length() < 6){
            binding.editPassword.setError("Password length must be more than 6 characters.");
            isValid = false;
        }

        if (fname.isEmpty()){
            binding.edFirstname.setError("First name Required.");
            isValid = false;
        }
        if(lname.isEmpty()){
            binding.edLastname.setError("Last name Required.");
            isValid = false;
        }
        if (pNum.isEmpty()){
            binding.edPhoneNumber.setError("Phone number Required.");
            isValid = false;
        }
        if(plateNum.isEmpty()){
            binding.edPlateNumber.setError("Plate number Required.");
            isValid = false;
        }
        else if(plateNum.length() < 2){
            binding.edPlateNumber.setError("Plate number must be at least 2 characters long.");
            isValid = false;
        }

        return  isValid;
    }


}