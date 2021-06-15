package com.jk.parkingproject;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.jk.parkingproject.databinding.Fragment1LayoutBinding;
import com.jk.parkingproject.models.Parking;
import com.jk.parkingproject.viewmodels.ParkingViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

public class Fragment1 extends Fragment {

    private Fragment1LayoutBinding binding;
    private List<Parking> parkingList;
    private ParkingViewModel parkingViewModel;
    private ParkingSharedPrefs sharedPrefs;
    private ParkingListAdapter parkingListAdapter;
    private String TAG = "QWERTY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.binding = Fragment1LayoutBinding.inflate(getLayoutInflater());
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPrefs = new ParkingSharedPrefs(getActivity().getApplicationContext());
        parkingViewModel = ParkingViewModel.getInstance(getActivity().getApplication());

        parkingList = new ArrayList<>();

//        parkingListAdapter = new ParkingListAdapter(parkingList, getActivity().getApplication());
//        binding.rcViewParkingList.setAdapter(parkingListAdapter);

        loadParkingDataOnScreen();
//        updateParkingInfoLabel();

    }

    @Override
    public void onResume() {

        super.onResume();
        binding.rcViewParkingList.invalidate();

        parkingList.clear();
        binding.tvParkingInfoMsg.setText("Fetching data ...");
        loadParkingDataOnScreen();
        super.onResume();

        //disable the back button so user cant return to login w/o logout button
        disableBackButton();

    }

    private void loadParkingDataOnScreen() {

        parkingViewModel.getAllParkings(sharedPrefs.getCurrentUser());

        parkingViewModel.parkingList.observe(getActivity(), new Observer<List<Parking>>() {
            @Override
            public void onChanged(List<Parking> parkings) {

                parkingList.clear();
                parkingList.addAll(parkings);
                parkingListAdapter.notifyDataSetChanged();
//                binding.tvParkingInfoMsg.setText(parkingList.size()+" Parking(s) available");
                updateParkingInfoLabel();
            }
        });

//        binding.tvParkingInfoMsg.setText(parkingList.size()+" Parking(s) available");

//        binding.rcViewParkingList.setAdapter(parkingListAdapter);
//        binding.rcViewParkingList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

    }

    void updateParkingInfoLabel(){

        if(this.parkingList.size() == 0){
            binding.tvParkingInfoMsg.setText("You do not have any parkings");
        }
        else {
            binding.tvParkingInfoMsg.setText(parkingList.size()+" Parking(s) available");
        }
    }

    private void disableBackButton(){
        if(getView() == null){
            return;
        }
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    // handle back button's click listener
                    return true;
                }
                return false;
            }
        });
    }


}
