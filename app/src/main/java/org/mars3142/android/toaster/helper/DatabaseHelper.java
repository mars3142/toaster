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

package org.mars3142.android.toaster.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.mars3142.android.toaster.BuildConfig;
import org.mars3142.android.toaster.table.FilterTable;
import org.mars3142.android.toaster.table.ToasterTable;

/**
 * Helper class for database request
 * <p/>
 * <p>It calls create/update/downgrade for every table in the database.</p>
 *
 * @author mars3142
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "database.db3";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, String.format("Create database"));
        }

        ToasterTable.onCreate(db);
        FilterTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, String.format("Upgrading database from version %s to %s", oldVersion, newVersion));
        }

        ToasterTable.onUpgrade(db, oldVersion, newVersion);
        FilterTable.onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, String.format("Downgrading database from version %s to %s", oldVersion, newVersion));
        }

        ToasterTable.onDowngrade(db, oldVersion, newVersion);
        FilterTable.onDowngrade(db, oldVersion, newVersion);
    }
}
