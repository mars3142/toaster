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

package org.mars3142.android.toaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.mars3142.android.toaster.R;

/**
 * @author mars3142
 */
public class PackageArrayAdapter extends ArrayAdapter<String> {

    private final static String TAG = PackageArrayAdapter.class.getSimpleName();

    private final Context mContext;
    private final String[] mPackages;

    public PackageArrayAdapter(Context context, int resource, String[] packages) {
        super(context, resource, packages);

        mContext = context;
        mPackages = packages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.filter_row, parent, false);

        TextView packageName = (TextView) rowView.findViewById(R.id.packageName);
        if (packageName != null) {
            packageName.setText(mPackages[position]);
        }

        return rowView;
    }
}
