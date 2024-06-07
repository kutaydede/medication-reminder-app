package com.example.myapplication.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.myapplication.R;

public class AlarmReceiver extends BroadcastReceiver {
    private static MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        String medicationName = intent.getStringExtra("ilacAdi");
        String time = intent.getStringExtra("time");
        int ilacID = intent.getIntExtra("ilacID", -1);
        int notificationId = intent.getIntExtra("notification_id", 0);

        NotificationHelper.createNotification(context, medicationName, time, notificationId, ilacID);
        playAlarmSound(context, notificationId);
    }

    private void playAlarmSound(Context context, int notificationId) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.alarm_sound);
            mediaPlayer.setOnCompletionListener(mp -> {
                mp.release();
                mediaPlayer = null;
                NotificationHelper.cancelNotification(context, notificationId);  // Bildirimi kapat
            });
        }
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        } else {
            Log.e("AlarmReceiver", "MediaPlayer is null or already playing");
        }
    }

    public static void stopAlarmSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
