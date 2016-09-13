package pmoreira.kikayon.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import pmoreira.kikayon.MainFragment;
import pmoreira.kikayon.R;
import pmoreira.kikayon.view.fragment.RecordDetailFragment;
import pmoreira.kikayon.view.fragment.RegisterRecordFragment;

public class FragmentUtils {

    public static final int MAIN = 0;
    public static final int REGISTER_RECORD = 1;
    public static final int DETAIL_FRAGMENT = 2;

    public static Fragment selectFragment(final FragmentManager fragmentManager, final int position) {
        Fragment fragment = getFragment(position);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container_content, fragment);
        if (!(fragment instanceof MainFragment)) transaction.addToBackStack(null);
        transaction.commit();

        return fragment;
    }

    private static Fragment getFragment(final int position) {
        switch (position) {
            case MAIN: return new MainFragment();
            case REGISTER_RECORD: return new RegisterRecordFragment();
            case DETAIL_FRAGMENT: return new RecordDetailFragment();
            default: return new MainFragment();
        }
    }
}
