package pmoreira.kikayon;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pmoreira.kikayon.adapter.MainFragmentPagerAdapter;


public class MainPagerFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_pager, container, false);

        setUpMainViewPager(view);
        Log.d("pedro", "FILHO DA PUTA");
        return view;
    }

    private void setUpMainViewPager(final View view) {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setAdapter(new MainFragmentPagerAdapter(getFragmentManager(), getActivity()));
        Log.d("pedro", getActivity().getClass().getSimpleName());
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

}
