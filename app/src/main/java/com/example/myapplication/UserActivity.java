package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserActivity extends AppCompatActivity {
    Button Login,SignUp;
    TextView mesaj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Login =findViewById(R.id.login_btn);
        SignUp =findViewById(R.id.SignUp_btn);
        mesaj =findViewById(R.id.textView);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin(v);
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignUp(v);
            }
        });
    }

    public void goLogin(View view){
        Intent intent = new Intent(this,UserLoginActivity.class);
        startActivity(intent);

    }
    public void goSignUp(View view){
        Intent intent = new Intent(this,UserSignUpActivity.class);
        startActivity(intent);

    }
}
