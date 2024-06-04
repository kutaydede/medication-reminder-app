package com.example.myapplication.Alarm;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.DBHelper;
import com.example.myapplication.HomeActivity;
import com.example.myapplication.R;
import com.example.myapplication.Users.UserModel;
import com.example.myapplication.Users.UserSignUpActivity;
import com.example.myapplication.Users.UsersMedications;

import java.util.Calendar;
import java.util.List;

public class AlarmActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 123;
    private DBHelper dbHelper;
    private int ilacID;
    private TextView ilacAdiTextView;
    private ListView listView;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Button setAlarmButton = findViewById(R.id.setAlarmButton1);
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        listView = findViewById(R.id.listView);
        Intent intent = getIntent();
        if (intent != null) {
            ilacID = intent.getIntExtra("IlacID", -1);
        }
        UserModel user = UserModel.getInstance();
        dbHelper = new DBHelper(this);
        List<UsersMedications> ilaclar = dbHelper.getUsersProgram(user.getTCKimlikNo(), ilacID);

        for (UsersMedications usersMedications : ilaclar) {
            TableRow row = new TableRow(this);

            ilacAdiTextView = new TextView(this);
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

            tableLayout.addView(row);
        }
        setAlarmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                boolean alarmsSet = true;
                for (UsersMedications usersMedications : ilaclar) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        if (alarmManager != null && !alarmManager.canScheduleExactAlarms()) {
                            Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                            startActivity(intent);
                            alarmsSet = false;
                            break;
                        } else {
                            setAlarms(usersMedications);
                        }
                    } else {
                        setAlarms(usersMedications);
                    }
                }

                if (alarmsSet) {
                    Intent intent = new Intent(AlarmActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Log.d("AlarmActivity", "Did not set alarms, staying in current activity");
                }
            }

        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(AlarmActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AlarmActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE);
            }
        }
    }

    private void setAlarms(UsersMedications usersMedications) {
        if (!usersMedications.getSabahKullanimi().isEmpty()) {
            setAlarm(usersMedications.getSabahKullanimi(), usersMedications.getIlacAdi(), generateRequestCode());
        }
        if (!usersMedications.getOgleKullanimi().isEmpty()) {
            setAlarm(usersMedications.getOgleKullanimi(), usersMedications.getIlacAdi(), generateRequestCode());
        }
        if (!usersMedications.getAksamKullanimi().isEmpty()) {
            setAlarm(usersMedications.getAksamKullanimi(), usersMedications.getIlacAdi(), generateRequestCode());
        }
    }

    private int generateRequestCode() {
        return (int) System.currentTimeMillis(); // Her bir istek için benzersiz bir requestCode oluşturulur.
    }

    private void setAlarm(String time, String ilacAdi, int requestCode) {
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("ilacAdi", ilacAdi);
        intent.putExtra("time", time);
        intent.putExtra("notification_id", requestCode);
        intent.putExtra("ilacID", ilacID);

        int positiveRequestCode = Math.abs(requestCode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, positiveRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Toast.makeText(this, "Alarm kuruldu! " + time, Toast.LENGTH_SHORT).show();

            dbHelper.addAlarm(ilacAdi, time);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // İzin verildi
            } else {
                Toast.makeText(AlarmActivity.this, "İzin reddedildi.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void disableBatteryOptimizations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (pm != null && !pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        disableBatteryOptimizations();
    }

    public void Back_btn(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
