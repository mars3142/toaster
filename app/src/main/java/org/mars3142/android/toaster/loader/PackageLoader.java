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

package org.mars3142.android.toaster.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import org.mars3142.android.toaster.data.PackageEntry;
import org.mars3142.android.toaster.observer.InstalledAppsObserver;
import org.mars3142.android.toaster.observer.SystemLocalObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author mars3142
 *
 * inspired by https://github.com/alexjlockwood/AppListLoader/blob/master/src/com/adp/loadercustom/loader/AppListLoader.java
 */
public class PackageLoader extends AsyncTaskLoader<PackageEntry[]> {

    /**
     * Key for source param
     */
    public static final String SOURCE = "source";

    /**
     * Parameter SOURCE is for apps
     */
    public static final int SOURCE_APPS = 0;

    /**
     * Parameter SOURCE is for database
     */
    public static final int SOURCE_DATABASE = 1;

    private int mSource;
    private PackageManager mManager;
    private InstalledAppsObserver mAppsObserver;
    private SystemLocalObserver mLocalObserver;

    public PackageLoader(Context context, Bundle params) {
        super(context);

        if (params != null) {
            mSource = params.getInt(SOURCE, SOURCE_APPS);
            if (mSource < SOURCE_APPS || mSource > SOURCE_DATABASE) {
                throw new IllegalArgumentException("Parameter source has to be between " + SOURCE_APPS + " and " + SOURCE_DATABASE);
            }
        }

        mManager = getContext().getPackageManager();
    }

    @Override
    public PackageEntry[] loadInBackground() {
        PackageEntry[] result = null;
        ArrayList<PackageEntry> entries = null;

        switch (mSource) {
            case SOURCE_DATABASE:
                break;

            case SOURCE_APPS:
            default:
                List<ApplicationInfo> apps = mManager.getInstalledApplications(0);

                if (apps == null) {
                    apps = new ArrayList<>();
                }

                entries = new ArrayList<>(apps.size());
                for (int i = 0; i < apps.size(); i++) {
                    PackageEntry entry = new PackageEntry(apps.get(i));
                    entry.getLabel(getContext());
                    entries.add(entry);
                }
                break;
        }

        if (entries != null) {
            Collections.sort(entries, new Comparator<PackageEntry>() {
                @Override
                public int compare(PackageEntry lhs, PackageEntry rhs) {
                    return lhs.toString().compareToIgnoreCase(rhs.toString());
                }
            });
            result = entries.toArray(new PackageEntry[entries.size()]);
        }

        return result;
    }

    @Override
    protected void onStartLoading() {
        if (mAppsObserver == null && mSource == SOURCE_APPS) {
            mAppsObserver = new InstalledAppsObserver(this);
        }

        if (mLocalObserver == null) {
            mLocalObserver = new SystemLocalObserver(this);
        }
    }

    @Override
    protected void onReset() {
        if (mAppsObserver != null) {
            getContext().unregisterReceiver(mAppsObserver);
            mAppsObserver = null;
        }

        if (mLocalObserver != null) {
            getContext().unregisterReceiver(mLocalObserver);
            mLocalObserver = null;
        }
    }
}
