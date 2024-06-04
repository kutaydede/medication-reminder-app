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

import com.example.myapplication.Doctors.DoctorActivity;
import com.example.myapplication.Users.UserSignUpActivity;

public class DrugUseListInquiryActivity extends AppCompatActivity {
    EditText edittextTc, edittextId;
    Button sorgula;
    DBHelper dbHelper;
    int id;
    String tc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_drug_use_list_inquiry);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edittextTc = findViewById(R.id.Tc_input);
        edittextId = findViewById(R.id.ilacId_input);
        sorgula = findViewById(R.id.sorgula_btn);
        dbHelper = new DBHelper(this);


        sorgula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tc = edittextTc.getText().toString();
                id = Integer.parseInt(edittextId.getText().toString());
                try {
                    boolean sorgu = dbHelper.drugList(tc, id);

                    if (sorgu) {
                        Intent intent = new Intent(DrugUseListInquiryActivity.this, DrugUseListActivity.class);
                        intent.putExtra("ilacId", id);
                        intent.putExtra("tc", tc);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DrugUseListInquiryActivity.this, "Bu kullanıcıya ait ilaç veya ilaç kullanım programı bulunamadı.", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(DrugUseListInquiryActivity.this, "Lütfen geçerli bir ID giriniz.", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    public void Back_btn(View view) {
        Intent intent = new Intent(this, DoctorActivity.class);
        startActivity(intent);
    }
}