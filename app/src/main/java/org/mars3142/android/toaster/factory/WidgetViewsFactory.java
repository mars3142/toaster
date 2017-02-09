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

package org.mars3142.android.toaster.factory;

import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.mars3142.android.toaster.BuildConfig;
import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.card.ToastCard;
import org.mars3142.android.toaster.helper.PackageHelper;
import org.mars3142.android.toaster.table.ToasterTable;

import java.util.ArrayList;

/**
 * @author mars3142
 */
public class WidgetViewsFactory
        implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = WidgetViewsFactory.class.getSimpleName();

    private Context mContext;
    private ArrayList<String> mPackages;

    public WidgetViewsFactory(Context context) {
        mContext = context;

        mPackages = new ArrayList<>();

        ContentResolver cr = context.getContentResolver();
        Cursor data = cr.query(ToasterTable.TOASTER_URI, new String[]{ToasterTable.PACKAGE}, null, null, ToasterTable._ID + " DESC LIMIT 5");
        if (data.moveToFirst()) {
            do {
                mPackages.add(data.getString(data.getColumnIndex(ToasterTable.PACKAGE)));
            } while (data.moveToNext());
        }
        data.close();
    }

    @Override
    public void onCreate() {
        // no-op
    }

    @Override
    public void onDataSetChanged() {
        // no-op
    }

    @Override
    public void onDestroy() {
        mContext = null;
    }

    @Override
    public int getCount() {
        return mPackages.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "getViewAt( " + position + " )");
        }

        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.widget_row);
        ToastCard toastCard = new ToastCard(mContext, mPackages.get(position));

        row.setTextViewText(R.id.package_name, toastCard.appName);
        row.setImageViewBitmap(R.id.package_icon, PackageHelper.drawableToBitmap(toastCard.packageIcon));

        int color = mContext.getResources().getColor(R.color.colorPrimary);
        if (toastCard.palette != null) {
            color = toastCard.palette.getMutedColor(color);
        }
        row.setInt(R.id.row, "setBackgroundColor", color);

        return (row);
    }

    @Override
    public RemoteViews getLoadingView() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "getLoadingView");
        }

        return new RemoteViews(mContext.getPackageName(), R.layout.widget_loading);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
