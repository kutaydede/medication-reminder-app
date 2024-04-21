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

public class AddMedicationActivity extends AppCompatActivity {
    EditText ilacAdi;
    Button ekle;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_medication);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ilacAdi = findViewById(R.id.IlacAdi_input);
        ekle = findViewById(R.id.AddMedication_btn);
        dbHelper = new DBHelper(this);

        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ilacAdi;
                ilacAdi = AddMedicationActivity.this.ilacAdi.getText().toString();

                if (AddMedicationActivity.this.ilacAdi.equals("")) {
                    Toast.makeText(AddMedicationActivity.this, "Lütfen İlaç Adını Giriniz!!", Toast.LENGTH_LONG).show();
                } else {
                    boolean Kayit = dbHelper.ilacEkle(ilacAdi);
                    if (Kayit) {
                        Toast.makeText(AddMedicationActivity.this, "İlaç Kayıtı Başarılı !!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AddMedicationActivity.this, "İlaç Kayıtı Başarısız !!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}