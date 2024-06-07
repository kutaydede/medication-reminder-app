package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Database.DBHelper;
import com.example.myapplication.Doctors.DoctorLoginActivity;
import com.example.myapplication.Users.HomeActivity;
import com.example.myapplication.Users.UserLoginActivity;

public class MainActivity extends AppCompatActivity {

    Button user, doctor;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // DBHelper nesnesini onCreate() metodunda değil, onResume() metodunda oluşturun
        dbHelper = new DBHelper(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String savedTc = sharedPreferences.getString("tc", "");
            String savedPassword = sharedPreferences.getString("password", "");

            if (!TextUtils.isEmpty(savedTc) && !TextUtils.isEmpty(savedPassword)) {
                // DBHelper nesnesini kullanıp kapatmadan önce onResume() metodunda oluşturuldu
                boolean loginSuccess = dbHelper.kullaniciGiris(savedTc, savedPassword);

                if (loginSuccess) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                }
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

    @Override
    protected void onResume() {
        super.onResume();
        dbHelper = new DBHelper(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // DBHelper nesnesini onResume() metodunda oluşturulduğu gibi kapatın
        dbHelper.close();
    }

    public void goUser(View view) {
        Intent intent = new Intent(this, UserLoginActivity.class);
        startActivity(intent);
    }

    public void goDoctor(View view) {
        Intent intent = new Intent(this, DoctorLoginActivity.class);
        startActivity(intent);
    }
}
