package com.example.myapplication.Doctors;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class DoctorActivity extends AppCompatActivity {
    Button ilacKayit, ilacKullanim, AddMTU;

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
        AddMTU = findViewById(R.id.AddMTU_btn);
        ilacKayit = findViewById(R.id.IlacKayit_btn);
        ilacKullanim = findViewById(R.id.IlacKullanim_btn);
        AddMTU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAddMTU(v);
            }
        });
        ilacKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goInsertMedication(v);
            }
        });
        ilacKullanim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDrugUseList(v);
            }
        });

    }

    public void goAddMTU(View view) {
        Intent intent = new Intent(this, AddMedicationToUserActivity.class);
        startActivity(intent);

    }

    public void goInsertMedication(View view) {
        Intent intent = new Intent(this, AddMedicationActivity.class);
        startActivity(intent);

    }

    public void goDrugUseList(View view) {
        Intent intent = new Intent(this, DrugUseListInquiryActivity.class);
        startActivity(intent);

    }

    public void exitHome_btn(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
