package org.mars3142.android.toaster.table;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import org.mars3142.android.toaster.provider.ToasterProvider;

public class ToasterTable {
    public final static String TABLENAME = "toaster";

    public final static String ID = BaseColumns._ID;
    public final static String PACKAGE = "package";
    public final static String MESSAGE = "message";
    public final static String TIMESTAMP = "timestamp";

    public static final Uri TOASTER_URI = Uri.parse("content://" + ToasterProvider.AUTHORITY + "/toaster");
    public static final Uri PACKAGE_URI = Uri.parse("content://" + ToasterProvider.AUTHORITY + "/packages");

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.mars3142.content.toaster";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.mars3142.content.toaster";
}
