/*
Copyright 2014 Peter Siegmund

This file is part of Toaster.

Foobar is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Foobar is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
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

    private final static String TAG = ToasterTable.class.getSimpleName();

    public final static String TABLENAME = "toaster";
    public final static String TIMESTAMP = "timestamp";
    public final static String PACKAGE = "package";
    public final static String MESSAGE = "message";
    public final static String VERSIONCODE = "version_code";
    public final static String VERSIONNAME = "version_name";
    public final static Uri TOASTER_URI = Uri.parse("content://" + ToasterProvider.AUTHORITY + "/toaster");
    public final static Uri PACKAGE_URI = Uri.parse("content://" + ToasterProvider.AUTHORITY + "/packages");
    public final static String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.mars3142.content.toaster";
    public final static String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.mars3142.content.toaster";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLENAME +
                " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TIMESTAMP + " LONG, " +
                PACKAGE + " TEXT, " +
                MESSAGE + " TEXT, " +
                VERSIONCODE + " INTEGER, " +
                VERSIONNAME + " TEXT " +
                ");");
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            switch (oldVersion) {
                case 1:
                    try {
                        db.execSQL("ALTER TABLE " + TABLENAME + " ADD COLUMN " + VERSIONCODE + " INTEGER;");
                        db.execSQL("ALTER TABLE " + TABLENAME + " ADD COLUMN " + VERSIONNAME + " TEXT;");
                    } catch (SQLException ex) {
                        // upgrade already done
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
