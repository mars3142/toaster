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

package org.mars3142.android.toaster.table;

import android.content.ContentResolver;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import org.mars3142.android.toaster.provider.ToasterProvider;

/**
 * @author mars3142
 */
public class FilterTable
        implements BaseColumns {

    private static final String TAG = FilterTable.class.getSimpleName();

    public static final String TABLE_NAME = "filter";
    public static final String PACKAGE = "package";
    public static final String EXCL_INCL = "excl_incl";
    public static final Uri FILTER_URI = Uri.parse("content://" + ToasterProvider.AUTHORITY + "/filter");
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.mars3142.content.filter";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.mars3142.content.filter";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PACKAGE + " TEXT," +
                EXCL_INCL + " INTEGER" +
                ");");
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            switch (oldVersion) {
                case 1:
                    try {
                        db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + PACKAGE + " TEXT;");
                        db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + EXCL_INCL + " INTEGER;");
                    } catch (SQLException ex) {
                        // upgrade already gone
                    }
                    break;

                default:
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
                    onCreate(db);
            }
        }
    }

    public static void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // nothing
    }
}
