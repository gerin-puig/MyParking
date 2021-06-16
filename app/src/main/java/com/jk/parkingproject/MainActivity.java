package com.jk.parkingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jk.parkingproject.databinding.ActivityMainBinding;
import com.jk.parkingproject.models.ParkingUser;
import com.jk.parkingproject.viewmodels.UserViewModel;

/**
 * Gerin Puig - 101343659
 * Rajdeep Dodiya - 101320088
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;

    ParkingSharedPrefs psp;
    private UserViewModel userViewModel;

    private String TAG = "MyDebug";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnLogin.setOnClickListener(this);
        binding.btnSignUp.setOnClickListener(this);
        binding.editEmail.setOnClickListener(this);
        binding.editPassword.setOnClickListener(this);

        psp = new ParkingSharedPrefs(this);
        userViewModel = UserViewModel.getInstance(getApplication());

        boolean isRemembered = psp.getIsRemembered();
        if(isRemembered){
            binding.editEmail.setText(psp.getInfo().first);
            binding.editPassword.setText(psp.getInfo().second);
            binding.switchRememberMe.setChecked(true);
            login();
        }
    }

    @Override
    public void onClick(View v) {
        if (v != null){
            switch (v.getId()){
                case R.id.btnLogin:
                    login();
                    break;
                case R.id.btnSignUp:
                    Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    private void login(){
        String email = binding.editEmail.getText().toString();
        String password = binding.editPassword.getText().toString();

        if (!checkUserInput(email,password)){
            return;
        }

        userViewModel.signIn(email, password, this);
        //checks if user sign in is successful
        userViewModel.isAuthenticated.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean == null)
                    return;

                boolean isRemembered = binding.switchRememberMe.isChecked();


                if (aBoolean) {
                    userViewModel.getUser(email);
                    userViewModel.myUser.observe(MainActivity.this, new Observer<ParkingUser>() {
                        @Override
                        public void onChanged(ParkingUser parkingUser) {
                            parkingUser.setActive(true);
                            userViewModel.updateUser(parkingUser, getApplicationContext());
                        }
                    });

                    psp.setCurrentUser(email);
                    psp.saveUserInfo(email,password,isRemembered);
                    Intent intent = new Intent(MainActivity.this, ParkingListActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean checkUserInput(String email, String password){
        Boolean isValid = true;
        if (email.isEmpty()){
            binding.editEmail.setError("Email Required.");
            isValid = false;
        }
        if(password.isEmpty()){
            binding.editPassword.setError("Password Required.");
            isValid = false;
        }
        return  isValid;
    }

}