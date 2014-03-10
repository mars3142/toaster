package org.mars3142.android.toaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.card.ToastCard;

import java.util.ArrayList;

public class ToastArrayAdapter extends ArrayAdapter<ToastCard> {

    private final Context mContext;
    private final ArrayList<ToastCard> mCards;

    public ToastArrayAdapter(Context context, int resource, ArrayList<ToastCard> cards) {
        super(context, resource, cards);

        mContext = context;
        mCards = cards;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.navigation_drawer_row, parent, false);

        TextView packageName = (TextView) rowView.findViewById(R.id.packageName);
        if (packageName != null) {
            packageName.setText(mCards.get(position).appName);
        }

        ImageView packageIcon = (ImageView) rowView.findViewById(R.id.packageIcon);
        if (packageIcon != null) {
            packageIcon.setImageDrawable(mCards.get(position).packageIcon);
        }

        return rowView;
    }
}
