package com.example.scoorent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PayActivity extends AppCompatActivity {

    private RecyclerView mRvData;
    private RentAdapter rentAdapter;
    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private List<RentModels> rentModelsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        mRvData = findViewById(R.id.rentInfo);
        initView();
        Button btn_continue = (Button) findViewById(R.id.btnScooterContinue);
    }

    private void initView() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference().child("Rents");
        mRvData.setLayoutManager(new LinearLayoutManager(this));
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rentModelsList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    RentModels rentModels = dataSnapshot1.getValue(RentModels.class);
                    rentModelsList.add(rentModels);
                }
                rentAdapter = new RentAdapter(PayActivity.this, rentModelsList);
                mRvData.setAdapter(rentAdapter);
                rentAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}