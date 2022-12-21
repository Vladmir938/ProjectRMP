package com.example.scoorent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RentFragment extends Fragment {

    FirebaseUser fuser;
    private RecyclerView mRvData;
    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private ScooterRentAdapter scooterRentAdapter;
    private List<ScooterPayRents> scooterPayRentsList = new ArrayList<>();
    private LinearLayout sad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rent, container, false);
        Button btn_Scan = (Button) view.findViewById(R.id.qrScan);
        mRvData = view.findViewById(R.id.activeRent);
        sad = view.findViewById(R.id.sad);
        initView();
        return view;
    }

    private void initView() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference().child("PayRents");
        mRvData.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                scooterPayRentsList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ScooterPayRents scooterPayRents = dataSnapshot1.getValue(ScooterPayRents.class);
                    fuser = FirebaseAuth.getInstance().getCurrentUser();
                    String userNow = fuser.getUid();

                    if (scooterPayRents.getUser().equals(userNow) & scooterPayRents.getNowIsRent().equals("true")) {
                        scooterPayRentsList.add(scooterPayRents);
                        if (scooterPayRentsList.size() != 0) {
                            mRvData.setVisibility(View.VISIBLE);
                            sad.setVisibility(View.GONE);
                        } else {
                            sad.setVisibility(View.VISIBLE);
                            mRvData.setVisibility(View.GONE);
                        }
                    }  else {
                        sad.setVisibility(View.VISIBLE);
                        mRvData.setVisibility(View.GONE);
                    }
                }
                scooterRentAdapter = new ScooterRentAdapter(getContext(), scooterPayRentsList);
                mRvData.setAdapter(scooterRentAdapter);
                scooterRentAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
