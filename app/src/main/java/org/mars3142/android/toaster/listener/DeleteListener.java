/*
 * This file is part of Toaster
 *
 * Copyright (c) 2014 Peter Siegmund
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

package org.mars3142.android.toaster.listener;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;

import org.mars3142.android.toaster.task.AsyncDelete;

/**
 * Listener for calling a delete on the database provider
 *
 * @author mars3142
 */
public class DeleteListener
        implements DialogInterface.OnClickListener {

    private static final String TAG = DeleteListener.class.getSimpleName();

    private final Context mContext;
    private final Uri mUri;
    private final String mWhere;
    private final String[] mSelectionArgs;

    public DeleteListener(Context context, Uri uri) {
        this(context, uri, null, null);
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
            AsyncDelete task = new AsyncDelete(mContext, mUri, mWhere, mSelectionArgs);
            task.execute();
        }
    }
}
