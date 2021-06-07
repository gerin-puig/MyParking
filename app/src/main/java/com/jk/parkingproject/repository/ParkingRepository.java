package com.jk.parkingproject.repository;

import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jk.parkingproject.models.Parking;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class ParkingRepository {

    private final FirebaseFirestore db;
    private String COLLECTION_ENTITY = "Parkings";
    private final String TAG = "TAG/RD";

    public ParkingRepository(){

        db = FirebaseFirestore.getInstance();
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


}


