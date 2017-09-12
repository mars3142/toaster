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

package org.mars3142.android.toaster.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import org.mars3142.android.toaster.factory.WidgetViewsFactory;

import timber.log.Timber;

/**
 * @author mars3142
 */
public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Timber.d("onGetViewFactory");

        return (new WidgetViewsFactory(this.getApplicationContext()));
    }
}
