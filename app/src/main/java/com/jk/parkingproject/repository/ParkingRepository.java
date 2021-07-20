package com.jk.parkingproject.repository;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jk.parkingproject.shared.ParkingSharedPrefs;
import com.jk.parkingproject.models.Parking;
import com.jk.parkingproject.models.ParkingUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
<<<<<<< HEAD
=======

/**
 * Gerin Puig - 101343659
 * Rajdeep Dodiya - 101320088
 */
>>>>>>> cc5d1b72f5ab636e1915d59f8cda5f54c5a6c69c

public class ParkingRepository {

    private final FirebaseFirestore db;
    private final FirebaseAuth myAuth;
    private String PARKING_COLLECTION_NAME = "Parkings";
    private final String COLLECTION_NAME = "Users";
    private final String TAG = "QWERTY";
    private final String TAG1 = "MyDebug";

    public MutableLiveData<Boolean> isAuthenticated = new MutableLiveData<Boolean>();
    public MutableLiveData<List<Parking>> parkingList = new MutableLiveData<List<Parking>>();
    public MutableLiveData<Parking> currentParking = new MutableLiveData<Parking>();
    public MutableLiveData<ParkingUser> thisUser = new MutableLiveData<ParkingUser>();

    private MutableLiveData<Boolean> isAuthSignUpSuccessful = new MutableLiveData<>();

    public MutableLiveData<String> currentUserCarPlateNumber = new MutableLiveData<>();

    public ParkingRepository(){
        db = FirebaseFirestore.getInstance();
        myAuth = FirebaseAuth.getInstance();

        isAuthSignUpSuccessful.postValue(false);
        isAuthenticated.postValue(false);
    }

    public void addParking(Parking parking){

        try{
            Map<String, Object> data = new HashMap<>();
            data.put("email", parking.getEmail());
            data.put("carPlateNumber", parking.getCarPlateNumber());
            data.put("buildingCode", parking.getBuildingCode());
            data.put("hostSuiteNumber", parking.getHostSuiteNumber());
            data.put("dateOfParking", parking.getDateOfParking());
            data.put("noOfHours", parking.getNoOfHours());
            data.put("latitude", parking.getLatitude());
            data.put("longitude", parking.getLongitude());

            db.collection(PARKING_COLLECTION_NAME)
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

    public void getCurrentParking(String parkingId){

        try{
            db.collection(PARKING_COLLECTION_NAME)
                    .document(parkingId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){

                            Parking p = (Parking) task.getResult().toObject(Parking.class);
                            p.setId(task.getResult().getId());
                            currentParking.postValue(p);
                            Log.e(TAG, "onComplete: getCurrentParking: pList :"+currentParking.toString());

                            }

                            else{
                                Log.e(TAG, "onComplete: getCurrentParking: Task Unsuccessful");
                            }

                        }
                    });

//                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                        @Override
//                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                            if(error != null){
//                                Log.d(TAG, "onEvent: Listening to collection failed  due to: "+error);
//                            }
//
//                            if(value.getData().isEmpty()){
//                                Log.d(TAG, "No change(s) in document of collection");
//                            }
//                            else{
//                                currentParking.setValue((Parking) value.getData());
//                                Log.d(TAG, "onEvent: Current data : " + value.getData());
//                            }
//                    }
//        });
        }

        catch(Exception e){
            Log.e(TAG, "getAllCars: Unable to get cars. Error : "+e.getLocalizedMessage());
        }
    }

    public void getCarByUsername(String currentUser){

        try{

            db.collection(COLLECTION_NAME).whereEqualTo("email", currentUser).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){

                                if (task.getResult().getDocuments().size() != 0){

                                    currentUserCarPlateNumber.postValue(task.getResult().getDocuments().get(0).getString("plate_number"));
                                    Log.e(TAG, "onComplete: "+currentUserCarPlateNumber);
                                }

                                else {
                                    Log.e(TAG, "onComplete: No such values exist");
                                }
                            }

                        }
                    });
        }

        catch(Exception e){
            Log.e(TAG, "getAllCars: Unable to get cars. Error : "+e.getLocalizedMessage());
        }

    }

    public void getAllParkings(String currentUser){

        try{
            db.collection(PARKING_COLLECTION_NAME).
                whereEqualTo("email", currentUser)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                            if(task.isSuccessful()){

                                if(task.getResult().getDocuments().size() >= 0){

                                    List<Parking> pList = new ArrayList<>();

                                    for(int i = 0; i < task.getResult().getDocuments().size(); i++){
                                        Parking currentParking = (Parking) task.getResult().getDocuments().get(i).toObject(Parking.class);
                                        currentParking.setId(task.getResult().getDocuments().get(i).getId());
                                        pList.add(currentParking);
                                        Log.e(TAG, "onComplete: getAllParkings: pList :"+pList.toString());
                                    }

                                    parkingList.postValue(pList);
                                    Log.e(TAG, "onComplete: getAllParkings: parkingList :"+parkingList.toString());

                                }
                                else{
                                    Log.e(TAG, "onComplete: getAllParkings: Empty results received");

                                }

                            }
                            else{
                                Log.e(TAG, "onComplete: getAllParkings: Task Unsuccessful");
                            }
                        }
                    });

        }

        catch(Exception e){
            Log.e(TAG, "getAllParkings: Error occured in getting all parkings "+e.getLocalizedMessage());
        }

    }

    public void updateParking(Parking updatedParking){
        try{
            Map<String, Object> updateInfo = new HashMap<>();
            updateInfo.put("email", updatedParking.getEmail());
            updateInfo.put("carPlateNumber", updatedParking.getCarPlateNumber());
            updateInfo.put("buildingCode", updatedParking.getBuildingCode());
            updateInfo.put("hostSuiteNumber", updatedParking.getHostSuiteNumber());
            updateInfo.put("dateOfParking", updatedParking.getDateOfParking());
            updateInfo.put("noOfHours", updatedParking.getNoOfHours());
            updateInfo.put("latitude", updatedParking.getLatitude());
            updateInfo.put("longitude", updatedParking.getLongitude());

            db.collection(PARKING_COLLECTION_NAME)
                    .document(updatedParking.getId())
                    .update(updateInfo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "onSuccess: Document updated successfully");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: Unable to update document");
                        }
                    });

            //TASK - COMPLETE THE REST OF THE FUNCTION CALLS AND OPERATIONS IN THE FLOW TO COMPLETTE THE UPDATE FUNCTIONALITY

        }catch (Exception ex){
            Log.e(TAG, "deleteFriend: Unable to update document " + ex.getLocalizedMessage() );
        }

    }

    public void deleteParking(String parkingId){
        try{
            db.collection(PARKING_COLLECTION_NAME)
                    .document(parkingId)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "onSuccess :  deleteParking():  Parking deleted successfuly ");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure : deleteParking() : Unable to delete parking :" + e.getLocalizedMessage() );
                        }
                    });
        }catch (Exception ex){
            Log.e(TAG, "deleteParking() : Unable to delete document " + ex.getLocalizedMessage() );
        }
    }


    public void signIn(String email, String password, Context context) {
        myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "onComplete: User Found");
                    //FirebaseUser user = fba.getCurrentUser();

                    isAuthenticated.postValue(true);
                } else {

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onComplete: "+e.getLocalizedMessage());
                    }
                }
            }
        });
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
                    isAuthSignUpSuccessful.postValue(true);
                }
                else {
                    //Toast.makeText(context, "Sign-up Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                isAuthSignUpSuccessful.postValue(false);
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
            data.put("isActive", true);

            signUpUser(user.getEmail(),user.getPassword(), context);

            isAuthSignUpSuccessful.observe((LifecycleOwner) context, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if(aBoolean){
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
                    }
                }
            });

        }catch (Exception e){
            Log.d(TAG1, "addUser: " + e.getLocalizedMessage());
        }
    }

    public void getUser(String email){
        try {
            db.collection(COLLECTION_NAME).whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        if (task.getResult().getDocuments().size() != 0){
                            ParkingUser parkingUser = (ParkingUser) task.getResult().getDocuments().get(0).toObject(ParkingUser.class);
                            parkingUser.setId(task.getResult().getDocuments().get(0).getId());

                            thisUser.postValue(parkingUser);
                            //Log.d(TAG, "onComplete: user found:" + parkingUser.toString());
                        }
                        else {
                            Log.e(TAG, "onComplete: No user with id found");
                        }
                    }
                }
            });
        }catch (Exception e){
            Log.e(TAG, "search for user: " + e.getLocalizedMessage() );
        }
    }

    public void updateUser(ParkingUser user, Context context){
        try{
            Map<String, Object> updateInfo = new HashMap<>();
            updateInfo.put("first_name", user.getFirst_name());
            updateInfo.put("last_name", user.getLast_name());
            updateInfo.put("email", user.getEmail());
            updateInfo.put("password", user.getPassword());
            updateInfo.put("phone_number", user.getPhone_number());
            updateInfo.put("plate_number", user.getPlate_number());
            updateInfo.put("isActive", user.isActive());

            changePassword(context, user.getPassword());

            db.collection(COLLECTION_NAME).document(user.getId()).update(updateInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG1, "onSuccess: document was updated!");
                    Toast.makeText(context, "User Updated.", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: document update has failed!");
                }
            });
        }catch (Exception e){
            Log.e(TAG, "updateFriend: unable to update doc " + e.getLocalizedMessage() );
        }
    }

    private void changePassword(Context context, String password){

        FirebaseUser user = myAuth.getCurrentUser();

        user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    ParkingSharedPrefs psp = new ParkingSharedPrefs(context.getApplicationContext());
                    psp.setPassword(password);
                    Log.d(TAG1, "onComplete: password updated");
                }
            }
        });

    }

}


