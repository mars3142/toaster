package org.mars3142.android.toaster.listener;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class AppInfoListener
        implements View.OnClickListener {

    private Context context;
    private String packageName;

    public AppInfoListener(Context context, String packageName) {
        this.context = context;
        this.packageName = packageName;
    }

    @Override
    public void onClick(View v) {
        try {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + packageName));
            context.startActivity(intent);
        } catch (Exception ex) {
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
            context.startActivity(intent);
        }
    }
}
