/*
Copyright 2014 Peter Siegmund

This file is part of Toaster.

Foobar is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Foobar is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.mars3142.android.toaster.card;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.helper.PackageHelper;

import it.gmariotti.cardslib.library.internal.Card;

public class ToastCard extends Card {

    private final static String TAG = ToastCard.class.getSimpleName();

    public String message;
    public String appName;
    public String packageName;
    public String timestamp;
    public Drawable packageIcon;

    public ToastCard(Context context) {
        super(context, R.layout.toaster_card);
    }

    public void loadData() {
        if (packageName != null && packageName != "") {
            appName = PackageHelper.getAppName(super.getContext(), packageName);
            appName = (appName == null) ? packageName : appName;
            packageIcon = PackageHelper.getIconFromPackageName(super.getContext(), packageName);
        }
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        TextView messageTextView = (TextView) parent.findViewById(R.id.message);
        TextView packageNameTextView = (TextView) parent.findViewById(R.id.packageName);
        ImageView packageIconView = (ImageView) parent.findViewById(R.id.packageIcon);

        if (messageTextView != null) {
            messageTextView.setText(message);
        }

        if (packageNameTextView != null) {
            packageNameTextView.setText(appName == null ? packageName : appName);
        }

        if (packageIconView != null) {
            packageIconView.setImageDrawable(packageIcon);
        }
    }
}
