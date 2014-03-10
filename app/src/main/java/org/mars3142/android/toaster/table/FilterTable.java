package org.mars3142.android.toaster.table;

import android.content.ContentResolver;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import org.mars3142.android.toaster.provider.ToasterProvider;

import java.io.FileFilter;

public class FilterTable
        implements BaseColumns {

    private final static String TAG = FilterTable.class.getSimpleName();

    public final static String TABLENAME = "filter";

    public final static String PACKAGE = "package";
    public final static String EXCL_INCL = "excl_incl";

    public static final Uri FILTER_URI = Uri.parse("content://" + ToasterProvider.AUTHORITY + "/filter");

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.mars3142.content.filter";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.mars3142.content.filter";

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
                    }
                    catch (SQLException ex) {
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
