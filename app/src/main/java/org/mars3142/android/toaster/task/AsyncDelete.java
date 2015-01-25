/*
 * This file is part of Toaster
 *
 * Copyright (c) 2015 Peter Siegmund
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

package org.mars3142.android.toaster.task;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

/**
 * @author mars3142
 */
public class AsyncDelete extends AsyncTask<Void, Void, Integer> {

    private final static String TAG = AsyncDelete.class.getSimpleName();

    private Context mContext;
    private Uri mUri;
    private String mWhere;
    private String[] mSelectionArgs;

    public AsyncDelete(Context context, Uri uri, String where, String[] selectionArgs) {
        mContext = context;
        mUri = uri;
        mWhere = where;
        mSelectionArgs = selectionArgs;
    }

    protected Integer doInBackground(Void... params) {
        Integer rows = -1;
        try {
            rows = mContext.getContentResolver().delete(mUri, mWhere, mSelectionArgs);
        } catch (Exception ex) {
            Log.e(TAG, "Error while delete");
        }

        return rows;
    }
}
