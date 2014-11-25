/*
 * Copyright (c) 2014.
 *
 * This file is part of Toaster.
 *
 * Toaster is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Toaster is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Toaster.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mars3142.android.toaster.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.mars3142.android.toaster.R;

/**
 * RecyclerViewHolder for the Navigation Drawer
 * @author mars3142
 */
public class PackagesRecyclerViewHolder extends RecyclerView.ViewHolder {

    private ImageView mPackageIcon;
    private TextView mPackageName;

    public PackagesRecyclerViewHolder(View itemView) {
        super(itemView);

        mPackageName = (TextView) itemView.findViewById(R.id.package_name);
        mPackageIcon = (ImageView) itemView.findViewById(R.id.package_icon);
    }

    public TextView getPackageName() {
        return mPackageName;
    }

    public ImageView getPackageIcon() {
        return mPackageIcon;
    }
}
