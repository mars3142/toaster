package org.mars3142.android.toaster.comparator;

import org.mars3142.android.toaster.card.ToastCard;

import java.util.Comparator;

public class ToastCardComparator implements Comparator<ToastCard> {

    private final static String TAG = ToastCardComparator.class.getSimpleName();

    @Override
    public int compare(ToastCard lhs, ToastCard rhs) {
        return lhs.appName.compareTo(rhs.appName);
    }
}
