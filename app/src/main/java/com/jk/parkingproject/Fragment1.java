package com.jk.parkingproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

//        parkingViewModel.getAllParkings(sharedPrefs.getCurrentUser());

        parkingList = new ArrayList<>();
        loadParkingDataOnScreen();

        this.binding.lvParkingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), ParkingDetailsActivity.class);
                intent.putExtra("currentParking", parkingList.get(i));
                startActivity(intent);
            }
        });


    }

    @Override
    public void onResume() {

        parkingList.clear();
        loadParkingDataOnScreen();
        super.onResume();

    }

    private void loadParkingDataOnScreen() {

        parkingViewModel.getAllParkings(sharedPrefs.getCurrentUser());

        parkingViewModel.parkingList.observe(getActivity(), new Observer<List<Parking>>() {
            @Override
            public void onChanged(List<Parking> parkings) {

                parkingList.addAll(parkings);
                parkingListAdapter.notifyDataSetChanged();
                binding.tvParkingInfoMsg.setText(parkingList.size()+" Parking(s) available");
            }
        });

        parkingListAdapter = new ParkingListAdapter(getActivity().getApplicationContext(), parkingList);
        binding.lvParkingList.setAdapter(parkingListAdapter);


    }


}
