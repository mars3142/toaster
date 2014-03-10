package org.mars3142.android.toaster.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.table.FilterTable;

public class FilterActivity extends Activity
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private final static String TAG = FilterActivity.class.getSimpleName();

    private final static int DATA_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.filter);
        getLoaderManager().initLoader(DATA_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        switch (loaderId) {
            case DATA_LOADER:
                return new CursorLoader(this, FilterTable.FILTER_URI, null, FilterTable.EXCL_INCL + " = ?", new String[] { "-1" }, null);

            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case DATA_LOADER:
                break;

            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case DATA_LOADER:
                break;

            default:
                throw new IllegalArgumentException();
        }
    }
}
