package com.example.myapplication.Users;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Alarm.AlarmActivity;
import com.example.myapplication.Database.DBHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.UserModel;
import com.example.myapplication.Model.UsersMedications;
import com.example.myapplication.R;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    TextView name;
    DBHelper dbHelper;
    UserModel user = UserModel.getInstance();
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String savedTc = sharedPreferences.getString("tc", "");

        dbHelper = new DBHelper(this);


        if (user.getTCKimlikNo() == null) {
            user.setTCKimlikNo(savedTc);
        }

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        List<UsersMedications> ilaclar = dbHelper.getUsersMedications(user.getTCKimlikNo());

        for (UsersMedications usersMedications : ilaclar) {
            TableRow row = new TableRow(this);

            TextView idTextView = new TextView(this);
            idTextView.setText(String.valueOf(usersMedications.getId()));
            idTextView.setPadding(5, 5, 5, 5);
            idTextView.setTextSize(13);
            idTextView.setGravity(Gravity.CENTER);
            idTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.Darkblue));
            row.addView(idTextView);

            TextView ilacAdiTextView = new TextView(this);
            ilacAdiTextView.setText(usersMedications.getIlacAdi());
            ilacAdiTextView.setPadding(5, 5, 5, 5);
            ilacAdiTextView.setTextSize(13);
            ilacAdiTextView.setGravity(Gravity.CENTER);
            ilacAdiTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
            row.addView(ilacAdiTextView);

            TextView sabahKullanimiTextView = new TextView(this);
            sabahKullanimiTextView.setText(usersMedications.getSabahKullanimi());
            sabahKullanimiTextView.setPadding(5, 5, 5, 5);
            sabahKullanimiTextView.setTextSize(13);
            sabahKullanimiTextView.setGravity(Gravity.CENTER);
            sabahKullanimiTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
            row.addView(sabahKullanimiTextView);

            TextView ogleKullanimiTextView = new TextView(this);
            ogleKullanimiTextView.setText(usersMedications.getOgleKullanimi());
            ogleKullanimiTextView.setPadding(5, 5, 5, 5);
            ogleKullanimiTextView.setTextSize(13);
            ogleKullanimiTextView.setGravity(Gravity.CENTER);
            ogleKullanimiTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
            row.addView(ogleKullanimiTextView);

            TextView aksamKullanimiTextView = new TextView(this);
            aksamKullanimiTextView.setText(usersMedications.getAksamKullanimi());
            aksamKullanimiTextView.setPadding(5, 5, 5, 5);
            aksamKullanimiTextView.setTextSize(13);
            aksamKullanimiTextView.setGravity(Gravity.CENTER);
            aksamKullanimiTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
            row.addView(aksamKullanimiTextView);

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(HomeActivity.this, "Tıklanan ilaç: " + usersMedications.getIlacAdi(), Toast.LENGTH_SHORT).show();
                    id = usersMedications.getId();
                    Intent intent = new Intent(HomeActivity.this, AlarmActivity.class);
                    intent.putExtra("IlacID", id);
                    startActivity(intent);
                }
            });

            tableLayout.addView(row);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = findViewById(R.id.textView3);

        if (dbHelper != null) {
            String[] kullaniciBilgileri = dbHelper.getKullaniciBilgileri(user.getTCKimlikNo());
            if (kullaniciBilgileri != null && kullaniciBilgileri.length > 1) {
                user.setAdi(kullaniciBilgileri[0]);
                user.setSoyadi(kullaniciBilgileri[1]);
                user.setSifre(kullaniciBilgileri[2]);
                user.setEmail(kullaniciBilgileri[3]);
                name.setText(String.format("%s %s", user.getAdi(), user.getSoyadi()));

            } else {
                Toast.makeText(this, "Kullanıcı bilgileri alınamadı.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Veritabanı yardımcısı başlatılamadı.", Toast.LENGTH_SHORT).show();
        }

    }

    public void exit_btn(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
