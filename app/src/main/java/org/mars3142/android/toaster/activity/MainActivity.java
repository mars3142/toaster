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

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

import org.mars3142.android.toaster.BuildConfig;
import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.fragment.PackagesFragment;
import org.mars3142.android.toaster.fragment.ToasterFragment;
import org.mars3142.android.toaster.helper.PackageHelper;
import org.mars3142.android.toaster.listener.AccessibilityServiceListener;
import org.mars3142.android.toaster.listener.DeleteListener;
import org.mars3142.android.toaster.service.ToasterService;
import org.mars3142.android.toaster.table.ToasterTable;
import org.mars3142.android.toaster.viewholder.NavDrawerRecyclerViewHolder;

/**
 * MainActivity of Toaster
 * <p/>
 * It's the main entry point for the app
 *
 * @author mars3142
 */
public class MainActivity extends AppCompatActivity
        implements PackagesFragment.PackagesCallbacks, NavDrawerRecyclerViewHolder.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String PACKAGE_NAME = "packageName";

    private PackagesFragment mPackagesFragment;
    private CharSequence mTitle;
    private String mPackageName;
    private Toolbar mToolbar;
    private Boolean mToobarVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTitle = getTitle();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setHomeButtonEnabled(false);
                getSupportActionBar().setElevation(getResources().getDimension(R.dimen.elevation_toolbar));
            }
        }

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout != null) {
            mPackagesFragment = (PackagesFragment) getFragmentManager().findFragmentById(R.id.packages);
            mPackagesFragment.setUp(R.id.packages, drawerLayout);
        }

        if (savedInstanceState != null) {
            mPackageName = savedInstanceState.getString(PACKAGE_NAME);
        }

        onPackagesItemSelected(mPackageName);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(PACKAGE_NAME, mPackageName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuRes = R.menu.main_options;
        if (mPackagesFragment != null) {
            if (!mPackagesFragment.isDrawerOpen()) {
                menuRes = R.menu.main_options;
            }
        }
        getMenuInflater().inflate(menuRes, menu);
        restoreActionBar();
        return true;
    }

    @Override
    public void onPackagesItemSelected(String packageFilter) {
        getFragmentManager().beginTransaction().replace(R.id.container, ToasterFragment.newInstance(packageFilter)).commit();
    }

    @Override
    public void onItemClick(String packageName) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, ToasterFragment.newInstance(packageName));
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_blacklist:
                intent = new Intent(this, BlacklistActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_delete:
                DeleteListener deleteListener;
                if (TextUtils.isEmpty(mPackageName)) {
                    deleteListener = new DeleteListener(this, ToasterTable.TOASTER_URI);
                } else {
                    deleteListener = new DeleteListener(this, ToasterTable.TOASTER_URI, ToasterTable.PACKAGE + " = ?", new String[]{mPackageName});
                }
                new AlertDialog.Builder(this)
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
            new AlertDialog.Builder(this)
                    .setTitle(R.string.toaster_service_header)
                    .setMessage(R.string.toaster_service_message)
                    .setCancelable(true)
                    .setPositiveButton(android.R.string.ok, new AccessibilityServiceListener(this))
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
        }
    }

    /**
     * Changes member variables depending on the given package name
     *
     * @param packageName new package name
     */
    public void onSectionAttached(String packageName) {
        if (mPackagesFragment != null) {
            if (!TextUtils.isEmpty(packageName)) {
                mTitle = PackageHelper.with(this).getAppName(packageName);
            } else {
                mTitle = getString(R.string.all_data);
            }
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

    /**
     * Checks if the app is set as accessibility
     *
     * @param context current context
     * @return true, if set
     */
    private boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        final String service = BuildConfig.APPLICATION_ID + "/" + ToasterService.class.getName();

        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException ex) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: " + ex.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(context.getApplicationContext().getContentResolver(),
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

    public void toggleToolbarVisibility() {
        // Animate Toolbar
        ObjectAnimator mAnimatorToolbar;
        if (mToobarVisible) {
            mAnimatorToolbar = ObjectAnimator.ofFloat(mToolbar, "y", mToolbar.getHeight() * -1);
            mAnimatorToolbar.setInterpolator(new LinearInterpolator());
            mAnimatorToolbar.addListener(new Animator.AnimatorListener() {
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mToobarVisible = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        } else { // show
            mAnimatorToolbar = ObjectAnimator.ofFloat(mToolbar, "y", 0);
            mAnimatorToolbar.setInterpolator(new BounceInterpolator());
            mAnimatorToolbar.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mToobarVisible = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        mAnimatorToolbar.setDuration(400);
        mAnimatorToolbar.start();
    }
}
