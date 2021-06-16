package com.jk.parkingproject.adapters;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jk.parkingproject.databinding.RowLayoutBinding;
import com.jk.parkingproject.models.Parking;
import com.jk.parkingproject.view.ParkingListViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ParkingListAdapter extends RecyclerView.Adapter<ParkingListViewHolder> {

    private List<Parking> parkings;
    private Application application;


    public ParkingListAdapter(List<Parking> pList, Application app){

        this.parkings = pList;
        this.application = app;

    }

    @Override
    public ParkingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RowLayoutBinding binding = RowLayoutBinding.inflate(LayoutInflater.from(this.application.getApplicationContext()), parent, false);
        return new ParkingListViewHolder(binding, application, parkings);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkingListViewHolder holder, int position) {

        holder.bind(position);
    }

    @Override
    public int getItemCount() {

        return this.parkings.size();
    }

}
