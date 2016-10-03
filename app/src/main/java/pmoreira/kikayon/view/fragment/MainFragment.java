package pmoreira.kikayon.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pmoreira.kikayon.R;
import pmoreira.kikayon.adapter.CardViewFirebaseAdapter;
import pmoreira.kikayon.model.RecordInformation;
import pmoreira.kikayon.utils.Constants;
import pmoreira.kikayon.utils.FirebaseUtils;
import pmoreira.kikayon.utils.FragmentUtils;

public class MainFragment extends Fragment {

    public static final String RECORD_ID = "RECORD_ID";

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        CardViewFirebaseAdapter cardViewFirebaseAdapter = new CardViewFirebaseAdapter(
                RecordInformation.class,
                R.layout.fragment_main,
                CardViewFirebaseAdapter.ViewHolder.class,
                FirebaseUtils.getInstance().getReference(Constants.FIREBASE_RECORDS_LOCATION),
                new CardClickListener());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setAdapter(cardViewFirebaseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.new_record);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                FragmentUtils.selectFragment(getActivity().getSupportFragmentManager(), FragmentUtils.REGISTER_RECORD);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    setExitTransition(new Slide(Gravity.END));
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //todo
        // destroy recycler
        // remove listeners
    }

    private class CardClickListener implements CardViewFirebaseAdapter.OnClickListener {

        @Override
        public void onClick(final String id) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            Fragment detailFragment = FragmentUtils.selectFragment(fragmentManager, FragmentUtils.DETAIL_FRAGMENT);

            Bundle bundle = new Bundle();
            bundle.putString(RECORD_ID, id);
            detailFragment.setArguments(bundle);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                MainFragment.this.setExitTransition(new Slide(Gravity.TOP));
        }
    }
}
