/*
 * Copyright (c) 2014.
 *
 * This file is part of Toaster.
 *
 * Foobar is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mars3142.android.toaster.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.table.FilterTable;

public class FilterActivity extends Activity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private final static String TAG = FilterActivity.class.getSimpleName();

    private final static int DATA_LOADER = 0;
    private AutoCompleteTextView packageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.filter);
        getLoaderManager().initLoader(DATA_LOADER, null, this);
        packageName = (AutoCompleteTextView) findViewById(R.id.packageName);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        switch (loaderId) {
            case DATA_LOADER:
                return new CursorLoader(this, FilterTable.FILTER_URI, null, FilterTable.EXCL_INCL + " = ?", new String[]{"-1"}, null);

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
