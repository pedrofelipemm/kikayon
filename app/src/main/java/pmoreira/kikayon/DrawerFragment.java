package pmoreira.kikayon;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment {

    private static final String DRAWER_PREFERENCES = "DRAWER_PREFERENCES";
    private static final String KEY_USER_LEARNED_DRAWER = "USER_LEARNED_DRAWER";

    private View containerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private boolean mFromSavedInstance;
    private boolean mUserlearnedDrawer;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFromSavedInstance = savedInstanceState != null;
        mUserlearnedDrawer = getFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, false);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drawer, container, false);
    }

    public void setUp(final int fragmentId, final DrawerLayout drawerLayout, final Toolbar toolbar) {

        containerView = getActivity().findViewById(fragmentId);

        mDrawerLayout = drawerLayout;

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(final View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!mUserlearnedDrawer) {
                    mUserlearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserlearnedDrawer);
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(final View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };

//        if (!mUserlearnedDrawer && !mFromSavedInstance) {
//            mDrawerLayout.openDrawer(containerView);
//        }

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() { public void run() { mDrawerToggle.syncState(); } });
    }

    public static void saveToPreferences(final Context context, final String name, final boolean value) {
        context.getSharedPreferences(DRAWER_PREFERENCES, context.MODE_MULTI_PROCESS)
                .edit()
                .putBoolean(name, value)
                .apply();
    }

    public static boolean getFromPreferences(final Context context, final String name, boolean defaultValue) {
        return context.getSharedPreferences(DRAWER_PREFERENCES, Context.MODE_PRIVATE)
                .getBoolean(name, defaultValue);
    }
}
