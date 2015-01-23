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

package org.mars3142.android.toaster.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.data.PackageEntry;
import org.mars3142.android.toaster.viewholder.PackagesRecyclerViewHolder;

/**
 * @author mars3142
 */
public class PackageRecyclerAdapter extends RecyclerView.Adapter<PackagesRecyclerViewHolder> {

    private Context mContext;
    private PackageEntry[] mPackages;

    public PackageRecyclerAdapter(Context context, PackageEntry[] packages) {
        mContext = context;
        mPackages = packages;
    }

    @Override
    public PackagesRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.packages_row, parent, false);
        return new PackagesRecyclerViewHolder(view, null);
    }

    @Override
    public void onBindViewHolder(PackagesRecyclerViewHolder holder, int position) {
        PackageEntry item = mPackages[position];
        if (item != null) {
            holder.getPackageName().setText(item.getLabel(mContext));
            holder.getPackageIcon().setImageDrawable(item.getIcon(mContext));
            holder.itemView.setTag(item);
        }
    }

    @Override
    public int getItemCount() {
        return mPackages.length;
    }
}
