/*
 * This file is part of Toaster
 *
 * Copyright (c) 2014, 2016 Peter Siegmund
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

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

/**
 * @author mars3142
 */
public class AsyncInsert extends AsyncTask<Void, Void, Uri>{

    private final static String TAG = AsyncInsert.class.getSimpleName();

    private Context mContext;
    private Uri mUri;
    private ContentValues mValues;

    public AsyncInsert(Context context, Uri uri, ContentValues values) {
        mContext = context;
        mUri = uri;
        mValues = values;
    }

    @Override
    protected Uri doInBackground(Void... params) {
        Uri result = null;
        try {
            result = mContext.getContentResolver().insert(mUri, mValues);
        } catch (Exception ex) {
            Log.e(TAG, "Error while insert");
        }

        return result;
    }
}
