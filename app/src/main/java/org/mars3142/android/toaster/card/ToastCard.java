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

package org.mars3142.android.toaster.card;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.helper.PackageHelper;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * @author mars3142
 */
public class ToastCard extends Card {

    private final static String TAG = ToastCard.class.getSimpleName();

    public String message;
    public String appName;
    public String packageName;
    public String timestamp;
    public Drawable packageIcon;

    private TextView messageTextView;
    private TextView packageNameTextView;
    private ImageView packageIconView;
    private RelativeLayout cardBackgroundView;
    private Resources resources;

    public ToastCard(Context context) {
        super(context, R.layout.toaster_card);

        resources = getContext().getResources();
    }

    public void loadData() {
        if (packageName != null && packageName.length() != 0) {
            appName = PackageHelper.getAppName(super.getContext(), packageName);
            appName = (appName == null) ? packageName : appName;
            packageIcon = PackageHelper.getIconFromPackageName(super.getContext(), packageName);
        }
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        if (cardBackgroundView == null) {
            cardBackgroundView = (RelativeLayout) parent.findViewById(R.id.backgroundColor);
        }

        if (messageTextView == null) {
            messageTextView = (TextView) parent.findViewById(R.id.message);
        }

        if (packageNameTextView == null) {
            packageNameTextView = (TextView) parent.findViewById(R.id.packageName);
        }

        if (packageIconView == null) {
            packageIconView = (ImageView) parent.findViewById(R.id.packageIcon);
        }

        if (cardBackgroundView != null) {
            int color = getContext().getResources().getColor(R.color.color_card);
            if (packageIcon != null) {
                Palette palette = Palette.generate(PackageHelper.drawableToBitmap(packageIcon));
                color = palette.getMutedColor(resources.getColor(R.color.color_card));
            }
            cardBackgroundView.setBackgroundColor(color);
        }

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
