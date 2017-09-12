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

import timber.log.Timber;

/**
 * @author mars3142
 */
public class PackageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.d("onReceive");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getBoolean(context.getString(R.string.delete_key), false)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            if (intent.getBooleanExtra(Intent.EXTRA_DATA_REMOVED, false)) {
                int rows = context.getContentResolver().delete(ToasterTable.TOASTER_URI, ToasterTable.PACKAGE + " = ?", new String[]{packageName});
                Timber.v("Removed %d entries for package %s", rows, packageName);
            } else {
                Timber.d("Package %s is not removed completely", packageName);
            }
        } else {
            Timber.d(context.getString(R.string.delete_key) + " is false");
        }
    }
}
