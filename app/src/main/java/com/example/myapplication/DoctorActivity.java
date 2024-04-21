package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DoctorActivity extends AppCompatActivity {
    Button DLogin,SignUp;
    TextView mesaj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        DLogin =findViewById(R.id.DL_btn);
        SignUp =findViewById(R.id.DoctorSignUp_btn);
        mesaj =findViewById(R.id.DoctortextView);

        DLogin.setOnClickListener(new View.OnClickListener() {
            @Override
         public void onClick(View v) {goLogin(v);}
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignUp(v);
            }
        });

    }
    public void goLogin(View view){
        Intent intent = new Intent(this,DoctorLoginActivity.class);
        startActivity(intent);

    }
    public void goSignUp(View view){
        Intent intent = new Intent(this,DoctorSignUpActivity.class);
        startActivity(intent);

    }
}
