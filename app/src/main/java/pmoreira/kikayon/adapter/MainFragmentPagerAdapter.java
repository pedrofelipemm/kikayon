package pmoreira.kikayon.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import pmoreira.kikayon.view.fragment.MainFragment;

public class MainFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private static final int PAGE_SIZE = 2;

    private String tabTitles[] = new String[]{"Tab1", "Tab2"};

    private Context context;

    public MainFragmentPagerAdapter(final FragmentManager fm, final Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(final int position) {
        switch (position) {
            case 1: return new MainFragment();
            default: return new MainFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_SIZE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
