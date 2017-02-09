/*
 * This file is part of Toaster
 *
 * Copyright (c) 2014, 2017 Peter Siegmund
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
public class ToasterTable
        implements BaseColumns {

    private static final String TAG = ToasterTable.class.getSimpleName();
    
    public static final String TABLE_NAME = "toaster";
    public static final String TIMESTAMP = "timestamp";
    public static final String PACKAGE = "package";
    public static final String MESSAGE = "message";
    public static final String VERSION_CODE = "version_code";
    public static final String VERSION_NAME = "version_name";
    public static final Uri TOASTER_URI = Uri.parse("content://" + ToasterProvider.AUTHORITY + "/toaster");
    public static final Uri PACKAGE_URI = Uri.parse("content://" + ToasterProvider.AUTHORITY + "/packages");
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.mars3142.content.toaster";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.mars3142.content.toaster";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TIMESTAMP + " LONG, " +
                PACKAGE + " TEXT, " +
                MESSAGE + " TEXT, " +
                VERSION_CODE + " INTEGER, " +
                VERSION_NAME + " TEXT " +
                ");");
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            switch (oldVersion) {
                case 1:
                    try {
                        db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + VERSION_CODE + " INTEGER;");
                        db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + VERSION_NAME + " TEXT;");
                    } catch (SQLException ex) {
                        // upgrade already done
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
