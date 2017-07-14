package com.example.asus.todoclass2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

    public class Alarm extends BroadcastReceiver {
        static int i =0;
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO: This method is called when the BroadcastReceiver is receiving
            // an Intent broadcast.
//        Toast.makeText(context, "Alarm Recieved", Toast.LENGTH_SHORT).show();

            NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentTitle("My Notifications")
                    .setAutoCancel(true)
                    .setContentText("Alarm !!!");
            Intent resultIntent = new Intent (context,MainActivity.class);
            resultIntent.putExtra("id",i);

            PendingIntent resultPendingIntent = PendingIntent.getActivity(context,0,resultIntent,PendingIntent.FLAG_CANCEL_CURRENT);

            mbuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(i++,mbuilder.build());
        }
    }

