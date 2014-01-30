package org.mars3142.android.toaster.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Parcelable;
import android.view.accessibility.AccessibilityEvent;

import org.mars3142.android.toaster.table.ToasterTable;

import java.util.Calendar;

public class ToasterService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() != AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED)
            return; // event is not a notification

        Calendar calendar = Calendar.getInstance();
        long timestamp = calendar.getTimeInMillis();
        String sourcePackageName = (String) event.getPackageName();
        String message = "";
        for (int i = 0; i < event.getText().size(); i++) {
            message += event.getText().get(i) + "\n";
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
        }
    }

    @Override
    public void onInterrupt() {
        // Nothing
    }
}
