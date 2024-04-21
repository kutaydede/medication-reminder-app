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
EditText IlacAdi ;
Button Ekle;
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
        IlacAdi=findViewById(R.id.IlacAdi_input);
        Ekle=findViewById(R.id.AddMedication_btn);
        dbHelper=new DBHelper(this);

        Ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  ilacAdi;
                ilacAdi = IlacAdi.getText().toString();

                if (IlacAdi.equals("")) {
                    Toast.makeText(AddMedicationActivity.this, "Lütfen İlaç Adını Giriniz!!", Toast.LENGTH_LONG).show();
                } else {
                    boolean Kayit = dbHelper.IlacEkle(ilacAdi);
                    if (Kayit){
                            Toast.makeText(AddMedicationActivity.this,"İlaç Kayıtı Başarılı !!",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(AddMedicationActivity.this,"İlaç Kayıtı Başarısız !!",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}