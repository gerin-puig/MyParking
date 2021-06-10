package com.jk.parkingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jk.parkingproject.databinding.ActivityEditProfileBinding;
import com.jk.parkingproject.models.ParkingUser;
import com.jk.parkingproject.viewmodels.UserViewModel;

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

        user.setActive(true);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirst_name(fname);
        user.setLast_name(lname);
        user.setPhone_number(pNum);
        user.setPlate_number(plateNum);

        userViewModel.updateUser(user, this);

    }


}