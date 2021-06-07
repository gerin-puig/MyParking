package com.jk.parkingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;

    ParkingSharedPrefs psp;
//will move
    private FirebaseAuth fba = FirebaseAuth.getInstance();

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

        fba.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: User Found");
                    //FirebaseUser user = fba.getCurrentUser();
                    psp.setCurrentUser(email);
                    Intent intent = new Intent(MainActivity.this, ParkingListActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
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