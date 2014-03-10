package org.mars3142.android.toaster.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import org.mars3142.android.toaster.factory.WidgetViewsFactory;

public class WidgetService extends RemoteViewsService {

    private final static String TAG = WidgetService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new WidgetViewsFactory(this.getApplicationContext(), intent));
    }
}
