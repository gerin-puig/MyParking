package com.jk.parkingproject.viewmodels;

import android.app.Application;

import com.jk.parkingproject.models.Parking;
import com.jk.parkingproject.models.ParkingUser;
import com.jk.parkingproject.repository.ParkingRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class ParkingViewModel extends AndroidViewModel {

    public static ParkingViewModel ourInstance;
    private  final ParkingRepository parkingRepository = new ParkingRepository();
    public MutableLiveData<List<Parking>> parkingList;
    public MutableLiveData<String> currentUserCarPlateNUmber;

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

    public void getAllParkings(String currentUser){
        this.parkingRepository.getAllParkings(currentUser);
        this.parkingList = parkingRepository.parkingList;
    }

    public void getCarByUsername(String currentUser){

        this.parkingRepository.getCarByUsername(currentUser);
        this.currentUserCarPlateNUmber = this.parkingRepository.currentUserCarPlateNumber;
    }

}
