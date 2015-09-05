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

package org.mars3142.android.toaster.card;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.helper.PackageHelper;

import java.util.HashMap;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Representation of a toast card
 *
 * @author mars3142
 */
public class ToastCard extends Card {

    public String message;
    public String appName;
    public String packageName;
    public String timestamp;
    public Drawable packageIcon;
    public Palette palette;

    private TextView mMessageTextView;
    private TextView mPackageNameTextView;
    private ImageView mPackageIconView;
    private RelativeLayout mCardBackgroundView;

    private static HashMap<String, Palette> mPalettes;

    public ToastCard(Context context) {
        super(context, R.layout.toaster_card);

        init();
    }

    public ToastCard(Context context, String packageName) {
        this(context);

        init();
        loadData(packageName);
    }

    private void init() {
        if (mPalettes == null) {
            mPalettes = new HashMap<>();
        }
    }

    private void loadData(String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            this.packageName = packageName;
            appName = PackageHelper.with(getContext()).getAppName(packageName);
            appName = (appName == null) ? packageName : appName;
            packageIcon = PackageHelper.with(getContext()).getIconFromPackageName(packageName);
            if (mPalettes.containsKey(packageName)) {
                palette = mPalettes.get(packageName);
            } else {
                if (packageIcon != null) {
                    palette = new Palette.Builder(PackageHelper.drawableToBitmap(packageIcon)).generate();
                    mPalettes.put(packageName, palette);
                }
            }
        }
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        if (mCardBackgroundView == null) {
            mCardBackgroundView = (RelativeLayout) parent.findViewById(R.id.backgroundColor);
        }

        if (mMessageTextView == null) {
            mMessageTextView = (TextView) parent.findViewById(R.id.message);
        }

        if (mPackageNameTextView == null) {
            mPackageNameTextView = (TextView) parent.findViewById(R.id.package_name);
        }

        if (mPackageIconView == null) {
            mPackageIconView = (ImageView) parent.findViewById(R.id.package_icon);
        }

        if (mCardBackgroundView != null) {
            int color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
            if (palette != null) {
                color = palette.getMutedColor(color);
            }
            mCardBackgroundView.setBackgroundColor(color);
        }

        if (mMessageTextView != null) {
            mMessageTextView.setText(message);
        }

        if (mPackageNameTextView != null) {
            mPackageNameTextView.setText(appName == null ? packageName : appName);
        }

        if (mPackageIconView != null) {
            mPackageIconView.setImageDrawable(packageIcon);
        }
    }
}
