package pmoreira.kikayon.view.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pmoreira.kikayon.R;
import pmoreira.kikayon.model.RecordInformation;
import pmoreira.kikayon.utils.Constants;
import pmoreira.kikayon.utils.FirebaseUtils;
import pmoreira.kikayon.widget.TimePickerFragment;

public class RegisterRecordFragment extends Fragment {

    private Spinner loginSpinner;
    private EditText descriptionEditText;
    private EditText observationEditText;
    private RadioButton liveRadio;
    private RadioButton deadRadio;
    private EditText dateEditText;
    private AppCompatButton newRecordButton;

    private String recordId;
    private Long timeInMillis;
    private int status = RecordInformation.STATUS_LIVE;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_record, container, false);

        initializeView(view);

        if (getArguments() != null && (recordId = getArguments().getString(MainFragment.RECORD_ID)) != null)
            populateFields();

        return view;
    }

    private void populateFields() {
        FirebaseUtils.getInstance().getReference(Constants.FIREBASE_LOCATION_RECORDS).child(recordId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        RecordInformation record = dataSnapshot.getValue(RecordInformation.class);

                        SpinnerAdapter adapter = loginSpinner.getAdapter();
                        for (int i = 0; i < adapter.getCount(); i++) {
                            if (adapter.getItem(i).equals(record.getLogin())) {
                                loginSpinner.setSelection(i);
                                break;
                            }
                        }

                        descriptionEditText.setText(record.getDescription());
                        observationEditText.setText(record.getObservation());
                        dateEditText.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(record.getDateLong())));
                        liveRadio.setChecked(record.getStatus() == RecordInformation.STATUS_LIVE);
                        deadRadio.setChecked(record.getStatus() == RecordInformation.STATUS_DEAD);
                        status = record.getStatus();
                        timeInMillis = record.getDateLong();
                    }

                    @Override
                    public void onCancelled(final DatabaseError databaseError) {

                    }
                });

    }

    private void initializeView(final View view) {
        loginSpinner = (Spinner) view.findViewById(R.id.input_login);
        loginSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, getLogins()));

        descriptionEditText = (EditText) view.findViewById(R.id.input_description);
        observationEditText = (EditText) view.findViewById(R.id.input_obs);

        dateEditText = (EditText) view.findViewById(R.id.input_date);
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                TimePickerFragment timePicker = TimePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(final DatePicker view, final int year, final int month, final int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, month, dayOfMonth);

                        dateEditText.setText(new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()));
                        timeInMillis = cal.getTimeInMillis();
                    }
                });

                timePicker.show(getFragmentManager(), "TimePicker");
            }
        });

        liveRadio = (RadioButton) view.findViewById(R.id.radio_live);
        liveRadio.setChecked(true);
        liveRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                status = RecordInformation.STATUS_LIVE;
            }
        });

        deadRadio = (RadioButton) view.findViewById(R.id.radio_dead);
        deadRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                status = RecordInformation.STATUS_DEAD;
            }
        });

        newRecordButton = (AppCompatButton) view.findViewById(R.id.btn_new_record);
        newRecordButton.setOnClickListener(new onClickNewRecord());
    }

    private void resetFields() {
        loginSpinner.setSelection(0);
        descriptionEditText.setText("");
        observationEditText.setText("");
        dateEditText.setText("dd/mm/yyyy");
        liveRadio.setChecked(true);
        status = RecordInformation.STATUS_LIVE;
        timeInMillis = null;
    }

    private String[] getLogins() {
        Set<String> logins = getActivity()
                .getSharedPreferences(Constants.SHARED_PREFERENCES_DEFAULT, Context.MODE_PRIVATE)
                .getStringSet(Constants.SHARED_PREFERENCES_KEY_LOGINS, new HashSet<String>());

        List<String> sortedLogins = new ArrayList<>(logins);
        Collections.sort(sortedLogins);

        return sortedLogins.toArray(new String[sortedLogins.size()]);
    }

    @Override
    public void onResume() { //TODO DELETE
        super.onResume();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }

    @Override
    public void onPause() { //TODO DELETE
        super.onPause();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    private class onClickNewRecord implements View.OnClickListener {

        private static final String LOGIN_SPINNER_DEFAULT_VALUE = "Login";

        @Override
        public void onClick(final View v) {

            if (!isValid(loginSpinner, descriptionEditText, observationEditText)) {
                return;
            }

            RecordInformation record = new RecordInformation(
                    R.drawable.ic_account_circle_white_48dp,
                    descriptionEditText.getText().toString().trim(),
                    observationEditText.getText().toString().trim(),
                    loginSpinner.getSelectedItem().toString(),
                    status, timeInMillis);

            DatabaseReference database = FirebaseUtils.getInstance().getReference(Constants.FIREBASE_LOCATION_RECORDS);
            if (recordId != null) {
                database.child(recordId).setValue(record);
            } else {
                database.push().setValue(record);

            }

            resetFields();

            Toast.makeText(getActivity(), R.string.msg_item_add_success, Toast.LENGTH_SHORT).show();

            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }

        //TODO REFACTOR
        private boolean isValid(final Spinner loginSpinner, final EditText descriptionEditText, final EditText observationEditText) {
            boolean isValid = true;
            String msg;

            if (LOGIN_SPINNER_DEFAULT_VALUE.equals(loginSpinner.getSelectedItem())) {
                msg = getString(R.string.msg_select_one_option);
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                ((TextView) loginSpinner.getSelectedView()).setError(msg);
                loginSpinner.requestFocus();
                isValid = false;
            } else if (StringUtils.isBlank(descriptionEditText.getText())) {
                msg = getString(R.string.msg_fill_field, "Phrase or thought");
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                descriptionEditText.setError(msg);
                descriptionEditText.requestFocus();
                isValid = false;
            } else if (StringUtils.isBlank(observationEditText.getText())) {
                msg = getString(R.string.msg_fill_field, "Observation");
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                observationEditText.setError(msg);
                observationEditText.requestFocus();
                isValid = false;
            }

            return isValid;
        }
    }
}
