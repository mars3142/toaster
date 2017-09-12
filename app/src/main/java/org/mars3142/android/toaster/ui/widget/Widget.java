/*
 * This file is part of Toaster
 *
 * Copyright (c) 2014, 2017 Peter Siegmund
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mars3142.android.toaster.ui.widget;

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

import timber.log.Timber;

/**
 * @author mars3142
 */
public class Widget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Timber.v("onUpdate: [ %s ]", Arrays.toString(appWidgetIds));

        updateWidget(context, appWidgetManager);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.v("onReceived");

        updateWidget(context, AppWidgetManager.getInstance(context));
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.widget);
        widget.setRemoteAdapter(R.id.listView, intent);

        ComponentName componentName = new ComponentName(context, Widget.class);
        appWidgetManager.updateAppWidget(componentName, widget);
    }
}