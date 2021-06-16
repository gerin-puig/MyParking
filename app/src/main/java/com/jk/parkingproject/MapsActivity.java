package com.jk.parkingproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jk.parkingproject.databinding.ActivityMapsBinding;
import com.jk.parkingproject.helpers.LocationHelper;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LatLng parkingLocation;
    LocationCallback locationCallback;
    LocationHelper locationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        parkingLocation = new LatLng(getIntent().getDoubleExtra("parking_lat", 0.0),
                getIntent().getDoubleExtra("parking_long", 0.0));

        locationHelper = LocationHelper.getInstance();
        locationHelper.checkPermissions(this);
        if(locationHelper.isLocationPermissionsGranted) {
            initiateLocationListener();
        }

    }

    private void initiateLocationListener(){
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if(locationResult == null){
                    return;
                }

                for(Location location: locationResult.getLocations()){
                    //parkingLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    //locationHelper.getAddress(getApplicationContext(), location);
                    //Log.d(TAG, "onLocationResult: update location " + location.toString());

                    mMap.addMarker(new MarkerOptions().position(parkingLocation).title("Parking Spot"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(parkingLocation, 18.0f));
                }
            }
        };

        locationHelper.requestLocationUpdates(this, locationCallback);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng loc = new LatLng(parkingLocation.latitude, parkingLocation.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
    }
}