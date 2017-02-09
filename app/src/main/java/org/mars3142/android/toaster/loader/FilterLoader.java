/*
 * This file is part of Toaster
 *
 * Copyright (c) 2014, 2017 Peter Siegmund
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

package org.mars3142.android.toaster.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import org.mars3142.android.toaster.data.PackageEntry;

/**
 * @author mars3142
 */
public class FilterLoader extends AsyncTaskLoader<PackageEntry[]> {

    public static final String SELECTION = "selection";
    public static final String SELECTION_ARGS = "selection_args";

    public FilterLoader(Context context, Bundle params) {
        super(context);
    }

    @Override
    public PackageEntry[] loadInBackground() {
        return new PackageEntry[1];
    }
}
