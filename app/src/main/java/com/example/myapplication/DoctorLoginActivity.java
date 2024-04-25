package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DoctorLoginActivity extends AppCompatActivity {
    EditText ad, tc;
    Button login;
    FirebaseDBHelper firebaseDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ad = findViewById(R.id.DoctorNamelogin_input);
        tc = findViewById(R.id.DoctorTclogin_input);
        login = findViewById(R.id.Doctorlogin_btn);
        firebaseDBHelper = new FirebaseDBHelper();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ad.getText().toString();
                String tc = DoctorLoginActivity.this.tc.getText().toString();

                firebaseDBHelper.readToDbDoctor(tc, name, new FirebaseDBHelper.OnReadCompleteListener() {
                    @Override
                    public void onSuccess(boolean result) {
                        if (result) {
                            Toast.makeText(DoctorLoginActivity.this, "Giriş Başarılı !!", Toast.LENGTH_LONG).show();
                            go(v);
                        } else {
                            Toast.makeText(DoctorLoginActivity.this, "Giriş Başarısız !!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(DoctorLoginActivity.this, "Bir hata oluştu: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    public void go(View view) {
        Intent intent = new Intent(this, AddMedicationToUserActivity.class);
        startActivity(intent);

    }
}