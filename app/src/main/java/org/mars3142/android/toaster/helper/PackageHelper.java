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

package org.mars3142.android.toaster.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.HashMap;

/**
 * @author mars3142
 */
public class PackageHelper {

    private static PackageHelper mInstance;

    private Context mContext;
    private HashMap<String, String> mAppNames;
    private BitmapLruCache mBitmapCache;

    private PackageHelper(Context context) {
        mContext = context;
        mAppNames = new HashMap<>();
        mBitmapCache = new BitmapLruCache();
    }

    public synchronized static PackageHelper with(Context context) {
        if (mInstance == null) {
            mInstance = new PackageHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public String getAppName(String packageName) {
        if (mAppNames.containsKey(packageName)) {
            return mAppNames.get(packageName);
        } else {
            try {
                PackageManager pm = mContext.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(packageName, 0);
                String appName = pi.applicationInfo.loadLabel(mContext.getPackageManager()).toString();
                mAppNames.put(packageName, appName);
                return appName;
            } catch (Exception e) {
                // Nothing
            }
        }
        return packageName;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Drawable getIconFromPackageName(String packageName) {
        Bitmap bitmap = mBitmapCache.get(packageName);
        if (bitmap != null) {
            return new BitmapDrawable(mContext.getResources(), bitmap);
        }

        PackageManager pm = mContext.getPackageManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            try {
                PackageInfo pi = pm.getPackageInfo(packageName, 0);
                Context otherAppCtx = mContext.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);

                int displayMetrics[] = {DisplayMetrics.DENSITY_XHIGH, DisplayMetrics.DENSITY_HIGH, DisplayMetrics.DENSITY_TV,
                        DisplayMetrics.DENSITY_MEDIUM, DisplayMetrics.DENSITY_LOW};

                for (int displayMetric : displayMetrics) {
                    try {
                        Drawable drawable;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            drawable = otherAppCtx.getResources().getDrawableForDensity(pi.applicationInfo.icon, displayMetric, null);
                        } else {
                            drawable = otherAppCtx.getResources().getDrawableForDensity(pi.applicationInfo.icon, displayMetric);
                        }
                        if (drawable != null) {
                            mBitmapCache.put(packageName, drawableToBitmap(drawable));
                            return drawable;
                        }
                    } catch (Resources.NotFoundException e) {
                        continue;
                    }
                }
            } catch (Exception e) {
                // Handle Error here
            }
        }

        ApplicationInfo appInfo;
        try {
            appInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            return appInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            // Nothing
        }
        return null;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
