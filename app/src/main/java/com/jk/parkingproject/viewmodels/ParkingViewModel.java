package com.jk.parkingproject.viewmodels;

import android.app.Application;

import com.jk.parkingproject.models.Parking;
import com.jk.parkingproject.repository.ParkingRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class ParkingViewModel extends AndroidViewModel {

    public static ParkingViewModel ourInstance;
    private  final ParkingRepository parkingRepository = new ParkingRepository();

    public static ParkingViewModel getInstance(Application application){

        if(ourInstance == null){
            ourInstance = new ParkingViewModel(application);
        }

        return  ourInstance;

    }

    //constructor matching super
    private ParkingViewModel(@NonNull Application application) {

        super(application);
    }

    public void addParking(Parking parking){
        this.parkingRepository.addParking(parking);

    }


}
