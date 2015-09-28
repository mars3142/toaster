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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.card.ToastCard;
import org.mars3142.android.toaster.viewholder.NavDrawerRecyclerViewHolder;

import java.util.ArrayList;

/**
 * @author mars3142
 */
public class NavDrawerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int TYPE_ACCOUNT = 0;
    private final static int TYPE_HEADER = 1;
    private final static int TYPE_ITEM = 2;

    private Context mContext;
    private NavDrawerRecyclerViewHolder.OnItemClickListener mListener;
    private ArrayList<ToastCard> mData;

    public NavDrawerRecyclerAdapter(Context context, ArrayList<ToastCard> data, NavDrawerRecyclerViewHolder.OnItemClickListener listener) {
        mContext = context;
        mListener = listener;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.packages_row, parent, false);
        return new NavDrawerRecyclerViewHolder(layout, mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_ACCOUNT:
                NavDrawerRecyclerViewHolder account = (NavDrawerRecyclerViewHolder) holder;
                account.mAppName.setText("ACCOUNT");
                break;

            case TYPE_HEADER:
                NavDrawerRecyclerViewHolder header = (NavDrawerRecyclerViewHolder) holder;
                header.mAppName.setText(mContext.getString(R.string.all_data));
                header.mPackageIcon.setImageDrawable(null);
                break;

            default:
                ToastCard card = mData.get(position - 2);
                NavDrawerRecyclerViewHolder item = (NavDrawerRecyclerViewHolder) holder;
                item.mPackageName = card.packageName;
                item.mAppName.setText(card.appName);
                item.mPackageIcon.setImageDrawable(card.packageIcon);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_ACCOUNT;

            case 1:
                return TYPE_HEADER;

            default:
                return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 2;
    }
}
