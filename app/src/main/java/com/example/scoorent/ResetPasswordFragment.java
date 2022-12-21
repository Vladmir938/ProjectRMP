package com.example.scoorent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordFragment extends AppCompatActivity {

    EditText textEmail;
    Button btn_Reset;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_reset_password);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Восстановление пароля");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResetPasswordFragment.this, LoginFragment.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        textEmail = (EditText) findViewById(R.id.textEmail);
        btn_Reset = (Button) findViewById(R.id.btn_resetPass);

        btn_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = textEmail.getText().toString();

                if (Email.equals("")){
                    Toast.makeText(ResetPasswordFragment.this, "Укажите Email",
                            Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPasswordFragment.this, "Инструкция отправлена на ваш Email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetPasswordFragment.this, LoginFragment.class));
                                finish();
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(ResetPasswordFragment.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}