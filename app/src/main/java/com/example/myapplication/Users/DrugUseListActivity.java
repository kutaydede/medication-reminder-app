package com.example.myapplication.Users;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Database.DBHelper;
import com.example.myapplication.Doctors.DoctorActivity;
import com.example.myapplication.Model.DrugUseListModel;
import com.example.myapplication.R;

import java.util.List;

public class DrugUseListActivity extends AppCompatActivity {
    DBHelper dbHelper;
    private int ilacId;
    private String tc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_drug_use_list);

        dbHelper = new DBHelper(this);
        Intent intent = getIntent();
        if (intent != null) {
            ilacId = intent.getIntExtra("ilacId", 0);
            tc = intent.getStringExtra("tc");
        }

        TableLayout tableLayout = findViewById(R.id.tableLayoutList);
        List<DrugUseListModel> drugUseListModels = dbHelper.getDrugUseList(tc, ilacId);

        for (DrugUseListModel drugUseListModel : drugUseListModels) {
            TableRow row = new TableRow(this);

            TextView programIdTextView = new TextView(this);
            programIdTextView.setText(String.valueOf(drugUseListModel.getProgramId()));
            programIdTextView.setPadding(5, 5, 5, 5);
            programIdTextView.setTextSize(13);
            programIdTextView.setGravity(Gravity.CENTER);
            programIdTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.Darkblue));
            row.addView(programIdTextView);

            TextView ilacIdTextView = new TextView(this);
            ilacIdTextView.setText(String.valueOf(drugUseListModel.getIlacId()));
            ilacIdTextView.setPadding(5, 5, 5, 5);
            ilacIdTextView.setTextSize(13);
            ilacIdTextView.setGravity(Gravity.CENTER);
            ilacIdTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
            row.addView(ilacIdTextView);

            TextView ilacAdiTextView = new TextView(this);
            ilacAdiTextView.setText(drugUseListModel.getIlacAdi());
            ilacAdiTextView.setPadding(5, 5, 5, 5);
            ilacAdiTextView.setTextSize(13);
            ilacAdiTextView.setGravity(Gravity.CENTER);
            ilacAdiTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
            row.addView(ilacAdiTextView);

            TextView kullanimZamaniTextView = new TextView(this);
            kullanimZamaniTextView.setText(drugUseListModel.getKullanimZamani());
            kullanimZamaniTextView.setPadding(5, 5, 5, 5);
            kullanimZamaniTextView.setTextSize(13);
            kullanimZamaniTextView.setGravity(Gravity.CENTER);
            kullanimZamaniTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
            row.addView(kullanimZamaniTextView);

            tableLayout.addView(row);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void exit_btn(View view) {
        Intent intent = new Intent(this, DoctorActivity.class);
        startActivity(intent);
    }
}
