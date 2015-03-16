/*
 * This file is part of Toaster
 *
 * Copyright (c) 2015 Peter Siegmund
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

package org.mars3142.android.toaster.data;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

import java.io.File;

/**
 * @author mars3142
 *
 * inspired by https://github.com/alexjlockwood/AppListLoader/blob/master/src/com/adp/loadercustom/loader/AppEntry.java
 */
public class PackageEntry {

    private ApplicationInfo mInfo;

    private File mApkFile;
    private String mLabel;
    private Drawable mIcon;
    private Boolean mMounted;

    public PackageEntry(ApplicationInfo applicationInfo) {
        mInfo = applicationInfo;
        mApkFile = new File(mInfo.sourceDir);
    }

    public String getPackageName() {
        if (mInfo != null) {
            return mInfo.packageName;
        }

        return null;
    }

    public String getLabel(Context context) {
        if (mLabel == null || !mMounted) {
            if (!mApkFile.exists()) {
                mMounted = false;
                mLabel = mInfo.packageName;
            } else {
                mMounted = true;
                CharSequence label = mInfo.loadLabel(context.getPackageManager());
                mLabel = label != null ? label.toString() : mInfo.packageName;
            }
        }

        return mLabel;
    }

    public Drawable getIcon(Context context) {
        if (mIcon == null) {
            if (mApkFile.exists()) {
                mIcon = mInfo.loadIcon(context.getPackageManager());
                return mIcon;
            } else {
                mMounted = false;
            }
        } else if (!mMounted) {
            if (mApkFile.exists()) {
                mMounted = true;
                mIcon = mInfo.loadIcon(context.getPackageManager());
                return mIcon;
            }
        } else {
            return mIcon;
        }

        return context.getResources().getDrawable(android.R.drawable.sym_def_app_icon);
    }

    @Override
    public String toString() {
        return mLabel;
    }
}
