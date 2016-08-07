/*
 * This file is part of Toaster
 *
 * Copyright (c) 2014, 2016 Peter Siegmund
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

package org.mars3142.android.toaster.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.mars3142.android.toaster.BuildConfig;
import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.table.ToasterTable;

/**
 * @author mars3142
 */
public class PackageReceiver extends BroadcastReceiver {

    private static final String TAG = PackageReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onReceive");
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getBoolean(context.getString(R.string.delete_key), false)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            if (intent.getBooleanExtra(Intent.EXTRA_DATA_REMOVED, false)) {
                int rows = context.getContentResolver().delete(ToasterTable.TOASTER_URI, ToasterTable.PACKAGE + " = ?", new String[]{packageName});
                if (BuildConfig.DEBUG) {
                    Log.v(TAG, "Removed " + rows + " entries for package " + packageName);
                }
            } else {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "Package " + packageName + " is not removed completely");
                }
            }
        } else {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, context.getString(R.string.delete_key) + " is false");
            }
        }
    }
}
