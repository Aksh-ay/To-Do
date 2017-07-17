package com.example.asus.todoclass2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

public class Alarm extends BroadcastReceiver {
        static int i =0;
        @Override
        public void onReceive(Context context, Intent intent) {
            String Time="null";
            String title =intent.getStringExtra("Task");
            Long time = intent.getLongExtra("epoch",-1);
            int Id = intent.getIntExtra("ID",-1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);
            final int day = calendar.get(Calendar.DATE);
            final int hourofday = calendar.get(Calendar.HOUR_OF_DAY);
            final int minute = calendar.get(Calendar.MINUTE);

            // TODO: This method is called when the BroadcastReceiver is receiving
            // an Intent broadcast.
//        Toast.makeText(context, "Alarm Recieved", Toast.LENGTH_SHORT).show();

//            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//            Ringtone r = RingtoneManager.getRingtone(context, notification);
//            r.play();

            // Get instance of Vibrator from current Context
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 600 milliseconds
            v.vibrate(600);

            if (hourofday>12){
                if (minute<10)
                    Time = (hourofday-12) + ":" + "0"+ minute + " PM";
                else
                    Time = (hourofday-12) + ":" + minute+ " PM";
            }
            else if(hourofday<12 && hourofday>0) {
                if (minute<10)
                    Time = hourofday + ":" + "0"+ minute + " AM";
                else
                    Time = hourofday + ":" + minute+ " AM";
            }
            else if (hourofday==12) {
                if (minute < 10)
                    Time =hourofday + ":" + "0" + minute + " PM";
                else
                    Time = hourofday + ":" + minute + " PM";
            }
            else if (hourofday==0) {
                if (minute < 10)
                    Time = (hourofday+12) + ":" + "0" + minute + " AM";
                else
                    Time = (hourofday+12) + ":" + minute + " AM";
            }

            NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentTitle("Task at " + Time)
                    .setAutoCancel(true)
                    .setContentText(title);
            Intent resultIntent = new Intent (context,ExpenseDetailActivity.class);
            resultIntent.putExtra("id",i);
            resultIntent.putExtra("ID",Id);
            resultIntent.putExtra(IntentConstants.EXPENSE_TITLE,title);
            resultIntent.putExtra("epoch",time);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(context,0,resultIntent,PendingIntent.FLAG_CANCEL_CURRENT);

            mbuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(i++,mbuilder.build());
        }
    }

