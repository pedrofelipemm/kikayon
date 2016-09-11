package pmoreira.kikayon;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setAdapter(new CardViewAdapter(RECORDS, new CardClickListener()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.new_record);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                FragmentUtils.selectFragment(getActivity().getSupportFragmentManager(), FragmentUtils.REGISTER_RECORD);
            }
        });

        return view;
    }

    private static List<Information> getData() {
        return mock();
    }

    private static List<Information> mock() {
        List<Information> data = new ArrayList<>();
        int img = R.drawable.ic_account_circle_white_48dp;
        Calendar cal = Calendar.getInstance();
        cal.clear();

        cal.set(2015, 3, 3);
        data.add(new RecordInformation(img, "Software livre é para quem tem tempo livre", null, "carlosh", cal.getTime()));

        cal.set(2015, 7, 18);
        data.add(new RecordInformation(img, "O que a gente não faz por hora extra?", null, "dmenon", cal.getTime()));

        cal.set(2016, 1, 2);
        data.add(new RecordInformation(img, "Estava mijando pensando em você", "Falando com o dmenon durante o almoço sobre a pedra no rim", "murilop", cal.getTime()));

        cal.set(2016, 7, 31);
        data.add(new RecordInformation(img, "Sim, não só dei, como até amassou. Tive que trocar o portão.", "Quando o lmoretti perguntou se ele já havia dado ré sem dó de si", "alans", cal.getTime()));

        cal.set(2015, 8, 18);
        data.add(new RecordInformation(img, "O bom senso que é difíce", "Antes de comer absurdo no pesqueiro do rolinha", "rmariano", cal.getTime()));

        return data;
    }

    // TODO remove it
    public static final List<Information> RECORDS = new ArrayList() {{
        addAll(mock());
    }};

    private class CardClickListener implements CardViewAdapter.onClickListener {

        @Override
        public void onClick(final int id) {

            Bundle bundle = new Bundle();
            bundle.putInt(RECORD_ID, id);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            Fragment fragment = FragmentUtils.selectFragment(fragmentManager, FragmentUtils.DETAIL_FRAGMENT);
            fragment.setArguments(bundle);
        }
    }
}
