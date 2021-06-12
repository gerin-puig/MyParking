package com.jk.parkingproject;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.jk.parkingproject.databinding.RowLayoutBinding;
import com.jk.parkingproject.models.Parking;
import com.jk.parkingproject.viewmodels.ParkingViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ParkingListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    RowLayoutBinding binding;
    Application application;
    List<Parking> parkingList;
    private String TAG = "MY_DEBUG_TAG";
    ParkingViewModel parkingViewModel;


    public ParkingListViewHolder(RowLayoutBinding binding, Application app, List<Parking> parkings) {
        super(binding.getRoot());
        this.binding = binding;
        this.application = app;
        this.parkingList = parkings;
        this.binding.getRoot().setOnClickListener(this);

    }

    public void bind(int position) {
        // to associate the UI with your data

        this.binding.tvCarPlateNumber.setText(parkingList.get(position).getCarPlateNumber());
        this.binding.tvDateOfParking.setText(parkingList.get(position).getDateOfParking().toString());
        this.binding.tvParkingLocation.setText("Parking Location");
        this.binding.tvParkingHours.setText(parkingList.get(position).getNoOfHours());
    }


    @Override
    public void onClick(View view) {

        int position = getAdapterPosition();
        Intent intent = new Intent(application.getApplicationContext(), ParkingDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("currentParking", parkingList.get(position));
        application.startActivity(intent);
    }
}
