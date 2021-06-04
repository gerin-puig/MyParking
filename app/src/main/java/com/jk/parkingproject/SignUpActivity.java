package com.jk.parkingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.jk.parkingproject.databinding.ActivitySignupBinding;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


    }

    private void SignUp(){
        String fname = binding.editFirstName.getText().toString();
        String lname = binding.editLastName.getText().toString();
        String email = binding.editEmail.getText().toString();
        String password = binding.editPassword.getText().toString();
        String pNum = binding.editPhoneNumber.getText().toString();
        String plateNum = binding.editPlateNumber.getText().toString();

        if(!checkUserInput(email,password,fname,lname,pNum,plateNum)){
            return;
        }

        
    }

    private boolean checkUserInput(String email, String password, String fname, String lname, String pNum, String plateNum){
        Boolean isValid = true;
        if (email.isEmpty()){
            binding.editEmail.setError("Email Required.");
            isValid = false;
        }
        if(password.isEmpty()){
            binding.editPassword.setError("Password Required.");
            isValid = false;
        }
        if(fname.isEmpty()){
            binding.editFirstName.setError("First Name Required.");
            isValid = false;
        }
        if(lname.isEmpty()){
            binding.editLastName.setError("Last Name Required.");
            isValid = false;
        }
        if(pNum.isEmpty()){
            binding.editPhoneNumber.setError("Phone Number Required.");
            isValid = false;
        }
        if(plateNum.isEmpty()){
            binding.editPlateNumber.setError("Plate Number Required.");
            isValid = false;
        }
        return  isValid;
    }
}