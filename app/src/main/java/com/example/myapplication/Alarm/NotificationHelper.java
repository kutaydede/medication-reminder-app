package com.example.myapplication.Alarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.Buttons.Button1Receiver;
import com.example.myapplication.Buttons.Button2Receiver;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class NotificationHelper {

    private static final String CHANNEL_ID = "Medication_Channel";

    public static void createNotification(Context context, String medicationName, String time, int notificationId, int ilacID) {
        createNotificationChannel(context);
        Log.d("NotificationHelper", "Sending notification for medication: " + medicationName + ", time: " + time + ", notification id: " + notificationId);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Intent button1Intent = new Intent(context, Button1Receiver.class);
        button1Intent.putExtra("notificationId", notificationId);
        button1Intent.putExtra("ilacID", ilacID);
        PendingIntent button1PendingIntent = PendingIntent.getBroadcast(context, 0, button1Intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Intent button2Intent = new Intent(context, Button2Receiver.class);
        button2Intent.putExtra("notificationId", notificationId);
        button2Intent.putExtra("ilacID", ilacID);
        PendingIntent button2PendingIntent = PendingIntent.getBroadcast(context, 0, button2Intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("İlaç Hatırlatma")
                .setContentText(medicationName + " ilacını almayı unutmayın. (" + time + ")")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(false)  // Bildirimin otomatik olarak kapanmasını engelle
                .setOngoing(true)  // Bildirimi sürekli hale getir
                .addAction(R.drawable.baseline_arrow_upward_24, "Kullandım", button1PendingIntent)
                .addAction(R.drawable.baseline_arrow_downward_24, "Kullanmadım", button2PendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(notificationId, builder.build());
        }
    }

    private static void createNotificationChannel(Context context) {
        CharSequence name = "Medication Reminder Channel";
        String description = "İlaç hatırlatma bildirim kanalı";
        int importance = NotificationManager.IMPORTANCE_HIGH;  // Bildirim kanalının önemini yüksek ayarla
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void cancelNotification(Context context, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancel(notificationId);
        }
    }
}

