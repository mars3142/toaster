package org.mars3142.android.toaster.listener;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

public class AccessibilityServiceListener
        implements DialogInterface.OnClickListener {

    private final static String TAG = AccessibilityServiceListener.class.getSimpleName();

    private Context context;

    public AccessibilityServiceListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        context.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
    }
}
