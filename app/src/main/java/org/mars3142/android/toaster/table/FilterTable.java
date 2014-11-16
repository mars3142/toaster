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

    public final static String TABLENAME = "filter";
    public final static String PACKAGE = "package";
    public final static String EXCL_INCL = "excl_incl";
    public final static Uri FILTER_URI = Uri.parse("content://" + ToasterProvider.AUTHORITY + "/filter");
    public final static String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.mars3142.content.filter";
    public final static String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.mars3142.content.filter";
    private final static String TAG = FilterTable.class.getSimpleName();

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLENAME +
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
                        db.execSQL("ALTER TABLE " + TABLENAME + " ADD COLUMN " + PACKAGE + " TEXT;");
                        db.execSQL("ALTER TABLE " + TABLENAME + " ADD COLUMN " + EXCL_INCL + " INTEGER;");
                    } catch (SQLException ex) {
                        // upgrade already gone
                    }
                    break;

                default:
                    db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
                    onCreate(db);
            }
        }
    }

    public static void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // nothing
    }
}
