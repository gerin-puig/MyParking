package com.jk.parkingproject;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jk.parkingproject.R;
import com.jk.parkingproject.databinding.Fragment2LayoutBinding;
import com.jk.parkingproject.models.ParkingUser;
import com.jk.parkingproject.viewmodels.UserViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

public class Fragment2 extends Fragment implements View.OnClickListener {
    private Fragment2LayoutBinding binding;

    ParkingSharedPrefs psp;
    private UserViewModel userViewModel;
    ParkingUser myUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment2_layout, container, false);
        binding = Fragment2LayoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        psp = new ParkingSharedPrefs(getContext());
        String currentUser = psp.getCurrentUser();
        userViewModel = UserViewModel.getInstance(getActivity().getApplication());
        userViewModel.getUser(currentUser);
        userViewModel.myUser.observe(getActivity(), new Observer<ParkingUser>() {
            @Override
            public void onChanged(ParkingUser user) {
                if(user == null)
                    return;
                myUser = user;
                binding.txtUserEmail.setText("  "+user.getEmail());
                binding.txtUserPassword.setText("  "+user.getPassword());
                binding.txtUserFirstname.setText("  "+user.getFirst_name());
                binding.txtUserLastname.setText("  "+user.getLast_name());
                binding.txtUserPhoneNumber.setText("  "+user.getPhone_number());
                binding.txtUserPlateNumber.setText("  "+user.getPlate_number());
            }
        });

        binding.btnEditProfile.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    @Override
    public void onClick(View v) {
        if(v != null){
            switch (v.getId()){
                case R.id.btnEditProfile:
                    Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                    intent.putExtra("myuser", myUser);
                    startActivity(intent);
                    break;

                case R.id.btnLogout:

                    break;
            }
        }
    }

    /*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_items, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:

                break;
        }
        //return true;
        return super.onOptionsItemSelected(item);
    }

     */
}
