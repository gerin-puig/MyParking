package com.jk.parkingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.jk.parkingproject.databinding.ActivityEditProfileBinding;
import com.jk.parkingproject.models.ParkingUser;

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;

    ParkingUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        user = (ParkingUser) intent.getSerializableExtra("myuser");

        binding.edEmail.setText(user.getEmail());
    }
}