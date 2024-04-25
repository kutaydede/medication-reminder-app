package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserLoginActivity extends AppCompatActivity {
    EditText tc, sifre, passwordInput;
    Button login;
    CheckBox hatirla;

    DBHelper dbHelper;
    FirebaseDBHelper firebaseDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHelper = new DBHelper(this);
        firebaseDBHelper = new FirebaseDBHelper();
        tc = findViewById(R.id.Tclogin_input);
        sifre = findViewById(R.id.Passwordlogin_input);
        login = findViewById(R.id.login_btn);
        passwordInput = findViewById(R.id.Passwordlogin_input);
        hatirla = findViewById(R.id.checkBox);
        ImageView visibilityToggle = findViewById(R.id.visibilityToggle); // Göz simgesi olarak tanımlanan ImageView

        visibilityToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordInput.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    // Şifre görünürse, şifre gizlenecek
                    passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    visibilityToggle.setImageResource(R.drawable.icons8_visibility_24); // Göz simgesini değiştirme
                } else {
                    // Şifre gizliyse, şifre görünür hale gelecek
                    passwordInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    visibilityToggle.setImageResource(R.drawable.icons8_visibility_24); // Göz simgesini değiştirme
                }

                // Yeni metni dikkate almak için cursor'u güncelle
                int position = passwordInput.length();
                passwordInput.setSelection(position);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tc = UserLoginActivity.this.tc.getText().toString();
                String sifre = UserLoginActivity.this.sifre.getText().toString();
                UserModel user = UserModel.getInstance();

                firebaseDBHelper.readToDbUser(tc, sifre, new FirebaseDBHelper.OnReadCompleteListener() {
                    @Override
                    public void onSuccess(boolean result) {
                        if (result) {
                            user.setTCKimlikNo(tc);
                            user.setSifre(sifre);
                            goHome(v);
                            if (hatirla.isChecked()) {
                                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("tc", tc);
                                editor.putString("password", sifre);
                                editor.apply();
                            }
                        } else {
                            Toast.makeText(UserLoginActivity.this, "Giriş Başarısız !!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(UserLoginActivity.this, "Bir hata oluştu: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public void goHome(View view) {

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);


    }
}