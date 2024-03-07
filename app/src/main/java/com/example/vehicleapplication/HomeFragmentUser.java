package com.example.vehicleapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragmentUser extends Fragment {

    CardView bicycle,scooter,bike,car;
  public HomeFragmentUser() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_user, container, false);

        bicycle = view.findViewById(R.id.cvBicycle);
        scooter = view.findViewById(R.id.cvScooter);
        bike = view.findViewById(R.id.cvBike);
        car =  view.findViewById(R.id.cvCar);

        bicycle.setOnClickListener(v-> {
            Intent intent = new Intent(getContext(), VehicleSearch.class);
            intent.putExtra("Vehicle","Bicycle");
            startActivity(intent);
        });

        scooter.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), VehicleSearch.class);
            intent.putExtra("Vehicle","Scooter");
            startActivity(intent);
        });

        bike.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), VehicleSearch.class);
            intent.putExtra("Vehicle","Bike");
            startActivity(intent);
        });

        car.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), VehicleSearch.class);
            intent.putExtra("Vehicle","Car");
            startActivity(intent);
        });

        return view;
    }
}