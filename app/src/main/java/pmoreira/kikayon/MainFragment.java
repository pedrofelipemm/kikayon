package pmoreira.kikayon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setAdapter(new CardViewAdapter(getData()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private static List<Information> getData() {
        List<Information> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new Information(R.drawable.ic_account_circle_white_48dp, "Nome" + i));
        }
        return data;
    }
}
