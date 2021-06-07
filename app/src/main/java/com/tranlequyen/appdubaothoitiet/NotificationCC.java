package com.tranlequyen.appdubaothoitiet;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationCC extends Application {
    private static final String CHANNEL_1_ID = "Chanel 1";
    Context context;
    Notification notification;
    private PendingIntent contentIntent;

    public  NotificationCC (Context c) {
        context = c;
    }
    public void sendOnChannel1(View view, String message, String bigText, String summary){

        String title = "Thời tiết hiện tại nè!";
         message = "Message";
         bigText = "Big text";
         summary = "ABC XYZ";
        String bigTitle = "Hình nè";
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(view.getContext());

        Bitmap largeIcon = BitmapFactory.decodeResource(view.getResources(), R.drawable.icon_clear);


        Notification notification = new NotificationCompat.Builder(view.getContext(), CHANNEL_1_ID)
                .setSmallIcon(R.drawable.humidity_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(largeIcon)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(bigText)
                        .setBigContentTitle(bigTitle)
                        .setSummaryText(summary))
                .setPriority( NotificationCompat.PRIORITY_HIGH)
                .setCategory( NotificationCompat.CATEGORY_MESSAGE)
                .setColor( Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();
        NotificationManager notificationManager1 = (NotificationManager) getSystemService ( Context.NOTIFICATION_SERVICE );
        notificationManager.notify(1, notification);
    }
}
