package org.mars3142.android.toaster.helper;

import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;

public class StrictModeHelpter {

    private final static String TAG = StrictModeHelpter.class.getSimpleName();

    public static void setStrictMode() {
        ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
        StrictMode.setThreadPolicy(threadPolicy);
    }
}
