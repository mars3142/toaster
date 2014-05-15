/*
 * Copyright (c) 2014.
 *
 * This file is part of Toaster.
 *
 * Foobar is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mars3142.android.toaster.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import org.mars3142.android.toaster.BuildConfig;
import org.mars3142.android.toaster.helper.DatabaseHelper;
import org.mars3142.android.toaster.table.FilterTable;
import org.mars3142.android.toaster.table.ToasterTable;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author mars3142
 */
public class ToasterProvider extends ContentProvider {

    public final static String AUTHORITY = "org.mars3142.android.toaster.provider";

    private final static String TAG = ToasterProvider.class.getSimpleName();
    private final static int TOASTER = 1;
    private final static int TOASTER_ID = 2;
    private final static int PACKAGE = 3;
    private final static int FILTER = 4;
    private final static int FILTER_ID = 5;
    private final static HashMap<String, String> toasterMap;
    private final static HashMap<String, String> packageMap;
    private final static HashMap<String, String> filterMap;
    private final static UriMatcher mUriMatcher;

    private DatabaseHelper dbHelper;

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITY, "toaster", TOASTER);
        mUriMatcher.addURI(AUTHORITY, "toaster/#", TOASTER_ID);
        mUriMatcher.addURI(AUTHORITY, "packages", PACKAGE);
        mUriMatcher.addURI(AUTHORITY, "filter", FILTER);
        mUriMatcher.addURI(AUTHORITY, "filter/#", FILTER_ID);

        toasterMap = new HashMap<String, String>();
        toasterMap.put(ToasterTable._ID, ToasterTable._ID);
        toasterMap.put(ToasterTable.TIMESTAMP, ToasterTable.TIMESTAMP);
        toasterMap.put(ToasterTable.MESSAGE, ToasterTable.MESSAGE);
        toasterMap.put(ToasterTable.PACKAGE, ToasterTable.PACKAGE);
        toasterMap.put(ToasterTable.VERSIONCODE, ToasterTable.VERSIONCODE);
        toasterMap.put(ToasterTable.VERSIONNAME, ToasterTable.VERSIONNAME);
        toasterMap.put(ToasterTable._COUNT, ToasterTable._COUNT);

        packageMap = new HashMap<String, String>();
        packageMap.put(ToasterTable.PACKAGE, ToasterTable.PACKAGE);

        filterMap = new HashMap<String, String>();
        filterMap.put(FilterTable._ID, FilterTable._ID);
        filterMap.put(FilterTable.PACKAGE, FilterTable.PACKAGE);
        filterMap.put(FilterTable.EXCL_INCL, FilterTable.EXCL_INCL);
        filterMap.put(FilterTable._COUNT, FilterTable._COUNT);
    }


    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "query: uri=" + uri + " projection=" + Arrays.toString(projection) +
                    " selection=[" + selection + "]  args=" + Arrays.toString(selectionArgs) +
                    " order=[" + sortOrder + "]");
        }

        String id;
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (mUriMatcher.match(uri)) {
            case TOASTER:
                queryBuilder.setTables(ToasterTable.TABLENAME);
                queryBuilder.setProjectionMap(toasterMap);
                if (sortOrder == null) {
                    sortOrder = ToasterTable.TIMESTAMP + " DESC ";
                }
                break;

            case TOASTER_ID:
                id = uri.getLastPathSegment();
                queryBuilder.setTables(ToasterTable.TABLENAME);
                queryBuilder.setProjectionMap(toasterMap);
                queryBuilder.appendWhere(ToasterTable._ID + " = " + id);
                break;

            case PACKAGE:
                queryBuilder.setTables(ToasterTable.TABLENAME);
                queryBuilder.setProjectionMap(packageMap);
                queryBuilder.setDistinct(true);
                if (sortOrder == null) {
                    sortOrder = ToasterTable.PACKAGE + " ASC ";
                }
                break;

            case FILTER:
                queryBuilder.setTables(FilterTable.TABLENAME);
                queryBuilder.setProjectionMap(filterMap);
                if (sortOrder == null) {
                    sortOrder = FilterTable.PACKAGE + " ASC ";
                }
                break;

            case FILTER_ID:
                id = uri.getLastPathSegment();
                queryBuilder.setTables(FilterTable.TABLENAME);
                queryBuilder.setProjectionMap(filterMap);
                queryBuilder.appendWhere(FilterTable._ID + " = " + id);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        if (database != null) {
            cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
        }
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "getType: uri=" + uri);
        }

        switch (mUriMatcher.match(uri)) {
            case TOASTER:
                return ToasterTable.CONTENT_TYPE;

            case TOASTER_ID:
                return ToasterTable.CONTENT_ITEM_TYPE;

            case FILTER:
                return FilterTable.CONTENT_TYPE;

            case FILTER_ID:
                return FilterTable.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "insert: uri=" + uri + " initialValues=[" + initialValues.toString() + "]");
        }

        String tableName;
        Uri contentUri;
        switch (mUriMatcher.match(uri)) {
            case TOASTER:
                tableName = ToasterTable.TABLENAME;
                contentUri = ToasterTable.TOASTER_URI;
                break;

            case FILTER:
                tableName = FilterTable.TABLENAME;
                contentUri = FilterTable.FILTER_URI;
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long rowId = 0;
        if (database != null) {
            rowId = database.insert(tableName, null, values);
        }
        if (rowId > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return ContentUris.withAppendedId(contentUri, rowId);
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "delete: uri=" + uri + " selection=[" + selection + "]  args=" + Arrays.toString(selectionArgs));
        }

        int count = 0;
        String id;
        String finalWhere;
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        if (database != null) {
            switch (mUriMatcher.match(uri)) {
                case TOASTER:
                    count = database.delete(ToasterTable.TABLENAME, selection, selectionArgs);
                    break;

                case TOASTER_ID:
                    id = uri.getLastPathSegment();
                    finalWhere = ToasterTable._ID + " = " + id;
                    if (selection != null) {
                        finalWhere += " AND " + selection;
                    }
                    count = database.delete(ToasterTable.TABLENAME, finalWhere, selectionArgs);
                    break;

                case FILTER:
                    count = database.delete(FilterTable.TABLENAME, selection, selectionArgs);
                    break;

                case FILTER_ID:
                    id = uri.getLastPathSegment();
                    finalWhere = FilterTable._ID + " = " + id;
                    if (selection != null) {
                        finalWhere += " AND " + selection;
                    }
                    count = database.delete(FilterTable.TABLENAME, finalWhere, selectionArgs);
                    break;

                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "update: uri=" + uri + " values=[" + values.toString() + "] selection=[" + selection + "]" +
                    " args=" + Arrays.toString(selectionArgs));
        }

        int count = 0;
        String id;
        String finalWhere;
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        if (database != null) {
            switch (mUriMatcher.match(uri)) {
                case TOASTER:
                    count = database.update(ToasterTable.TABLENAME, values, selection, selectionArgs);
                    break;

                case TOASTER_ID:
                    id = uri.getLastPathSegment();
                    finalWhere = ToasterTable._ID + " = " + id;
                    if (selection != null) {
                        finalWhere += " AND " + selection;
                    }
                    count = database.update(ToasterTable.TABLENAME, values, finalWhere, selectionArgs);
                    break;

                case FILTER:
                    count = database.update(FilterTable.TABLENAME, values, selection, selectionArgs);
                    break;

                case FILTER_ID:
                    id = uri.getLastPathSegment();
                    finalWhere = FilterTable._ID + " = " + id;
                    if (selection != null) {
                        finalWhere += " AND " + selection;
                    }
                    count = database.update(FilterTable.TABLENAME, values, finalWhere, selectionArgs);
                    break;

                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
