package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button user, doctor;
 FirebaseDBHelper firebaseDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        firebaseDBHelper = new FirebaseDBHelper();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String savedTc = sharedPreferences.getString("tc", "");
            String savedPassword = sharedPreferences.getString("password", "");

            if (!TextUtils.isEmpty(savedTc) && !TextUtils.isEmpty(savedPassword)) {
                firebaseDBHelper.readToDbUser(savedTc, savedPassword, new FirebaseDBHelper.OnReadCompleteListener() {
                    @Override
                    public void onSuccess(boolean result) {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(MainActivity.this, "Bir hata oluştu: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
            }

            return insets;
        });

        user = findViewById(R.id.GoUser_btn);
        doctor = findViewById(R.id.GoDoctor_btn);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goUser(v);
            }
        });
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDoctor(v);
            }
        });
    }

    public void goUser(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    public void goDoctor(View view) {
        Intent intent = new Intent(this, DoctorActivity.class);
        startActivity(intent);
    }
}
