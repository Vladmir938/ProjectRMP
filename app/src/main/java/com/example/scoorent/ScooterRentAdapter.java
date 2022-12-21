package com.example.scoorent;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScooterRentAdapter extends RecyclerView.Adapter<ScooterRentAdapter.ViewHolder> {

    private List<ScooterPayRents> scooterRentAdapterList = new ArrayList<>();
    private Context mContext;
    DatabaseReference referenceScooter, referenceRent;
    FirebaseUser fuser;
    String timeRent;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scooter_rent_item, parent, false);
        return new ViewHolder(view);
    }

    public ScooterRentAdapter(Context mContext, List<ScooterPayRents> scooterRentAdapterList) {
        this.mContext = mContext;
        this.scooterRentAdapterList = scooterRentAdapterList;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ScooterPayRents scooterPayRents = scooterRentAdapterList.get(position);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        String userNow = fuser.getUid();
        if (scooterPayRents.getUser().equals(userNow) & scooterPayRents.getNowIsRent().equals("true")) {
            referenceScooter = FirebaseDatabase.getInstance().getReference("Scooters").child(scooterPayRents.getScooter());
            referenceScooter.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ScooterModel scooterModel = snapshot.getValue(ScooterModel.class);
                    holder.scooterBattery.setText(scooterModel.getBattery() + "%");
                    holder.scooterModel.setText(scooterModel.getModel());
                    Glide.with(mContext).load(scooterModel.getScooterIMG()).into(holder.imgScooter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            referenceRent = FirebaseDatabase.getInstance().getReference("Rents").child(scooterPayRents.getRent());
            referenceRent.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    RentModels rentModels = snapshot.getValue(RentModels.class);
                    timeRent = rentModels.getTime();

                    holder.scooterTimeRent.setText(timeRent);
                    holder.scooterTimeWhen.setText("В прокате: " + scooterPayRents.getTime());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return scooterRentAdapterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView scooterModel, scooterBattery, scooterTimeRent, scooterTimeWhen;
        ImageView imgScooter;
        CardView rentCardScooter;

        public ViewHolder(View itemView) {
            super(itemView);
            scooterModel = itemView.findViewById(R.id.rentScooterModel);
            scooterBattery = itemView.findViewById(R.id.rentScooterBattery);
            rentCardScooter = itemView.findViewById(R.id.rentCardScooter);
            imgScooter = itemView.findViewById(R.id.imgScooter);
            scooterTimeRent = itemView.findViewById(R.id.rentScooterTimeRent);
            scooterTimeWhen = itemView.findViewById(R.id.rentScooterTimeWhen);
        }
    }



}
