package pmoreira.kikayon.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import pmoreira.kikayon.R;
import pmoreira.kikayon.adapter.DrawerAdapter;
import pmoreira.kikayon.model.Information;

public class DrawerFragment extends Fragment {

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private DrawerAdapter adapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        adapter = new DrawerAdapter(getActivity(), getData());

        View view = inflater.inflate(R.layout.fragment_drawer, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.drawer_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void setUp(final DrawerLayout drawerLayout, final Toolbar toolbar) {

        mDrawerLayout = drawerLayout;

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(final View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(final View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(final View drawerView, final float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset < 0.3) {
                    toolbar.setAlpha(1 - slideOffset);
                }
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static List<Information> getData() {
        List<Information> data = new ArrayList<>();
        data.add(new Information(R.drawable.ic_account_circle_black_48dp, "My records"));
        data.add(new Information(R.drawable.ic_settings_black_48dp, "Settings"));

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser.getEmail().equals("pmoreira@ciandt.com")) {
            data.add(new Information(R.drawable.ic_settings_black_48dp, "Insert Data"));
            data.add(new Information(R.drawable.ic_settings_black_48dp, "Wipe Data"));
        }

        return data;
    }
}
