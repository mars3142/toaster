/*
 * Copyright (c) 2014.
 *
 * This file is part of Toaster.
 *
 * Toaster is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Toaster is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Toaster.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mars3142.android.toaster.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import org.mars3142.android.toaster.table.ToasterTable;

import java.util.Calendar;

/**
 * Accessibility service who fetches the notification
 *
 * <p>Sends the data to the database provider and informs every widget to update</p>
 *
 * @author mars3142
 */
public class ToasterService extends AccessibilityService {

    private final static String TAG = ToasterService.class.getSimpleName();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() != AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            Log.d(TAG, "Unexpected event type - ignoring");
            return; // event is not a notification
        }

        Calendar calendar = Calendar.getInstance();
        long timestamp = calendar.getTimeInMillis();
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
            ContentResolver cr = getContentResolver();
            ContentValues cv = new ContentValues();
            cv.put(ToasterTable.PACKAGE, sourcePackageName);
            cv.put(ToasterTable.MESSAGE, message);
            cv.put(ToasterTable.TIMESTAMP, timestamp);
            cr.insert(ToasterTable.TOASTER_URI, cv);

            Intent intent = new Intent("org.mars3142.android.toaster.APPWIDGET_UPDATE");
            sendBroadcast(intent);
        }
    }

    @Override
    public void onInterrupt() {
        // Nothing
    }
}
