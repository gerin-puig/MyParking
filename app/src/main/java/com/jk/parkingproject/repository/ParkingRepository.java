package com.jk.parkingproject.repository;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jk.parkingproject.MainActivity;
import com.jk.parkingproject.ParkingListActivity;
import com.jk.parkingproject.ParkingSharedPrefs;
import com.jk.parkingproject.models.Parking;
import com.jk.parkingproject.models.ParkingUser;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class ParkingRepository {

    private final FirebaseFirestore db;
    private final FirebaseAuth myAuth;
    private String COLLECTION_ENTITY = "Parkings";
    private final String COLLECTION_NAME = "Users";
    private final String TAG = "TAG/RD";
    private final String TAG1 = "MyDebug";

    public MutableLiveData<Boolean> isAuthenticated = new MutableLiveData<Boolean>();

    public ParkingRepository(){
        db = FirebaseFirestore.getInstance();
        myAuth = FirebaseAuth.getInstance();

        isAuthenticated.postValue(false);
    }

    public void addParking(Parking parking){

        try{
            Map<String, Object> data = new HashMap<>();
            data.put("car", parking.getCarNumber());
            data.put("buildingCode", parking.getCarNumber());
            data.put("hostSuiteNumber", parking.getHostSuiteNumber());
            data.put("dateOfParking", parking.getDateOfParking());
            data.put("noOfHours", parking.getNoOfHours());

            db.collection(COLLECTION_ENTITY)
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "Parking added successfully with ID : "+documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure : Error while adding parking to Firebase : "+e.getLocalizedMessage());
                        }
                    });

        }
        catch (Exception e){
            Log.d(TAG, e.getLocalizedMessage());
        }

    }

    public void getAllParkings(){

    }

    public void deleteParking(){

    }

    public void signIn(String email, String password, Context context){
        myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: User Found");
                    //FirebaseUser user = fba.getCurrentUser();

                    isAuthenticated.postValue(true);
                }
                else{
                    Toast.makeText(context, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void isEmailValid(String email){

    }

    public void signOut(){
        myAuth.signOut();
    }

    private void signUpUser(String email, String password, Context context){
        myAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = myAuth.getCurrentUser();
                    Toast.makeText(context, "User Created.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Sign-up Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addUser(ParkingUser user, Context context){
        try{
            Map<String, Object> data = new HashMap<>();
            data.put("email", user.getEmail());
            data.put("password", user.getPassword());
            data.put("first_name", user.getFirst_name());
            data.put("last_name", user.getLast_name());
            data.put("phone_number", user.getPhone_number());
            data.put("plate_number", user.getPlate_number());

            signUpUser(user.getEmail(),user.getPassword(), context);

            db.collection(COLLECTION_NAME).add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG1, "onSuccess: New User Added " + documentReference.getId());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG1, "onFailure: Add new user has failed!" + e.getLocalizedMessage());
                }
            });

        }catch (Exception e){
            Log.d(TAG1, "addUser: " + e.getLocalizedMessage());
        }
    }



}


