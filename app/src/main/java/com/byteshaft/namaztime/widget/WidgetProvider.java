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

package com.byteshaft.namaztime.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.byteshaft.namaztime.R;
import com.byteshaft.namaztime.receivers.WidgetReceiver;

public class WidgetProvider extends AppWidgetProvider {

    public static void setupWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Intent receiver = new Intent(context, WidgetReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, receiver, 0);
        RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget);
        mRemoteViews.setOnClickPendingIntent(R.id.widget, pendingIntent);
        appWidgetManager.updateAppWidget(new ComponentName(context, WidgetProvider.class),
                mRemoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        setupWidget(context);
    }
}
