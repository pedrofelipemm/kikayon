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

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pmoreira.kikayon.R;
import pmoreira.kikayon.adapter.CardViewFirebaseAdapter;
import pmoreira.kikayon.model.RecordInformation;
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
                FirebaseDatabase.getInstance().getReference(),
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

    private static List<RecordInformation> mock() {
        List<RecordInformation> data = new ArrayList<>();
        int img = R.drawable.ic_account_circle_white_48dp;
        Calendar cal = Calendar.getInstance();
        cal.clear();

        cal.set(2015, 3, 3);
//        data.add(new RecordInformation(img, "Software livre é para quem tem tempo livre", null, "carlosh", cal.getTime()));
        data.add(new RecordInformation( img, "Software livre é para quem tem tempo livre", null, "carlosh"));

        cal.set(2015, 7, 18);
//        data.add(new RecordInformation(img, "O que a gente não faz por hora extra?", null, "dmenon", cal.getTime()));
        data.add(new RecordInformation( img, "O que a gente não faz por hora extra?", null, "dmenon"));

        cal.set(2016, 1, 2);
//        data.add(new RecordInformation(img, "Estava mijando pensando em você", "Falando com o dmenon durante o almoço sobre a pedra no rim", "murilop", cal.getTime()));
        data.add(new RecordInformation( img, "Estava mijando pensando em você", "Falando com o dmenon durante o almoço sobre a pedra no rim", "murilop"));

        cal.set(2016, 7, 31);
//        data.add(new RecordInformation(img, "Sim, não só dei, como até amassou. Tive que trocar o portão.", "Quando o lmoretti perguntou se ele já havia dado ré sem dó de si", "alans", cal.getTime()));
        data.add(new RecordInformation( img, "kra", "Quando o lmoretti perguntou se ele já havia dado ré sem dó de si", "alans"));

        cal.set(2015, 8, 18);
//        data.add(new RecordInformation(img, "O bom senso que é difíce", "Antes de comer absurdo no pesqueiro do rolinha", "rmariano", cal.getTime()));
        data.add(new RecordInformation( img, "O bom senso que é difíce", "Antes de comer absurdo no pesqueiro do rolinha", "rmariano"));

        return data;
    }

    // TODO remove it
    public static final List<RecordInformation> RECORDS = new ArrayList() {{
        addAll(mock());
    }};

    //TODO REMOVE
//    private class CardClickListener implements CardViewAdapter.onClickListener {
//
//        @Override
//        public void onClick(final int id) {
//            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//
//            Fragment detailFragment = FragmentUtils.selectFragment(fragmentManager, FragmentUtils.DETAIL_FRAGMENT);
//
//            Bundle bundle = new Bundle();
//            bundle.putInt(RECORD_ID, id);
//            detailFragment.setArguments(bundle);
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//                MainFragment.this.setExitTransition(new Slide(Gravity.TOP));
//        }
//


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
