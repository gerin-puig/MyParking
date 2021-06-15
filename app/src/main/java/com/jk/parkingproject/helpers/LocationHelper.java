package com.jk.parkingproject.helpers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.jk.parkingproject.AddNewParking;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

public class LocationHelper {

    private final String TAG = "QWERTY";
    public boolean isLocationPermissionsGranted = false;
    private LocationRequest locationRequest;
    public int LOCATION_REQUEST_CODE = 101;
    private FusedLocationProviderClient fusedLocationProviderClient = null;
    MutableLiveData<Location> mLocation = new MutableLiveData<>();

    private static final LocationHelper ourInstance = new LocationHelper();

    public static LocationHelper getInstance(){
        return ourInstance;
    }

    private LocationHelper(){
        // create location request
        this.locationRequest = new LocationRequest();
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        this.locationRequest.setInterval(30000); // 30 seconds

    }

    public void checkPermissions(Context context){

        this.isLocationPermissionsGranted = (ContextCompat.checkSelfPermission(context.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);

        if(!this.isLocationPermissionsGranted){
            //permission not granted, request the permissions
            requestLocationPermissions(context);
        }
    }

    void requestLocationPermissions(Context context){

        String[] permissionsList = {Manifest.permission.ACCESS_FINE_LOCATION};
        ActivityCompat.requestPermissions((Activity) context, permissionsList, this
        .LOCATION_REQUEST_CODE);

    }

    public FusedLocationProviderClient getFusedLocationProviderClient(Context context){

        if(this.fusedLocationProviderClient == null){

            this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        }
        return this.fusedLocationProviderClient;
    }

    @SuppressLint("MissingPermission")
    public MutableLiveData<Location> getLastLocation(Context context){

        if(this.isLocationPermissionsGranted){
            try{
                this.getFusedLocationProviderClient(context)
                        .getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if(location != null){

                                    mLocation.setValue(location);
                                    Log.d(TAG, "onSuccess: Location obtained : LATITUDE : "+mLocation.getValue().getLatitude()
                                            +"LONGITUDE : "+mLocation.getValue().getLongitude());
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.e(TAG, "onFailure: Couldn't get location. Error : "+e.getLocalizedMessage());
                            }
                        });


            }
            catch (Exception e){
                Log.e(TAG, "getLastLocation: Exception occured "+e.getLocalizedMessage());
                return  null;
            }
            return this.mLocation;
        }

        else{
            Log.e(TAG, "getLastLocation: Permission not granted. Certain features might not work" );
            return  null;
        }

    }

    public String getAddress(Context context, Location location){

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addressList;

        try{
            addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 2);

            String address = addressList.get(0).getAddressLine(0);
            return address;

        }
        catch (Exception e){
            Log.e(TAG, "getAddress: Exception:"+e.getLocalizedMessage());
        }

        return null;

    }

    public List<Address> getLocation(Context context, String locationName){

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addressList;

        try{
            addressList = geocoder.getFromLocationName(locationName, 3);

            return addressList;

        }
        catch (Exception e){
            Log.e(TAG, "getAddress: Exception:"+e.getLocalizedMessage());
        }

        return null;

    }
}
