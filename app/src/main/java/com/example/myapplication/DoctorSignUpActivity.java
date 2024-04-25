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

public class DoctorSignUpActivity extends AppCompatActivity {
    EditText tc, ad, soyad, uzmanlik;
    Button kayit_btn;
    FirebaseDBHelper firebaseDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tc = findViewById(R.id.Tc_input);
        ad = findViewById(R.id.Name_input);
        soyad = findViewById(R.id.LastName_input);
        uzmanlik = findViewById(R.id.Profession_input);
        kayit_btn = findViewById(R.id.DoctorSignUp1_btn);
        firebaseDBHelper = new FirebaseDBHelper();

        kayit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tc, ad, soyad, uzmanlik;
                tc = DoctorSignUpActivity.this.tc.getText().toString();
                ad = DoctorSignUpActivity.this.ad.getText().toString();
                soyad = DoctorSignUpActivity.this.soyad.getText().toString();
                uzmanlik = DoctorSignUpActivity.this.uzmanlik.getText().toString();


                if (tc.equals("") || ad.equals("") || soyad.equals("") || uzmanlik.equals("")) {
                    Toast.makeText(DoctorSignUpActivity.this, "Lütfen Tüm Bilgileri Giriniz!!", Toast.LENGTH_LONG).show();
                } else {
                    DoctorModel doctor = new DoctorModel();
                    doctor.setTCKimlikNo(tc);
                    doctor.setAdi(ad);
                    doctor.setSoyadi(soyad);
                    doctor.setUzmanlikAlani(uzmanlik);


                    firebaseDBHelper.writeToDbDoctor(doctor, new FirebaseDBHelper.OnWriteCompleteListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(DoctorSignUpActivity.this, "Kayıt Başarılı !!", Toast.LENGTH_LONG).show();
                            goLogin(v);
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            Toast.makeText(DoctorSignUpActivity.this, "Kayıt Başarısız: " + errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

        });
    } public void goLogin(View view) {
        Intent intent = new Intent(this, DoctorLoginActivity.class);
        startActivity(intent);

    }

}