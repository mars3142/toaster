package org.mars3142.android.toaster.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mars3142.android.toaster.R;
import org.mars3142.android.toaster.activity.MainActivity;
import org.mars3142.android.toaster.adapter.ToastCardAdapter;
import org.mars3142.android.toaster.table.ToasterTable;

import it.gmariotti.cardslib.library.view.CardListView;

public class ToasterFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private final static String TAG = ToasterFragment.class.getSimpleName();

    public final static String PACKAGE_FILTER = "packageFilter";

    private final int DATA_LOADER_ALL = 0;
    private final int DATA_LOADER_FILTERED = 1;

    private ToastCardAdapter mAdapter;

    public ToasterFragment() {
    }

    public static ToasterFragment newInstance(String packageFilter) {
        ToasterFragment fragment = new ToasterFragment();
        Bundle args = new Bundle();
        args.putString(PACKAGE_FILTER, packageFilter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.toaster, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new ToastCardAdapter(getActivity());
        CardListView mListView = (CardListView) getActivity().findViewById(R.id.toast_card_list);
        if (mListView != null) {
            mListView.setAdapter(mAdapter);
        }

        String packageFilter = getArguments().getString(PACKAGE_FILTER);
        if (packageFilter == null || packageFilter.length() == 0) {
            getLoaderManager().restartLoader(DATA_LOADER_ALL, null, this);
        } else {
            getLoaderManager().restartLoader(DATA_LOADER_FILTERED, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case DATA_LOADER_ALL:
                return new CursorLoader(getActivity(), ToasterTable.TOASTER_URI, null, null, null, null);

            case DATA_LOADER_FILTERED:
                return new CursorLoader(getActivity(), ToasterTable.TOASTER_URI, null, ToasterTable.PACKAGE + " = ?", new String[]{getArguments().getString(PACKAGE_FILTER)}, null);

            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (getActivity() == null) {
            return;
        }

        switch (loader.getId()) {
            case DATA_LOADER_ALL:
            case DATA_LOADER_FILTERED:
                mAdapter.swapCursor(data);
                break;

            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case DATA_LOADER_ALL:
            case DATA_LOADER_FILTERED:
                mAdapter.swapCursor(null);
                break;

            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            String packageFilter = getArguments().getString(PACKAGE_FILTER);
            if (packageFilter == null) {
                packageFilter = "";
            }
            ((MainActivity) activity).onSectionAttached(packageFilter);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}
