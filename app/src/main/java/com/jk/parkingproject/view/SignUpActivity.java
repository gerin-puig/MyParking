package com.jk.parkingproject.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jk.parkingproject.R;
import com.jk.parkingproject.databinding.ActivitySignupBinding;
import com.jk.parkingproject.models.ParkingUser;
import com.jk.parkingproject.viewmodels.UserViewModel;

/**
 * Gerin Puig - 101343659
 * Rajdeep Dodiya - 101320088
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySignupBinding binding;

    private UserViewModel userViewModel;

    private String TAG = "MyDebug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        this.binding.btnSignUp.setOnClickListener(this);

        userViewModel = UserViewModel.getInstance(getApplication());
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

        ParkingUser newUser = new ParkingUser(fname,lname,pNum,email,password,plateNum, true);

        userViewModel.addNewUser(newUser, this);

        Log.d(TAG, "SignUp: saved to DB: " + newUser.toString());
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
        else if(password.length() <= 5){
            binding.editPassword.setError("Password length must be 6 or more.");
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
        else if(plateNum.length() < 2){
            binding.editPlateNumber.setError("Plate needs at least 2 characters.");
            isValid = false;
        }

        return  isValid;
    }

    @Override
    public void onClick(View v) {
        if(v != null){
            switch (v.getId()){
                case R.id.btn_sign_up:
                    SignUp();
                    break;
            }
        }
    }
}