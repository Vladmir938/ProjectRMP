package com.example.scoorent;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RentAdapter extends RecyclerView.Adapter<RentAdapter.ViewHolder>{
    private List<RentModels> rentModelsList = new ArrayList<>();
    private Context mContext;
    public Dialog dialog;
    DatabaseReference reference;
    String id_rent;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item,parent,false);
        return new ViewHolder(view);
    }

    public RentAdapter(Context mContext, List<RentModels> rentModelsList) {
        this.mContext = mContext;
        this.rentModelsList = rentModelsList;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RentModels rentModels = rentModelsList.get(position);
        holder.rentName.setText(rentModels.getName());
        holder.rentTime.setText(rentModels.getTime() + "ч");
        holder.rentPrice.setText(rentModels.getPrice() + "₽");
        Glide.with(mContext).load(rentModels.getImgURL()).into(holder.rentImg);

        holder.rentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.pay_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView rentNAME = (TextView) dialog.findViewById(R.id.rentNAME);
                Button btn_pay = (Button) dialog.findViewById(R.id.btn_pay);

                rentNAME.setText("Тариф" + " " + rentModels.getName());

                btn_pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, ScooterInfoPay.class);
                        intent.putExtra("rent_name", rentModels.getName());
//                      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return rentModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rentPrice, rentTime, rentName;
        ImageView rentImg;
        CardView rentCard;
        public ViewHolder(View itemView) {
            super(itemView);
            rentTime = itemView.findViewById(R.id.rentTime);
            rentPrice = itemView.findViewById(R.id.rentPrice);
            rentName = itemView.findViewById(R.id.rentName);
            rentImg = itemView.findViewById(R.id.rentImg);
            rentCard = itemView.findViewById(R.id.rentCard);
        }
    }


}
