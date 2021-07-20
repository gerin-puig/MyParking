package com.jk.parkingproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jk.parkingproject.databinding.Fragment2LayoutBinding;
import com.jk.parkingproject.models.ParkingUser;
import com.jk.parkingproject.shared.ParkingSharedPrefs;
import com.jk.parkingproject.view.EditProfileActivity;
import com.jk.parkingproject.view.MainActivity;
import com.jk.parkingproject.viewmodels.UserViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

/**
 * Gerin Puig - 101343659
 * Rajdeep Dodiya - 101320088
 */

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
                binding.txtUserEmail.setText("Email: "+user.getEmail());
                binding.txtUserPassword.setText("Password: "+user.getPassword());
                binding.txtUserFullname.setText("Name: "+user.getFirst_name() + " " + user.getLast_name());
                binding.txtUserPhoneNumber.setText("Phone Number: "+user.getPhone_number());
                binding.txtUserPlateNumber.setText("Plate Number: "+user.getPlate_number());
            }
        });

        binding.btnEditProfile.setOnClickListener(this);
        binding.btnLogout.setOnClickListener(this);
        binding.btnDeactivate.setOnClickListener(this);
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
                    logout();
                    break;
                case R.id.btnDeactivate:
                    checkUserSelection();
                    break;
            }
        }
    }

    private void checkUserSelection(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Set a title for alert dialog
        builder.setTitle("Deactivate Account?");

        // Ask the final question
        builder.setMessage("Are you sure?");

        // Set the alert dialog yes button click listener
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // Set the alert dialog no button click listener
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myUser.setActive(false);
                userViewModel.updateUser(myUser, getContext());
                logout();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void logout(){
        psp.userLogout();
        userViewModel.signOut();
        Intent i = new Intent(getContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        getActivity().finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        String currentUser = psp.getCurrentUser();
        userViewModel.getUser(currentUser);
        disableBackButton();
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
