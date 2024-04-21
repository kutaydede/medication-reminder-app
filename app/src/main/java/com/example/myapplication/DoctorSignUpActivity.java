package com.example.myapplication;

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
EditText Tc , Ad, Soyad ,Uzmanlik;
Button Kayit_btn;
DBHelper dbHelper;
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
        Tc=findViewById(R.id.Tc_input);
        Ad=findViewById(R.id.Name_input);
        Soyad=findViewById(R.id.LastName_input);
        Uzmanlik=findViewById(R.id.Profession_input);
        Kayit_btn=findViewById(R.id.DoctorSignUp1_btn);
        dbHelper=new DBHelper(this);

        Kayit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tc, ad, soyad ,uzmanlik;
                tc = Tc.getText().toString();
                ad = Ad.getText().toString();
                soyad = Soyad.getText().toString();
                uzmanlik=Uzmanlik.getText().toString();


                if (tc.equals("") || ad.equals("")  || soyad.equals("") || uzmanlik.equals("")){
                    Toast.makeText(DoctorSignUpActivity.this, "Lütfen Tüm Bilgileri Giriniz!!", Toast.LENGTH_LONG).show();
                } else {
                    DoctorModel doctor = new DoctorModel();
                    doctor.setTCKimlikNo(tc);
                    doctor.setAdi(ad);
                    doctor.setSoyadi(soyad);
                    doctor.setUzmanlikAlani(uzmanlik);


                    boolean Kayit = dbHelper.DoktorEkle(doctor);
                    if (Kayit){
                        Toast.makeText(DoctorSignUpActivity.this,"Kayıt Başarılı !!",Toast.LENGTH_LONG).show();
                    }else {
                        boolean Sorgula =dbHelper.DoktorTcSorgula(doctor.getTCKimlikNo());
                        if (Sorgula){
                            Toast.makeText(DoctorSignUpActivity.this,"Girdiğiniz Tc Kimlik Numarasına Kayıtlı Bir Kayıt Var!!",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(DoctorSignUpActivity.this,"Kayıt Başarısız !!",Toast.LENGTH_LONG).show();
                        } }
                }
            }

        });
    }
}