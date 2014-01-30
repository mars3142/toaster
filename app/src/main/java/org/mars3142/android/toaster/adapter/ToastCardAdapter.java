package org.mars3142.android.toaster.adapter;

import android.content.Context;
import android.database.Cursor;

import org.mars3142.android.toaster.card.ToastCard;
import org.mars3142.android.toaster.helper.PackageHelper;
import org.mars3142.android.toaster.table.ToasterTable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardCursorAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;

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
        card.packageName = cursor.getString(cursor.getColumnIndex(ToasterTable.PACKAGE));
        card.packageIcon = PackageHelper.getIconFromPackageName(super.getContext(), card.packageName);
        card.appName = PackageHelper.getAppName(super.getContext(), card.packageName);

        card.setId(cursor.getString(cursor.getColumnIndex(ToasterTable.ID)));

        CardHeader header = new CardHeader(super.getContext());
        header.setTitle(card.timestamp);
        card.addCardHeader(header);

        return card;
    }
}
