package org.mars3142.android.toaster.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.mars3142.android.toaster.BuildConfig;
import org.mars3142.android.toaster.table.FilterTable;
import org.mars3142.android.toaster.table.ToasterTable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "database.db3";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, String.format("create database"));
        }
        ToasterTable.onCreate(db);
        FilterTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, String.format("Upgrading database from version %s to %s", oldVersion, newVersion));
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
