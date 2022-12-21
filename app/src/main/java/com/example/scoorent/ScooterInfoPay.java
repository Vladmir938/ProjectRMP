package com.example.scoorent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ScooterInfoPay extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebaseInstance;
    public String rent, scooter, user, rentPrice;

    DatabaseReference reference, referenceMoney, referenceRent;
    TextInputEditText scooterModel, scooterSpeed, scooterBattery;
    ImageView scooterIMG;
    ProgressBar loadBar;
    Button btn_continue;

    FirebaseUser fuser;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scooter_info_pay);
        Bundle arguments = getIntent().getExtras();
        String rent_name = arguments.get("rent_name").toString();

        find_id_rent(rent_name);
        find_id_user();
        scanCode();

        scooterModel = (TextInputEditText) findViewById(R.id.scooterModel);
        scooterSpeed = (TextInputEditText) findViewById(R.id.scooterSpeed);
        scooterBattery = (TextInputEditText) findViewById(R.id.scooterBattery);
        scooterIMG = (ImageView) findViewById(R.id.scooterImg);

        loadBar = (ProgressBar) findViewById(R.id.ScooterLoad);

        btn_continue = (Button) findViewById(R.id.btnScooterContinue);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(ScooterInfoPay.this);
                dialog.setContentView(R.layout.pay_succesfull);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                LottieAnimationView lottie = (LottieAnimationView) dialog.findViewById(R.id.lottie);
                lottie.playAnimation();
                createRent();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(ScooterInfoPay.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }, 2500);

                dialog.show();
            }
        });

    }

    public void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() != null) {
                    mFirebaseInstance = FirebaseDatabase.getInstance();
                    mDatabase = mFirebaseInstance.getReference().child("Scooters");
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        String scooter_id;
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //ПОИСК ID ЭЛЕКТРОСАМОКАТА
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                if ((dataSnapshot1.getKey()).toString().equals((result.getContents()).toString())) {
                                    scooter_id  = result.getContents();
                                    enableScooter(scooter_id);
                                    scooter = scooter_id;
                                }
                            }

                            if (scooter_id == null) {
                                Toast.makeText(ScooterInfoPay.this, "Не удаётся идентифицировать электросамокат",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ScooterInfoPay.this, PayActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });

    private void enableScooter(String scooter_id) {
        reference = FirebaseDatabase.getInstance().getReference("Scooters").child(scooter_id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ScooterModel scooter = dataSnapshot.getValue(ScooterModel.class);
                scooterModel.setText(scooter.getModel());
                scooterBattery.setText(scooter.getBattery() + "%");
                scooterSpeed.setText(scooter.getSpeed() + " км/ч");
                Glide.with(ScooterInfoPay.this).load(scooter.getScooterIMG()).into(scooterIMG);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadBar.setVisibility(View.GONE);
                        btn_continue.setVisibility(View.VISIBLE);

                    }
                }, 2500);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void find_id_rent(String namerent) {
        reference = FirebaseDatabase.getInstance().getReference("Rents");
        reference.addValueEventListener(new ValueEventListener() {
            String id_rent;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    RentModels rentModels = dataSnapshot1.getValue(RentModels.class);
                    String check = rentModels.getName();
                    if (namerent.equals(check)) {
                        rent = dataSnapshot1.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void find_id_user() {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        user = fuser.getUid();
    }

    private void createRent() {
        Long date = System.currentTimeMillis();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd", Locale.getDefault());
        String time = timeFormat.format(date);
        String datedate = dateFormat.format(date);

        SimpleDateFormat timeHour = new SimpleDateFormat("HH", Locale.getDefault());
        String hour = timeHour.format(date);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Scooter", scooter);
        hashMap.put("Rent", rent);
        hashMap.put("User", user);
        hashMap.put("NowIsRent", "true");
        hashMap.put("Time", time);
        hashMap.put("Date", datedate);
        hashMap.put("Hour", hour);

        reference.child("PayRents").push().setValue(hashMap);

    }

}