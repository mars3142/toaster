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

package org.mars3142.android.toaster.listener;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import org.mars3142.android.toaster.BuildConfig;

/**
 * Listener for starting the accessibility settings activity
 *
 * @author mars3142
 */
public class AccessibilityServiceListener
        implements DialogInterface.OnClickListener {

    private static final String TAG = AccessibilityServiceListener.class.getSimpleName();

    private final Context mContext;

    public AccessibilityServiceListener(Context context) {
        this.mContext = context;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "Starting accessibility settings activity...");
        }

        mContext.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
    }
}
