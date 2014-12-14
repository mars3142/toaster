/*
 * Copyright (c) 2014.
 *
 * This file is part of Toaster.
 *
 * Toaster is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Toaster is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Toaster.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mars3142.android.toaster.fragment;

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
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.activity.SettingsActivity;
import org.mars3142.android.toaster.adapter.PackagesRecyclerAdapter;
import org.mars3142.android.toaster.adapter.ToastArrayAdapter;
import org.mars3142.android.toaster.card.ToastCard;
import org.mars3142.android.toaster.comparator.ToastCardComparator;
import org.mars3142.android.toaster.table.ToasterTable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author mars3142
 */
public class PackagesFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener, AbsListView.OnScrollListener, View.OnClickListener {

    private static final String TAG = PackagesFragment.class.getSimpleName();
    private static final String STATE_SELECTED_POSITION = "selected_packages_position";
    private static final int DATA_LOADER = 0;

    private PackagesCallbacks mCallbacks;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;
    private ArrayList<ToastCard> mNavList;
    private RecyclerView mDrawerRecyclerView;
    private int mCurrentSelectedPosition = 0;

    public PackagesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
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

        mDrawerListView = (ListView) layout.findViewById(R.id.list_view);
        mDrawerListView.setOnItemClickListener(this);
        mDrawerListView.setOnScrollListener(this);

        RelativeLayout settings = (RelativeLayout) layout.findViewById(R.id.setting);
        settings.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDrawerRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);
        mDrawerRecyclerView.setLayoutManager(layoutManager);

        getLoaderManager().restartLoader(DATA_LOADER, null, this);

        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectItem(mCurrentSelectedPosition);
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

        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
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
                mNavList = new ArrayList<ToastCard>();

                ToastCard emptyCard = new ToastCard(getActionBar().getThemedContext());
                emptyCard.appName = getString(R.string.all_data);
                mNavList.add(emptyCard);

                if (data.moveToFirst()) {
                    do {
                        ToastCard packageCard = new ToastCard(getActionBar().getThemedContext());
                        packageCard.loadData(data.getString(data.getColumnIndex(ToasterTable.PACKAGE)));
                        if (packageCard.packageName != null) {
                            mNavList.add(packageCard);
                        }
                    } while (data.moveToNext());
                }
                Collections.sort(mNavList, new ToastCardComparator());
                Collections.swap(mNavList, mNavList.indexOf(emptyCard), 0);

                mDrawerRecyclerView.setAdapter(new PackagesRecyclerAdapter(mNavList));

                mDrawerListView.setAdapter(new ToastArrayAdapter(getActionBar().getThemedContext(), mNavList));
                mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
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
                mDrawerListView.setAdapter(null);
                break;

            default:
                throw new IllegalArgumentException();
        }
    }

    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mDrawerListView.setElevation(actionBar.getElevation());
            }
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

    private void selectItem(int position) {
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        mCurrentSelectedPosition = position;
        if (mCallbacks != null && mNavList != null && position < mNavList.size()) {
            mCallbacks.onPackagesItemSelected(mNavList.get(position).packageName);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch (scrollState) {
                case SCROLL_STATE_FLING:
                case SCROLL_STATE_TOUCH_SCROLL:
                    mDrawerListView.setTranslationZ(getResources().getDimension(R.dimen.elevation_toolbar));

                default:
                    mDrawerListView.setTranslationZ(0);
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // nothing
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

    public static interface PackagesCallbacks {
        void onPackagesItemSelected(String packageName);
    }
}
