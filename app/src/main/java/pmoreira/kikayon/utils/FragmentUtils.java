package pmoreira.kikayon.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import pmoreira.kikayon.MainFragment;
import pmoreira.kikayon.R;
import pmoreira.kikayon.view.fragment.RegisterRecordFragment;

public class FragmentUtils {

    public static void selectFragment(final FragmentManager fragmentManager, final int position) {
        Fragment fragment = getFragment(position);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(!(fragment instanceof MainFragment)) transaction.addToBackStack(null);
        transaction.replace(R.id.container_content, fragment);
        transaction.commit();
    }

    private static Fragment getFragment(final int position) {
        switch (position) {
            case 1: return new RegisterRecordFragment();
            default: return new MainFragment();
        }
    }
}
