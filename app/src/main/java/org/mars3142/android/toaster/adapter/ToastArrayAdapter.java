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

package org.mars3142.android.toaster.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.card.ToastCard;

import java.util.ArrayList;

/**
 * ArrayAdapter for the Navigation Drawer
 *
 * @author mars3142
 */
public class ToastArrayAdapter extends ArrayAdapter<ToastCard> {

    private final Context mContext;
    private final ArrayList<ToastCard> mCards;

    public ToastArrayAdapter(Context context, ArrayList<ToastCard> cards) {
        this(context, R.layout.packages_row, cards);
    }

    public ToastArrayAdapter(Context context, @LayoutRes int resource, ArrayList<ToastCard> cards) {
        super(context, resource, cards);

        mContext = context;
        mCards = cards;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.packages_row, parent, false);
        }

        TextView packageName = (TextView) convertView.findViewById(R.id.package_name);
        if (packageName != null) {
            packageName.setText(mCards.get(position).appName);
        }

        ImageView packageIcon = (ImageView) convertView.findViewById(R.id.package_icon);
        if (packageIcon != null) {
            packageIcon.setImageDrawable(mCards.get(position).packageIcon);
        }

        return convertView;
    }
}
