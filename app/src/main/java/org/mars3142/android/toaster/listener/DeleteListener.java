package org.mars3142.android.toaster.listener;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;

import org.mars3142.android.toaster.table.ToasterTable;

public class DeleteListener
        implements DialogInterface.OnClickListener {

    private final static String TAG = DeleteListener.class.getSimpleName();

    private final Context mContext;
    private final Uri mUri;
    private final String mWhere;
    private final String[] mSelectionArgs;

    public DeleteListener(Context context) {
        this(context, ToasterTable.TOASTER_URI, null, null);
    }

    public DeleteListener(Context context, Uri uri) {
        this(context, uri, null, null);
    }

    public DeleteListener(Context context, String where, String[] selectionArgs) {
        this(context, ToasterTable.TOASTER_URI, where, selectionArgs);
    }

    public DeleteListener(Context context, Uri uri, String where, String[] selectionArgs) {
        mContext = context;
        mUri = uri;
        mWhere = where;
        mSelectionArgs = selectionArgs;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mUri != null) {
            ContentResolver cr = mContext.getContentResolver();
            cr.delete(mUri, mWhere, mSelectionArgs);
        }
    }
}
