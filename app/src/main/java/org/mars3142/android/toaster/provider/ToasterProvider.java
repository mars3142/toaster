package org.mars3142.android.toaster.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import org.mars3142.android.toaster.BuildConfig;
import org.mars3142.android.toaster.table.FilterTable;
import org.mars3142.android.toaster.table.ToasterTable;

import java.util.Arrays;
import java.util.HashMap;

public class ToasterProvider extends ContentProvider {

    public static final String AUTHORITY = "org.mars3142.android.toaster.provider";

    private final String TAG = this.getClass().getSimpleName();

    private static final String DATABASE_NAME = "database.db3";
    private static final int DATABASE_VERSION = 1;

    private static final int TOASTER = 1;
    private static final int TOASTER_ID = 2;
    private static final int PACKAGE = 3;
    private static final int FILTER = 4;
    private static final int FILTER_ID = 5;

    private static final HashMap<String, String> toasterMap;
    private static final HashMap<String, String> packageMap;
    private static final HashMap<String, String> filterMap;

    private static final UriMatcher mUriMatcher;

    private DatabaseHelper dbHelper;

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITY, "toaster", TOASTER);
        mUriMatcher.addURI(AUTHORITY, "toaster/#", TOASTER_ID);
        mUriMatcher.addURI(AUTHORITY, "packages", PACKAGE);
        mUriMatcher.addURI(AUTHORITY, "filter", FILTER);
        mUriMatcher.addURI(AUTHORITY, "filter/#", FILTER_ID);

        toasterMap = new HashMap<String, String>();
        toasterMap.put(ToasterTable.ID, ToasterTable.ID);
        toasterMap.put(ToasterTable.TIMESTAMP, ToasterTable.TIMESTAMP);
        toasterMap.put(ToasterTable.MESSAGE, ToasterTable.MESSAGE);
        toasterMap.put(ToasterTable.PACKAGE, ToasterTable.PACKAGE);

        packageMap = new HashMap<String, String>();
        packageMap.put(ToasterTable.PACKAGE, ToasterTable.PACKAGE);

        filterMap = new HashMap<String, String>();
        filterMap.put(FilterTable.ID, FilterTable.ID);
        filterMap.put(FilterTable.FILTER, FilterTable.FILTER);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private final String TAG = this.getClass().getSimpleName();

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + ToasterTable.TABLENAME +
                    " (" +
                    ToasterTable.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ToasterTable.TIMESTAMP + " LONG, " +
                    ToasterTable.PACKAGE + " TEXT, " +
                    ToasterTable.MESSAGE + " TEXT" +
                    ");");

            db.execSQL("CREATE TABLE " + FilterTable.TABLENAME +
                    " (" +
                    FilterTable.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FilterTable.FILTER + " TEXT" +
                    ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (BuildConfig.DEBUG) {
                Log.w(TAG, String.format("Upgrading database from version %s to %s", oldVersion, newVersion));
            }
            db.execSQL("DROP TABLENAME IF EXISTS " + ToasterTable.TABLENAME);
            db.execSQL("DROP TABLENAME IF EXISTS " + FilterTable.TABLENAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return false;
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
                queryBuilder.appendWhere(ToasterTable.ID + " = " + id);
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
                    sortOrder = FilterTable.FILTER + " ASC ";
                }
                break;

            case FILTER_ID:
                id = uri.getLastPathSegment();
                queryBuilder.setTables(FilterTable.TABLENAME);
                queryBuilder.setProjectionMap(filterMap);
                queryBuilder.appendWhere(FilterTable.ID + " = " + id);
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
    public synchronized Uri insert(Uri uri, ContentValues initialValues) {
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
            Uri lessonUri = ContentUris.withAppendedId(contentUri, rowId);
            getContext().getContentResolver().notifyChange(uri, null);
            return lessonUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public synchronized int delete(Uri uri, String selection, String[] selectionArgs) {
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
                    finalWhere = ToasterTable.ID + " = " + id;
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
                    finalWhere = FilterTable.ID + " = " + id;
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
    public synchronized int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
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
                    finalWhere = ToasterTable.ID + " = " + id;
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
                    finalWhere = FilterTable.ID + " = " + id;
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
