package pmoreira.kikayon.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import pmoreira.kikayon.R;
import pmoreira.kikayon.model.RecordInformation;
import pmoreira.kikayon.utils.Constants;
import pmoreira.kikayon.utils.DrawableUtils;
import pmoreira.kikayon.utils.FirebaseUtils;
import pmoreira.kikayon.view.activity.LoginActivity;


public class RecordDetailFragment extends Fragment {

    private DatabaseReference recordDetail;
    private ValueEventListener recordDetailListener;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_record_detail, container, false);

        final String recordId = getArguments().getString(MainFragment.RECORD_ID);

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
                    dateTextView.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(record.getDateLong())));

                    final ImageView image = (ImageView) view.findViewById(R.id.image);
                    FirebaseUtils.getInstance().getReference(Constants.FIREBASE_LOCATION_IMAGES)
                            .child(record.getLogin())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    final String url = dataSnapshot.getValue(String.class);
                                    Picasso.with(view.getContext())
                                            .load(LoginActivity.decodeUrl(url) != "" ? LoginActivity.decodeUrl(url) : "imageNotLoaded")
                                            .networkPolicy(NetworkPolicy.OFFLINE)
                                            .transform(new CropCircleTransformation())
                                            .into(image, new Callback.EmptyCallback() {
                                                @Override
                                                public void onError() {
                                                    image.setImageDrawable(DrawableUtils.getDrawable(view.getContext(), R.drawable.ic_account_circle_white_48dp));
                                                }
                                            });
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {

            }
        };

        ImageView image = (ImageView) view.findViewById(R.id.image);

        recordDetail = FirebaseUtils.getInstance().getReference(Constants.FIREBASE_LOCATION_RECORDS).child(recordId);
        recordDetail.addValueEventListener(recordDetailListener);

        ImageButton editButton = (ImageButton) view.findViewById(R.id.btn_edit_record);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Bundle bundle = new Bundle();
                bundle.putString(MainFragment.RECORD_ID, recordId);

                RegisterRecordFragment fragment = new RegisterRecordFragment();
                fragment.setArguments(bundle);

                getFragmentManager().beginTransaction()
                        .replace(R.id.container_content, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        ImageButton deleteButton = (ImageButton) view.findViewById(R.id.btn_delete_record);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(RecordDetailFragment.this.getActivity())
                        .setMessage("Do you really want to delete this record??")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseUtils.getInstance().getReference(Constants.FIREBASE_LOCATION_RECORDS).child(recordId)
                                        .removeValue(new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(final DatabaseError databaseError, final DatabaseReference databaseReference) {
                                                getFragmentManager().popBackStack();
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recordDetail.removeEventListener(recordDetailListener);
    }
}
