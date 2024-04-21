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

public class AddMedicationToUserActivity extends AppCompatActivity {
    EditText id, tcNo, sabah, ogle, aksam;
    Button addMedication;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_medication_to_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        id = findViewById(R.id.IlacId_input);
        tcNo = findViewById(R.id.TcNo_input);
        sabah = findViewById(R.id.SabaheditTextTime);
        ogle = findViewById(R.id.OgleeditTextTime);
        aksam = findViewById(R.id.AksameditTextTime);
        addMedication = findViewById(R.id.AddMedicationToUser_btn);
        dbHelper = new DBHelper(this);

        addMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id;
                String idString, tc, sabah, ogle, aksam;
                idString = AddMedicationToUserActivity.this.id.getText().toString();
                id = Integer.parseInt(idString);
                tc = tcNo.getText().toString();
                sabah = AddMedicationToUserActivity.this.sabah.getText().toString();
                ogle = AddMedicationToUserActivity.this.ogle.getText().toString();
                aksam = AddMedicationToUserActivity.this.aksam.getText().toString();
                if (idString.isEmpty() || tc.isEmpty() || sabah.isEmpty() || ogle.isEmpty() || aksam.isEmpty()) {
                    Toast.makeText(AddMedicationToUserActivity.this, "Lütfen Tüm Bilgileri Giriniz!!", Toast.LENGTH_LONG).show();
                } else {
                    DrugUseModel drugUseModel = new DrugUseModel();
                    drugUseModel.setId(id);
                    drugUseModel.setTc(tc);
                    drugUseModel.setSabahKullanimi(sabah);
                    drugUseModel.setOgleKullanimi(ogle);
                    drugUseModel.setAksamKullanimi(aksam);

                    boolean Kayit = dbHelper.insertIlacKullanimProgrami(drugUseModel);
                    if (Kayit) {
                        Toast.makeText(AddMedicationToUserActivity.this, "Kayıt Başarılı !!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AddMedicationToUserActivity.this, "Kayıt Başarısız !!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

}