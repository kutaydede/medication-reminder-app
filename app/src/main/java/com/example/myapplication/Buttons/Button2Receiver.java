package com.example.myapplication.Buttons;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.example.myapplication.Alarm.AlarmReceiver;

public class Button2Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getIntExtra("notificationId", 0);

        Toast.makeText(context, "İlaç kullanılmadı!", Toast.LENGTH_SHORT).show();
        AlarmReceiver.stopAlarmSound();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancel(notificationId);
        }
    }
}
