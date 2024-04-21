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
EditText Ad , Tc;
Button Login;
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
       Ad=findViewById(R.id.DoctorNamelogin_input);
       Tc=findViewById(R.id.DoctorTclogin_input);
       Login=findViewById(R.id.Doctorlogin_btn);
        dbHelper =new DBHelper(this);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Ad.getText().toString();
                String tc = Tc.getText().toString();

                DoctorModel doctor = new DoctorModel();
                doctor.setAdi(name);
                doctor.setTCKimlikNo(tc);


                boolean Giris = dbHelper.DoktorGiris(doctor);

                if (Giris) {
                    go(v);
                } else {
                    Toast.makeText(DoctorLoginActivity.this, "Giriş Başarısız !!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void go(View view){
        Intent intent = new Intent(this,AddMedicationActivity.class);
        startActivity(intent);

    }
}