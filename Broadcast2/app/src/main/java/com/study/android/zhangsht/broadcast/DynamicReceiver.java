package com.study.android.zhangsht.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Created by zhangsht on 2016/10/20.
 */

public class DynamicReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        if (intent.getAction().equals("DynamicAction")) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String text = bundle.getString("text");
                rv.setImageViewResource(R.id.appwidget_img, R.mipmap.dynamic);
                rv.setTextViewText(R.id.appwidget_text, text);
                AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, AppWidget.class), rv);

                Notification.Builder builder = new Notification.Builder(context);
                builder.setContentTitle("动态广播")
                        .setContentText(text)
                        .setTicker("您有一条新消息")
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.dynamic))
                        .setSmallIcon(R.mipmap.dynamic)
                        .setAutoCancel(false);
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                Intent myIntent = new Intent(context, MainActivity.class);
                PendingIntent myPendingIntent = PendingIntent.getActivity(context, 0, myIntent, 0);
                builder.setContentIntent(myPendingIntent);

                Notification notification = builder.build();
                manager.notify(0, notification);
            }
        }
    }
}
