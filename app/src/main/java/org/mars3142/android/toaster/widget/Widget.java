package org.mars3142.android.toaster.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import org.mars3142.android.toaster.BuildConfig;
import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.service.WidgetService;

import java.util.Arrays;

public class Widget extends AppWidgetProvider {

    private final static String TAG = Widget.class.getSimpleName();

    private static int[] mWidgetIds;

    public static void updateWidget(Context context, AppWidgetManager appWidgetManager) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.widget);
        widget.setRemoteAdapter(R.id.listView, intent);

        ComponentName componentName = new ComponentName(context, Widget.class);
        appWidgetManager.updateAppWidget(componentName, widget);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "onUpdate: [ " + Arrays.toString(appWidgetIds) + " ]");
        }

        updateWidget(context, appWidgetManager);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "onReceived");
        }

        updateWidget(context, AppWidgetManager.getInstance(context));
    }
}