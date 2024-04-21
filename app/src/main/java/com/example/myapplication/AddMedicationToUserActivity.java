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
EditText Id,Tc,Sabah,Ogle,Aksam;
Button AddMedication;
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
        Id=findViewById(R.id.IlacId_input);
        Tc=findViewById(R.id.TcNo_input);
        Sabah=findViewById(R.id.SabaheditTextTime);
        Ogle=findViewById(R.id.OgleeditTextTime);
        Aksam=findViewById(R.id.AksameditTextTime);
        AddMedication=findViewById(R.id.AddMedicationToUser_btn);
        dbHelper =new DBHelper(this);

     AddMedication.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             int id;
             String idString, tc, sabah, ogle, aksam;
             idString = Id.getText().toString();
             id = Integer.parseInt(idString);
             tc = Tc.getText().toString();
             sabah = Sabah.getText().toString();
             ogle = Ogle.getText().toString();
             aksam = Aksam.getText().toString();
             if (idString.equals("") || tc.equals("") || sabah.equals("") || ogle.equals("") || aksam.equals("")) {
                 Toast.makeText(AddMedicationToUserActivity.this, "Lütfen Tüm Bilgileri Giriniz!!", Toast.LENGTH_LONG).show();
             } else {
                 DrugUseModel drugUseModel = new DrugUseModel();
                 drugUseModel.setId(id);
                 drugUseModel.setTc(tc);
                 drugUseModel.setSabahKullanimi(sabah);
                 drugUseModel.setOgleKullanimi(ogle);
                 drugUseModel.setAksamKullanimi(aksam);

                 boolean Kayit =dbHelper.insertIlacKullanimProgrami(drugUseModel);
                 if (Kayit){
                     Toast.makeText(AddMedicationToUserActivity.this,"Kayıt Başarılı !!",Toast.LENGTH_LONG).show();
                 } else{
                         Toast.makeText(AddMedicationToUserActivity.this,"Kayıt Başarısız !!",Toast.LENGTH_LONG).show();
                 }
             }
         }
     });
    }

}