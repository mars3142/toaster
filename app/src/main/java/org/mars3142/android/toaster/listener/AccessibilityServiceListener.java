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

package org.mars3142.android.toaster.listener;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import timber.log.Timber;

/**
 * Listener for starting the accessibility settings activity
 *
 * @author mars3142
 */
public class AccessibilityServiceListener
        implements DialogInterface.OnClickListener {

    private final Context mContext;

    public AccessibilityServiceListener(Context context) {
        this.mContext = context;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Timber.e("Starting accessibility settings activity...");

        mContext.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
    }
}
