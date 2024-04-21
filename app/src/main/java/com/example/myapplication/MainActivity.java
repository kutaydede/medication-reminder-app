package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button User, Doctor;
    TextView mesaj;
    boolean loginSuccess=false;
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String savedTc = sharedPreferences.getString("tc", "");
            String savedPassword = sharedPreferences.getString("password", "");

            if (!TextUtils.isEmpty(savedTc) && !TextUtils.isEmpty(savedPassword)) {

                 loginSuccess = dbHelper.KullaniciGiris(savedTc,savedPassword);

                if (loginSuccess) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                }
            }

            return insets;
        });

        User = findViewById(R.id.GoUser_btn);
        Doctor = findViewById(R.id.GoDoctor_btn);
        mesaj = findViewById(R.id.textView);

        User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goUser(v);
            }
        });
        Doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDoctor(v);
            }
        });
    }

    public void goUser(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        dbHelper.close();
    }

    public void goDoctor(View view) {
        Intent intent = new Intent(this, DoctorActivity.class);
        startActivity(intent);
    }
}
