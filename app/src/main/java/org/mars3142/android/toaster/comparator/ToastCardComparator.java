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

package org.mars3142.android.toaster.comparator;

import org.mars3142.android.toaster.card.ToastCard;

import java.util.Comparator;
import java.util.Locale;

/**
 * Compares two TwoCards app names (case insensitive)
 *
 * @author mars3142
 */
public class ToastCardComparator implements Comparator<ToastCard> {

    private static final String TAG = ToastCardComparator.class.getSimpleName();

    @Override
    public int compare(ToastCard lhs, ToastCard rhs) {
        return lhs.appName.toUpperCase(Locale.getDefault()).compareToIgnoreCase(rhs.appName.toUpperCase(Locale.getDefault()));
    }
}
