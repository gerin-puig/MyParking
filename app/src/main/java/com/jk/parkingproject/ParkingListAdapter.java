package com.jk.parkingproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.jk.parkingproject.databinding.RowLayoutBinding;
import com.jk.parkingproject.models.Parking;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ParkingListAdapter extends ArrayAdapter<Parking> {


    public ParkingListAdapter(@NonNull Context context, List<Parking> parkingList) {
        super(context, 0, parkingList);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        RowLayoutBinding rowLayoutBinding;

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent,false);
        }

        Parking p = getItem(position);

        rowLayoutBinding = RowLayoutBinding.bind(convertView);
        rowLayoutBinding.tvCarPlateNumber.setText(p.getCarPlateNumber());
        rowLayoutBinding.tvDateOfParking.setText(p.getDateOfParking().toString());
        rowLayoutBinding.tvParkingHours.setText(p.getNoOfHours());

        return convertView;
    }

    private String formatDate(Date date){

        return DateFormat.getDateInstance().format(date);

    }
}
