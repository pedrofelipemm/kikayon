package pmoreira.kikayon.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import pmoreira.kikayon.R;
import pmoreira.kikayon.model.RecordInformation;


public class RecordDetailFragment extends Fragment {

    private DatabaseReference recordDetail;
    private ValueEventListener recordDetailListener;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_record_detail, container, false);

        String recordId = getArguments().getString(MainFragment.RECORD_ID);

        recordDetailListener = new ValueEventListener() {
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
            public void onCancelled(final DatabaseError databaseError) {

            }
        };

        recordDetail = FirebaseDatabase.getInstance().getReference().child(recordId);
        recordDetail.addValueEventListener(recordDetailListener);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recordDetail.removeEventListener(recordDetailListener);
    }
}
