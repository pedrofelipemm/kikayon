package pmoreira.kikayon;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pmoreira.kikayon.model.RecordInformation;

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

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.new_record);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Toast.makeText(getActivity(), "Oi ;)", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private static List<Information> getData() {
        List<Information> data = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            data.add(new RecordInformation(R.drawable.ic_account_circle_white_48dp, "Sua frase aqui!", new Date()));
        }
        return data;
    }
}
