package com.example.myfirstapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class BreakService extends Service {
    private static final String TAG = "BreakService";
    private static final String BREAK_CHANNEL_ID = "breakChannel";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Perform your long-time task check here (e.g., app usage time)
        Runnable r = new Runnable() {
            @Override
            public void run() {
                while(true){
                    long futuretime=System.currentTimeMillis()+1800000;
                    while(System.currentTimeMillis()<futuretime){
                        synchronized (this){
                            try{
                                wait(futuretime-System.currentTimeMillis());
                                showBreakNotification();
                            }catch (Exception e){

                            }
                        }
                    }
                }
            }
        };
        Thread myThread=new Thread(r);
        myThread.start();


        // Return START_STICKY to ensure the service restarts if it gets terminated
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("com.example.myfirstapp","on destroy called");
    }


    private void showBreakNotification() {
        createNotificationChannel();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE  // Add FLAG_IMMUTABLE here
        );
        long[] vibrationPattern = {0, 1000, 500, 1000};

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, BREAK_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Take a Break!")
                .setContentText("It's time to take a break.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(vibrationPattern) // Set the vibration pattern
                .setDefaults(Notification.DEFAULT_VIBRATE);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "BreakChannel";
            String description = "Channel for break notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(BREAK_CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

