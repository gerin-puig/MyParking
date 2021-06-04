package com.jk.parkingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD

=======
>>>>>>> 5c4312a35d91d5fae7f0ce44182132bc98cf980f
import android.content.Intent;
import android.os.Bundle;
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


//will move
    private FirebaseAuth fba = FirebaseAuth.getInstance();

    private String TAG = "MyDebug";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, ParkingListActivity.class);
        startActivity(intent);
=======

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnLogin.setOnClickListener(this);
        binding.editEmail.setOnClickListener(this);
        binding.editPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v != null){
            switch (v.getId()){
                case R.id.btnLogin:
                    login();
                    break;
            }
        }
    }

    private void login(){
        String email = binding.editEmail.getText().toString();
        String password = binding.editPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()){
            return;
        }

        fba.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: User Found");
                    //FirebaseUser user = fba.getCurrentUser();
                    Intent intent = new Intent(MainActivity.this, ParkingListActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
>>>>>>> 5c4312a35d91d5fae7f0ce44182132bc98cf980f
    }
}