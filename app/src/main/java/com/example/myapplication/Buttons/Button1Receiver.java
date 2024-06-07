package com.example.myapplication.Buttons;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.myapplication.Alarm.AlarmReceiver;
import com.example.myapplication.Database.DBHelper;

public class Button1Receiver extends BroadcastReceiver {
    DBHelper dbHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getIntExtra("notificationId", 0);
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String savedTCKimlikNo = sharedPreferences.getString("tc", "");

        int ilacID = intent.getIntExtra("ilacID", -1);
        dbHelper = new DBHelper(context);
        dbHelper.ilacKullanimiEkle(ilacID, savedTCKimlikNo);

        Toast.makeText(context, "İlaç kullanıldı!", Toast.LENGTH_SHORT).show();
        AlarmReceiver.stopAlarmSound();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancel(notificationId);
        }

    }
}
