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

package org.mars3142.android.toaster.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.activity.SettingsActivity;
import org.mars3142.android.toaster.adapter.NavDrawerRecyclerAdapter;
import org.mars3142.android.toaster.card.ToastCard;
import org.mars3142.android.toaster.comparator.ToastCardComparator;
import org.mars3142.android.toaster.table.ToasterTable;
import org.mars3142.android.toaster.viewholder.NavDrawerRecyclerViewHolder;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author mars3142
 */
public class PackagesFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, NavDrawerRecyclerViewHolder.OnItemClickListener {

    private static final String STATE_SELECTED_PACKAGE_NAME = "selected_package_name";
    private static final int DATA_LOADER = 0;

    private PackagesCallbacks mCallbacks;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;
    private RecyclerView mDrawerRecyclerView;
    private String mCurrentSelectedPackageName = null;

    public PackagesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mCurrentSelectedPackageName = savedInstanceState.getString(STATE_SELECTED_PACKAGE_NAME);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.packages, container, false);

        RelativeLayout settings = (RelativeLayout) layout.findViewById(R.id.setting);
        settings.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDrawerRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);
        if (mDrawerRecyclerView != null) {
            mDrawerRecyclerView.setLayoutManager(layoutManager);
        }

        getLoaderManager().restartLoader(DATA_LOADER, null, this);

        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onItemClick(mCurrentSelectedPackageName);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallbacks = (PackagesCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement PackagesCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(STATE_SELECTED_PACKAGE_NAME, mCurrentSelectedPackageName);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mDrawerLayout != null && isDrawerOpen()) {
            showGlobalContextActionBar();
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        switch (loaderId) {
            case DATA_LOADER:
                return new CursorLoader(getActivity(), ToasterTable.PACKAGE_URI, null, null, null, null);

            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case DATA_LOADER:
                ArrayList<ToastCard> packageList = new ArrayList<>();

                if (data.moveToFirst()) {
                    do {
                        String packageName = data.getString(data.getColumnIndex(ToasterTable.PACKAGE));
                        ToastCard packageCard = new ToastCard(getActionBar().getThemedContext(), packageName);
                        if (packageCard.packageName != null) {
                            packageList.add(packageCard);
                        }
                    } while (data.moveToNext());
                }
                Collections.sort(packageList, new ToastCardComparator());
                mDrawerRecyclerView.setAdapter(new NavDrawerRecyclerAdapter(getActivity(), packageList, this));

                break;

            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case DATA_LOADER:
                mDrawerRecyclerView.setAdapter(null);
                break;

            default:
                throw new IllegalArgumentException();
        }
    }

    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
    }

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                (Toolbar) getActivity().findViewById(R.id.toolbar),
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getLoaderManager().restartLoader(DATA_LOADER, null, PackagesFragment.this);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                getLoaderManager().restartLoader(DATA_LOADER, null, PackagesFragment.this);
                getActivity().invalidateOptionsMenu();
            }
        };

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void onItemClick(String packageName) {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }

        if (mCallbacks != null && !(("" + packageName).equals("" + mCurrentSelectedPackageName))) {
            mCallbacks.onPackagesItemSelected(packageName);
        }

        mCurrentSelectedPackageName = packageName;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                getActivity().startActivity(intent);
                break;

            default:
                break;
        }
    }

    public interface PackagesCallbacks {
        void onPackagesItemSelected(String packageName);
    }
}
