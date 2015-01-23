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
public class PackagesRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = PackagesRecyclerViewHolder.class.getSimpleName();

    private OnItemClickListener mOnItemClickListener;
    private ImageView mPackageIcon;
    private TextView mPackageName;

    public PackagesRecyclerViewHolder(View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);

        mOnItemClickListener = onItemClickListener;
        mPackageName = (TextView) itemView.findViewById(R.id.package_name);
        mPackageIcon = (ImageView) itemView.findViewById(R.id.package_icon);
    }

    public TextView getPackageName() {
        return mPackageName;
    }

    public ImageView getPackageIcon() {
        return mPackageIcon;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view, getPosition());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }
}
