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

package org.mars3142.android.toaster.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import org.mars3142.android.toaster.BuildConfig;
import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.activity.BlacklistActivity;
import org.mars3142.android.toaster.receiver.PackageReceiver;

/**
 * @author mars3142
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        Preference preference = findPreference(getString(R.string.blacklist_key));
        if (preference != null) {
            preference.setOnPreferenceClickListener(this);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (isAdded()) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onSharedPreferenceChanged of " + key);
            }

            if (key.equals(getString(R.string.delete_key))) {
                boolean enabled = sharedPreferences.getBoolean(key, false);
                int flag = (enabled ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED);

                ComponentName component = new ComponentName(getActivity(), PackageReceiver.class);
                getActivity().getPackageManager().setComponentEnabledSetting(component, flag, PackageManager.DONT_KILL_APP);
            }
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onPreferenceClick on " + preference.getKey());
        }

        if (preference.getKey().equals(getString(R.string.blacklist_key))) {
            Intent intent = new Intent(getActivity(), BlacklistActivity.class);
            getActivity().startActivity(intent);
            return true;
        }

        return false;
    }

}
