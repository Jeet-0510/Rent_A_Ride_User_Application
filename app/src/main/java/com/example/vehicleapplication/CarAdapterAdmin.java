package com.example.vehicleapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CarAdapterAdmin extends RecyclerView.Adapter<MyCarViewHolder> {

    private Context context;
    private List<CarData> datalist;

    public CarAdapterAdmin(Context context, List<CarData> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public MyCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.car_recycle,parent,false);

        return new MyCarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCarViewHolder holder, int position) {
        Glide.with(context).load(datalist.get(position).getCarImage()).into(holder.carImage);
        holder.carName.setText(datalist.get(position).getCarName());
        holder.carLocation.setText(datalist.get(position).getCarLocation());
        holder.carPrice.setText(datalist.get(position).getCarRupees());
        holder.carDesc.setText(datalist.get(position).getCarDesc());

        holder.carCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,CarAdminView.class);
                intent.putExtra("Car_Image",datalist.get(holder.getAdapterPosition()).getCarImage());
                intent.putExtra("Car_Name",datalist.get(holder.getAdapterPosition()).getCarName());
                intent.putExtra("Car_Location",datalist.get(holder.getAdapterPosition()).getCarLocation());
                intent.putExtra("Car_Price",datalist.get(holder.getAdapterPosition()).getCarRupees());
                intent.putExtra("Car_Desc",datalist.get(holder.getAdapterPosition()).getCarDesc());
                intent.putExtra("Car_Key",datalist.get(holder.getAdapterPosition()).getCarKey());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
    public void searchDataList(ArrayList<CarData> searchList)
    {
        datalist=searchList;
        notifyDataSetChanged();
    }
}
class MyCarViewHolder extends RecyclerView.ViewHolder
{
    ImageView carImage;
    TextView carName,carLocation,carPrice,carDesc;
    CardView carCard;

    public MyCarViewHolder(@NonNull View itemView) {
        super(itemView);
        carImage=itemView.findViewById(R.id.reccar_Image);
        carName=itemView.findViewById(R.id.reccar_name);
        carLocation=itemView.findViewById(R.id.reccar_location);
        carPrice=itemView.findViewById(R.id.reccar_rupees);
        carCard=itemView.findViewById(R.id.recCar_card);
        carDesc=itemView.findViewById(R.id.reccar_desc);
    }
}

