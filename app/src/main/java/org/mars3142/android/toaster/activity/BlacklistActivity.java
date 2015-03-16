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

package org.mars3142.android.toaster.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.Loader;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageButton;

import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.adapter.PackageRecyclerAdapter;
import org.mars3142.android.toaster.data.PackageEntry;
import org.mars3142.android.toaster.helper.DividerItemDecoration;
import org.mars3142.android.toaster.loader.FilterLoader;
import org.mars3142.android.toaster.loader.PackageLoader;
import org.mars3142.android.toaster.table.FilterTable;

/**
 * @author mars3142
 */
public class BlacklistActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<PackageEntry[]>, View.OnClickListener {

    private static final String TAG = BlacklistActivity.class.getSimpleName();

    private static final int APPS_LOADER = 0;
    private static final int BLACKLIST_LOADER = 1;

    private ImageButton mFab;
    private RecyclerView mRecyclerView;
    private PackageEntry[] mData;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.filter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setElevation(getResources().getDimension(R.dimen.elevation_toolbar));
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        if (mRecyclerView != null) {
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        mFab = (ImageButton) findViewById(R.id.fab);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {

                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void getOutline(View view, Outline outline) {
                    int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
                    outline.setOval(0, 0, size, size);
                }
            };
            mFab.setOutlineProvider(viewOutlineProvider);
        }
        mFab.setOnClickListener(this);

        getLoaderManager().initLoader(APPS_LOADER, null, this).forceLoad();
        getLoaderManager().initLoader(BLACKLIST_LOADER, null, this).forceLoad();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<PackageEntry[]> onCreateLoader(int loaderId, Bundle args) {
        Bundle params = new Bundle();
        switch (loaderId) {
            case APPS_LOADER:
                mFab.setVisibility(View.GONE);
                params.putInt(PackageLoader.SOURCE, PackageLoader.SOURCE_APPS);
                return new PackageLoader(this, params);

            case BLACKLIST_LOADER:
                params.putString(FilterLoader.SELECTION, FilterTable.EXCL_INCL + " = ?");
                params.putStringArray(FilterLoader.SELECTION_ARGS, new String[]{"-1"});
                return new FilterLoader(this, params);

            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(Loader<PackageEntry[]> loader, PackageEntry[] data) {
        Log.d(TAG, "Loader #" + loader.getId() + " size " + data.length);

        switch (loader.getId()) {
            case APPS_LOADER:
                mData = data;
                mFab.setVisibility(View.VISIBLE);
                break;

            case BLACKLIST_LOADER:
                mRecyclerView.setAdapter(new PackageRecyclerAdapter(this, data));
                break;

            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoaderReset(Loader<PackageEntry[]> loader) {
        switch (loader.getId()) {
            case APPS_LOADER:
                mData = null;
                break;

            case BLACKLIST_LOADER:
                mRecyclerView.setAdapter(null);
                break;

            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onClick(View view) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        final Dialog appDialog = new Dialog(this);
        appDialog.setTitle(getString(R.string.filter_app));
        appDialog.setContentView(R.layout.filter_dialog);

        RecyclerView recyclerView = (RecyclerView) appDialog.findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new PackageRecyclerAdapter(this, mData));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        appDialog.show();
    }
}
