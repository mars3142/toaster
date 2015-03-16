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

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.MenuItem;
import android.widget.PopupMenu;

import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.card.ToastCard;
import org.mars3142.android.toaster.table.ToasterTable;
import org.mars3142.android.toaster.task.AsyncDelete;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardCursorAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;

/**
 * CursorAdapter for the main view
 *
 * @author mars3142
 */
public class ToastCardAdapter extends CardCursorAdapter {

    public ToastCardAdapter(Context context) {
        super(context);
    }

    @Override
    protected Card getCardFromCursor(Cursor cursor) {
        ToastCard card = new ToastCard(super.getContext());

        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
        card.timestamp = dateFormat.format(Long.parseLong(cursor.getString(cursor.getColumnIndex(ToasterTable.TIMESTAMP))));
        card.message = cursor.getString(cursor.getColumnIndex(ToasterTable.MESSAGE));
        card.loadData(cursor.getString(cursor.getColumnIndex(ToasterTable.PACKAGE)));

        card.setId(cursor.getString(cursor.getColumnIndex(ToasterTable._ID)));

        CardHeader header = new CardHeader(super.getContext());
        header.setTitle(card.timestamp);
        header.setPopupMenu(R.menu.card_main, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard baseCard, MenuItem menuItem) {
                Intent intent;
                Context context = baseCard.getContext();
                String packageName = ((ToastCard) baseCard).packageName;

                switch (menuItem.getItemId()) {
                    case R.id.delete:
                        AsyncDelete task = new AsyncDelete(context, ToasterTable.TOASTER_URI, ToasterTable._ID + " = ?", new String[]{baseCard.getId()});
                        task.execute();
                        break;

                    case R.id.app_info:
                        try {
                            intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + packageName));
                            context.startActivity(intent);
                        } catch (Exception ex) {
                            intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                            context.startActivity(intent);
                        }
                        break;

                    case R.id.play_store:
                        try {
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
                            context.startActivity(intent);
                        } catch (ActivityNotFoundException ex) {
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName));
                            context.startActivity(intent);
                        }
                        break;
                }
            }
        });
        header.setPopupMenuPrepareListener(new CardHeader.OnPrepareCardHeaderPopupMenuListener() {
            @Override
            public boolean onPreparePopupMenu(BaseCard baseCard, PopupMenu popupMenu) {
                if (((ToastCard) baseCard).packageIcon == null) {
                    popupMenu.getMenu().removeItem(R.id.app_info);
                }
                return true;
            }
        });

        card.addCardHeader(header);

        return card;
    }
}
