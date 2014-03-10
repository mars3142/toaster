package org.mars3142.android.toaster.factory;

import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
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

public class WidgetViewsFactory
        implements RemoteViewsService.RemoteViewsFactory {

    private final String TAG = WidgetViewsFactory.class.getSimpleName();

    private Context context = null;
    private ArrayList<String> packages = new ArrayList<String>();

    public WidgetViewsFactory(Context context, Intent intent) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "ctr");
        }

        this.context = context;

        ContentResolver cr = context.getContentResolver();
        Cursor data = cr.query(ToasterTable.TOASTER_URI, null, null, null, ToasterTable._ID + " DESC LIMIT 5");
        if (data != null) {
            packages.clear();
            data.moveToFirst();
            do {
                packages.add(data.getString(data.getColumnIndex(ToasterTable.PACKAGE)));
            } while (data.moveToNext());
        }
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
        // no-op
    }

    @Override
    public int getCount() {
        return packages.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "getViewAt( " + position + " )");
        }

        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.widget_row);
        ToastCard toastCard = new ToastCard(context);
        toastCard.packageName = packages.get(position);
        toastCard.loadData();

        row.setTextViewText(R.id.packageName, toastCard.appName);
        row.setImageViewBitmap(R.id.packageIcon, PackageHelper.drawableToBitmap(toastCard.packageIcon));

        return (row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
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
