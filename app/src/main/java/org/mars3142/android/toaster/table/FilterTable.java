package org.mars3142.android.toaster.table;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import org.mars3142.android.toaster.provider.ToasterProvider;

public class FilterTable {
    public final static String TABLENAME = "filter";

    public final static String ID = BaseColumns._ID;
    public final static String FILTER = "filter";

    public static final Uri FILTER_URI = Uri.parse("content://" + ToasterProvider.AUTHORITY + "/filter");

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.mars3142.content.filter";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.mars3142.content.filter";
}
