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

package org.mars3142.android.toaster.activity;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * @author mars3142
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Activity activity = null;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        activity = getActivity();
    }

    public void testActivity() {
        assertTrue(activity instanceof MainActivity);
    }
}
