package pmoreira.kikayon.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import pmoreira.kikayon.R;
import pmoreira.kikayon.model.RecordInformation;


public class RecordDetailFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_detail, container, false);

        int recordId = getArguments().getInt(MainFragment.RECORD_ID);
        RecordInformation record = (RecordInformation) MainFragment.RECORDS.get(recordId);

        TextView loginTextView = (TextView) view.findViewById(R.id.login);
        loginTextView.setText(record.getLogin());

        TextView descriptionTextView = (TextView) view.findViewById(R.id.description);
        descriptionTextView.setText(record.getDescription());

        TextView observationTextView = (TextView) view.findViewById(R.id.observation);
        observationTextView.setText(record.getObservation());

        TextView dateTextView = (TextView) view.findViewById(R.id.date);
        dateTextView.setText(new SimpleDateFormat("dd/MM/yyyy").format(record.getDate()));

        return view;
    }
}
