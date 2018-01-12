/*
 *
 *  * (C) Copyright 2015 byteShaft Inc.
 *  *
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the GNU Lesser General Public License
 *  * (LGPL) version 2.1 which accompanies this distribution, and is available at
 *  * http://www.gnu.org/licenses/lgpl-2.1.html
 *  
 */

package com.byteshaft.namaztime;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.byteshaft.namaztime.widget.WidgetGlobals;

public class Notifications extends ContextWrapper {

    private final int SILENT_NOTIFICATION_ID = 56;
    private final int UPCOMING_NAMAZ_NOTIFICATION_ID = 57;
    private NotificationManager mNotificationManager = null;
    private NotificationChannel mChannel;
    private String CHANNEL_ID;
    int importance = 0;


    public Notifications(Context context) {
        super(context);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public void startPhoneSilentNotification() {
        NotificationCompat.Builder notification = buildPhoneSilentNotification();
        showNotification(SILENT_NOTIFICATION_ID, notification);
    }

    public void clearPhoneSilentNotification() {
        if (mNotificationManager != null) {
            mNotificationManager.cancel(SILENT_NOTIFICATION_ID);
        }
    }

    public void startUpcomingNamazNotification(String namaz) {
        NotificationCompat.Builder notification = buildUpcomingNamazNotification(namaz);
        showNotification(UPCOMING_NAMAZ_NOTIFICATION_ID, notification);
    }

    private void showNotification(int id, NotificationCompat.Builder notification) {
        mNotificationManager.notify(id, notification.build());
    }

    @SuppressLint("WrongConstant")
    private NotificationCompat.Builder buildPhoneSilentNotification() {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,
                CHANNEL_ID);
        Intent intent = new Intent(WidgetGlobals.SILENT_INTENT);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        notificationBuilder.setContentTitle("Namaz Time");
        notificationBuilder.setLargeIcon(bm);
        notificationBuilder.setContentText("Swipe to restore Ringtone");
        notificationBuilder.setSmallIcon(R.drawable.ic_mute);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(pIntent);
        notificationBuilder.setChannelId(CHANNEL_ID)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        CHANNEL_ID = getPackageName();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_HIGH;
        }
        CharSequence name = getString(R.string.app_name);// The user-visible name of the channel.

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(CHANNEL_ID);
            mNotificationManager.createNotificationChannel(mChannel);
        }
        return notificationBuilder;
    }

    @SuppressLint("WrongConstant")
    private NotificationCompat.Builder buildUpcomingNamazNotification(String namaz) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this
        , CHANNEL_ID);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        notificationBuilder.setContentTitle("Namaz Time at " + namaz);
        notificationBuilder.setLargeIcon(bm);
        notificationBuilder.setContentText("Slide to remove");
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.drawable.ic_notify);
        notificationBuilder.setVibrate(new long[]{250, 175, 250, 175, 250});
        notificationBuilder.setLights(Color.GREEN, 3000, 3000);
        notificationBuilder.setSound(uri);
        notificationBuilder.setChannelId(CHANNEL_ID)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        CHANNEL_ID = getPackageName();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_HIGH;
        }
        CharSequence name = getString(R.string.app_name);// The user-visible name of the channel.

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(CHANNEL_ID);
            mNotificationManager.createNotificationChannel(mChannel);
        }
        return notificationBuilder;
    }

    public void removeNotification() {
        mNotificationManager.cancel(UPCOMING_NAMAZ_NOTIFICATION_ID);
    }
}
