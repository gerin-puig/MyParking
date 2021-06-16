package com.jk.parkingproject.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.jk.parkingproject.models.ParkingUser;
import com.jk.parkingproject.repository.ParkingRepository;

/**
 * Gerin Puig - 101343659
 * Rajdeep Dodiya - 101320088
 */

public class UserViewModel extends AndroidViewModel {
    private static UserViewModel myInstance;
    private final ParkingRepository parkingRepository = new ParkingRepository();

    public MutableLiveData<Boolean> isAuthenticated;
    public MutableLiveData<ParkingUser> myUser;

    public static UserViewModel getInstance(Application app){
        if(myInstance == null){
            myInstance = new UserViewModel(app);
        }

        return myInstance;
    }

    private UserViewModel(Application app){
        super(app);

    }

    public void addNewUser(ParkingUser user, Context context){
        this.parkingRepository.addUser(user, context);
    }

    public void signIn(String email, String password, Context context){
        parkingRepository.signIn(email,password,context);
        isAuthenticated = parkingRepository.isAuthenticated;
    }

    public void signOut(){
        parkingRepository.signOut();
    }


    public void getUser(String id){
        parkingRepository.getUser(id);
        myUser = parkingRepository.thisUser;
    }

    public void updateUser(ParkingUser user, Context context){
        parkingRepository.updateUser(user, context);
    }
}
