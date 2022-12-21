package com.example.scoorent;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class RegistrationFragment extends AppCompatActivity {

    Button btn_register;
    EditText textSurname, textName, textPatronymic, textEmail, textPassword, textPassword_2;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Регистрация");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationFragment.this, AuthActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });

        btn_register = (Button) findViewById(R.id.btn_Register);
        textSurname = (EditText) findViewById(R.id.textSurname);
        textName = (EditText) findViewById(R.id.textName);
        textPatronymic = (EditText) findViewById(R.id.textPatronymic);
        textEmail = (EditText) findViewById(R.id.textEmail);
        textPassword = (EditText) findViewById(R.id.textPassword);
        textPassword_2 = (EditText) findViewById(R.id.textPassword_2);

        auth = FirebaseAuth.getInstance();

        TextInputEditText phone = (TextInputEditText) findViewById(R.id.edit_text_numphone);
        phone.addTextChangedListener(new TextWatcher() {
            int length_before = 0;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                length_before = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (length_before < s.length()) {
                    if (s.length() == 1)
                        s.append("(");
                    if (s.length() == 5)
                        s.append(")");
                    if (s.length() == 9)
                        s.append("-");
                    if (s.length() == 12)
                        s.append("-");
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name, Surname, Patronymic, Email, Phone, Password, Password_2;
                Name = textName.getText().toString();
                Surname = textSurname.getText().toString();
                Patronymic = textPatronymic.getText().toString();
                Email = textEmail.getText().toString();
                Password = textPassword.getText().toString();
                Password_2 = textPassword_2.getText().toString();
                Phone = phone.getText().toString();

                if (TextUtils.isEmpty(Name) || TextUtils.isEmpty(Surname) || TextUtils.isEmpty(Patronymic) ||
                        TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password) || TextUtils.isEmpty(Phone)) {
                    Toast.makeText(RegistrationFragment.this, "Заполните все поля",
                            Toast.LENGTH_SHORT).show();
                } else if (!Password.equals(Password_2)) {
                    Toast.makeText(RegistrationFragment.this, "Пароли не совпадают",
                            Toast.LENGTH_SHORT).show();
                }
                else if (phone.length() < 15) {
                    Toast.makeText(RegistrationFragment.this, "Некорректный номер телефона",
                            Toast.LENGTH_SHORT).show();
                } else {
                    register(Name, Surname, Patronymic, Email, Phone, Password);
                }
            }
        });

    }

    private void register(String Name, String Surname, String Patronymic, String Email, String Phone, String Password) {
        auth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("Name", Name);
                            hashMap.put("Surname", Surname);
                            hashMap.put("Patronymic", Patronymic);
                            hashMap.put("Email", Email);
                            hashMap.put("Password", Password);
                            hashMap.put("Phone", Phone);

                            hashMap.put("ProfileIMG", "default");
                            hashMap.put("Balance", "1000");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(RegistrationFragment.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegistrationFragment.this, "Регистрация не удалась",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}