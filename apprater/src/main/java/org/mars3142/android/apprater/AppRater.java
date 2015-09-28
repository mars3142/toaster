package org.mars3142.android.apprater;

import android.content.Context;

public class AppRater {

    public static AppRater mInstance;

    private AppRater() {

    }

    public synchronized static AppRater with(Context context) {
        if (mInstance == null) {
            mInstance = new AppRater();
        }

        return mInstance;
    }
}
