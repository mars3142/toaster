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

package org.mars3142.android.toaster.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.mars3142.android.toaster.R;

/**
 * RecyclerViewHolder
 *
 * @author mars3142
 */
public class NavDrawerRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnItemClickListener mOnItemClickListener;
    public ImageView mPackageIcon;
    public TextView mAppName;
    public String mPackageName;

    public NavDrawerRecyclerViewHolder(View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);

        mOnItemClickListener = onItemClickListener;
        mAppName = (TextView) itemView.findViewById(R.id.app_name);
        mPackageIcon = (ImageView) itemView.findViewById(R.id.package_icon);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(mPackageName);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String packageName);
    }
}
