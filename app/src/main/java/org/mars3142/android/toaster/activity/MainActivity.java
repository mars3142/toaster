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

package org.mars3142.android.toaster.activity;

import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.mars3142.android.toaster.BuildConfig;
import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.fragment.NavigationDrawerFragment;
import org.mars3142.android.toaster.fragment.ToasterFragment;
import org.mars3142.android.toaster.helper.PackageHelper;
import org.mars3142.android.toaster.listener.AccessibilityServiceListener;
import org.mars3142.android.toaster.listener.DeleteListener;
import org.mars3142.android.toaster.service.ToasterService;
import org.mars3142.android.toaster.table.ToasterTable;

/**
 * @author mars3142
 */
public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private final static String TAG = MainActivity.class.getSimpleName();

    private final static String PACKAGE_NAME = "packagename";

    private NavigationDrawerFragment mNavDrawerFragment;
    private CharSequence mTitle;
    private String mPackageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTitle = getTitle();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setElevation(getResources().getInteger(R.integer.elevation_toolbar));
        }

        mNavDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        if (savedInstanceState != null) {
            mPackageName = savedInstanceState.getString(PACKAGE_NAME);
        }

        onNavigationDrawerItemSelected(mPackageName);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(PACKAGE_NAME, mPackageName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuRes = R.menu.nav_drawer_closed;
        if (mNavDrawerFragment != null) {
            if (!mNavDrawerFragment.isDrawerOpen()) {
                menuRes = R.menu.nav_drawer_closed;
            }
        }
        getMenuInflater().inflate(menuRes, menu);
        restoreActionBar();
        return true;
    }

    @Override
    public void onNavigationDrawerItemSelected(String packageFilter) {
        getFragmentManager().beginTransaction().replace(R.id.container, ToasterFragment.newInstance(packageFilter)).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_blacklist:
                intent = new Intent(this, FilterActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_delete:
                DeleteListener deleteListener;
                if (mPackageName.length() == 0) {
                    deleteListener = new DeleteListener(this);
                } else {
                    deleteListener = new DeleteListener(this, ToasterTable.PACKAGE + " = ?", new String[]{mPackageName});
                }
                new Builder(this)
                        .setTitle(R.string.action_delete)
                        .setMessage(R.string.delete_question)
                        .setCancelable(true)
                        .setPositiveButton(android.R.string.ok, deleteListener)
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
                return true;

            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isAccessibilitySettingsOn(getApplicationContext())) {
            new Builder(this)
                    .setTitle(R.string.toaster_service_header)
                    .setMessage(R.string.toaster_service_message)
                    .setCancelable(true)
                    .setPositiveButton(android.R.string.ok, new AccessibilityServiceListener(this))
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
        }
    }

    public void onSectionAttached(String packageName) {
        if (packageName.length() != 0) {
            mTitle = PackageHelper.getAppName(getApplicationContext(), packageName);
        } else {
            mTitle = getString(R.string.all_data);
        }
        mPackageName = packageName;
    }

    private void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mTitle);
        }
    }

    private boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = BuildConfig.APPLICATION_ID + "/" + ToasterService.class.getName();

        try {
            accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException ex) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: " + ex.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String serviceName = mStringColonSplitter.next();
                    if (serviceName.equalsIgnoreCase(service)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
