package com.example.myapplication.Doctors;

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

import com.example.myapplication.DBHelper;
import com.example.myapplication.R;

public class DoctorLoginActivity extends AppCompatActivity {
    EditText ad, tc;
    Button login, signUp;
    DBHelper dbHelper;

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
        signUp=findViewById(R.id.DoctorSign_btn);
        dbHelper = new DBHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ad.getText().toString();
                String tc = DoctorLoginActivity.this.tc.getText().toString();

                DoctorModel doctor = new DoctorModel();
                doctor.setAdi(name);
                doctor.setTCKimlikNo(tc);


                boolean Giris = dbHelper.doktorGiris(doctor);

                if (Giris) {
                    go(v);
                } else {
                    Toast.makeText(DoctorLoginActivity.this, "Giriş Başarısız !!", Toast.LENGTH_LONG).show();
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gosignUp(v);
            }
        });
    }

    public void go(View view) {
        Intent intent = new Intent(this, DoctorActivity.class);
        startActivity(intent);

    }public void gosignUp(View view) {
        Intent intent = new Intent(this, DoctorSignUpActivity.class);
        startActivity(intent);

    }
}