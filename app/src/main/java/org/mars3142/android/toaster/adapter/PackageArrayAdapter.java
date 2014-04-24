package org.mars3142.android.toaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.mars3142.android.toaster.R;

public class PackageArrayAdapter extends ArrayAdapter<String> {

    private final static String TAG = PackageArrayAdapter.class.getSimpleName();

    private final Context mContext;
    private final String[] mPackages;

    public PackageArrayAdapter(Context context, int resource, String[] packages) {
        super(context, resource, packages);

        mContext = context;
        mPackages = packages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.filter_row, parent, false);

        TextView packageName = (TextView) rowView.findViewById(R.id.packageName);
        if (packageName != null) {
            packageName.setText(mPackages[position]);
        }

        return rowView;
    }
}
