package com.babylon.alex.academicaldiary;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.babylon.alex.academicaldiary.MainActivity;
import com.babylon.alex.academicaldiary.R;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // класс для вызова напоминания

        Intent notificationIntent = new Intent(context, MainActivity.class);
        String text = intent.getExtras().getString("Text");
        String title = intent.getExtras().getString("Title");
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder.setContentTitle(context.getString(R.string.app_name))
                .setContentText(title + " " +text)
                .setTicker(title)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setSmallIcon(R.drawable.logo)
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);
    }
}
