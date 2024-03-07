package com.example.vehicleapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class CarAdmin extends Fragment {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    List<CarData> datalist;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    //  Button btn;
    SearchView searchView;
    CarAdapterAdmin adepter;

    public CarAdmin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_car_admin, container, false);

        fab = view.findViewById(R.id.car_fab);
        recyclerView=view.findViewById(R.id.car_RecycleView);
        // btn=findViewById(R.id.home);
        searchView=view.findViewById(R.id.car_search);
        searchView.clearFocus();

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress);
        AlertDialog dialog=builder.create();
        dialog.show();

        datalist=new ArrayList<>();

        adepter=new CarAdapterAdmin(getContext(),datalist);
        recyclerView.setAdapter(adepter);

        databaseReference = FirebaseDatabase.getInstance().getReference("CAR");
        dialog.show();

        eventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for(DataSnapshot itemSnapshot:snapshot.getChildren())
                {
                    CarData dataclass=itemSnapshot.getValue(CarData.class);
                    dataclass.setCarKey(itemSnapshot.getKey());
                    datalist.add(dataclass);
                }
                adepter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(),CarDetail.class);
                startActivity(intent);
            }
        });

        return view;
    }
    public void searchList(String text)
    {
        ArrayList<CarData> searchList=new ArrayList<>();
        for(CarData dataclass: datalist)
        {
            if(dataclass.getCarName().toLowerCase().contains(text.toLowerCase()))
            {
                searchList.add(dataclass);
            }
        }
        adepter.searchDataList(searchList);
        // adepter.notifyDataSetChanged();
        // recyclerView.setAdapter(adepter);
    }
}