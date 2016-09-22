package pmoreira.kikayon.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import pmoreira.kikayon.R;
import pmoreira.kikayon.model.RecordInformation;
import pmoreira.kikayon.utils.Constants;


public class RecordDetailFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_record_detail, container, false);

        String recordId = getArguments().getString(MainFragment.RECORD_ID);

        Firebase recordDetail = new Firebase(Constants.FIREBASE_RECORDS_URL).child(recordId);
        recordDetail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                RecordInformation record = dataSnapshot.getValue(RecordInformation.class);
                if (record != null) {
                    TextView loginTextView = (TextView) view.findViewById(R.id.login);
                    loginTextView.setText(record.getLogin());

                    TextView descriptionTextView = (TextView) view.findViewById(R.id.description);
                    descriptionTextView.setText(record.getDescription());

                    TextView observationTextView = (TextView) view.findViewById(R.id.observation);
                    observationTextView.setText(record.getObservation());

                    TextView dateTextView = (TextView) view.findViewById(R.id.date);
                    dateTextView.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(record.getLastChangeLong())));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return view;
    }
}
