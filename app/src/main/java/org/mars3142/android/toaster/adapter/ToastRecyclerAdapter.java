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

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.mars3142.android.toaster.card.ToastCard;
import org.mars3142.android.toaster.viewholder.PackagesRecyclerViewHolder;

import java.util.ArrayList;

/**
 * @author mars3142
 */
public class ToastRecyclerAdapter extends RecyclerView.Adapter<PackagesRecyclerViewHolder> {


    public ToastRecyclerAdapter(ArrayList<ToastCard> data) {

    }

    @Override
    public PackagesRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(PackagesRecyclerViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
