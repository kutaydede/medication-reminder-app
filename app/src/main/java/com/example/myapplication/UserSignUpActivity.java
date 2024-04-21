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

public class UserSignUpActivity extends AppCompatActivity {
    EditText TC,Name,LastName,Password,Email;
    Button SignUpBtn ;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        TC=findViewById(R.id.Tc_input);
        Name=findViewById(R.id.Name_input);
        LastName=findViewById(R.id.LastName_input);
        Password=findViewById(R.id.Password_input);
        Email=findViewById(R.id.Email_input);
        SignUpBtn=findViewById(R.id.SignUp_btn);
        dbHelper=new DBHelper(this);

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tc, name,lastName, password, email;
                tc = TC.getText().toString();
                name = Name.getText().toString();
                lastName=LastName.getText().toString();
                password = Password.getText().toString();
                email = Email.getText().toString();

                if (tc.equals("") || name.equals("")|| lastName.equals("") || password.equals("") || email.equals("")) {
                    Toast.makeText(UserSignUpActivity.this, "Lütfen Tüm Bilgileri Giriniz!!", Toast.LENGTH_LONG).show();
                } else {
                    UserModel user = UserModel.getInstance();
                    user.setTCKimlikNo(tc);
                    user.setAdi(name);
                    user.setSoyadi(lastName);
                    user.setSifre(password);
                    user.setEmail(email);

                    boolean Kayit = dbHelper.KullaniciEkle(user);
                    if (Kayit){
                        Toast.makeText(UserSignUpActivity.this,"Kayıt Başarılı !!",Toast.LENGTH_LONG).show();
                    }else {
                        boolean Sorgula =dbHelper.KullaniciTcSorgula(user.getTCKimlikNo());
                        if (Sorgula){
                            Toast.makeText(UserSignUpActivity.this,"Girdiğiniz Tc Kimlik Numarasına Kayıtlı Bir Kayıt Var!!",Toast.LENGTH_LONG).show();
                        }
                        else{
                        Toast.makeText(UserSignUpActivity.this,"Kayıt Başarısız !!",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

        });

    } public void goLogin(View view){
        Intent intent = new Intent(this,UserLoginActivity.class);
        startActivity(intent);

    }

}