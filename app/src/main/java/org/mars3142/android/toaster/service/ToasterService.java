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

package org.mars3142.android.toaster.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Parcelable;
import android.view.accessibility.AccessibilityEvent;

import org.mars3142.android.toaster.BuildConfig;
import org.mars3142.android.toaster.table.ToasterTable;
import org.mars3142.android.toaster.task.AsyncInsert;

import java.util.Calendar;

import timber.log.Timber;

/**
 * Accessibility service who fetches the notification
 * <p/>
 * <p>Sends the data to the database provider and informs every widget to update</p>
 *
 * @author mars3142
 */
public class ToasterService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Timber.d("Event: %s", event);

        if (event.getEventType() != AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            Timber.d("Unexpected event type - ignoring");
            return; // event is not a notification
        }

        long timestamp = Calendar.getInstance().getTimeInMillis();
        String sourcePackageName = (String) event.getPackageName();
        String message = "";
        for (CharSequence text : event.getText()) {
            message += text + "\n";
        }
        if (message.length() > 0) {
            message = message.substring(0, message.length() - 1);
        }

        Parcelable parcelable = event.getParcelableData();
        if (!(parcelable instanceof Notification)) {
            ContentValues cv = new ContentValues();
            cv.put(ToasterTable.PACKAGE, sourcePackageName);
            cv.put(ToasterTable.MESSAGE, message);
            cv.put(ToasterTable.TIMESTAMP, timestamp);
            new AsyncInsert(this, ToasterTable.TOASTER_URI, cv).execute();

            Intent intent = new Intent(BuildConfig.APPLICATION_ID + ".APPWIDGET_UPDATE");
            sendBroadcast(intent);
        }
    }

    @Override
    public void onInterrupt() {
        // Nothing
    }
}
