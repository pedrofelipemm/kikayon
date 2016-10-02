package pmoreira.kikayon.view.fragment;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.lang3.StringUtils;

import pmoreira.kikayon.R;
import pmoreira.kikayon.model.RecordInformation;
import pmoreira.kikayon.utils.Constants;

public class RegisterRecordFragment extends Fragment {

    private Spinner loginSpinner;
    private EditText descriptionEditText;
    private EditText observationEditText;
    private EditText dateEditText;
    private AppCompatButton newRecordButton;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_record, container, false);

        initializeView(view);

        return view;
    }

    private void initializeView(final View view) {
        loginSpinner = (Spinner) view.findViewById(R.id.input_login);
        loginSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, mockLogin()));

        descriptionEditText = (EditText) view.findViewById(R.id.input_description);
        observationEditText = (EditText) view.findViewById(R.id.input_obs);
        dateEditText = (EditText) view.findViewById(R.id.input_date);

        newRecordButton = (AppCompatButton) view.findViewById(R.id.btn_new_record);
        newRecordButton.setOnClickListener(new onClickNewRecord());
    }

    private String[] mockLogin() { //TODO DELETE
        return new String[]{
                "alans",
                "fmaldonado",
                "pedrov",
                "pmoreira",
                "rmariano"
        };
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
                    loginSpinner.getSelectedItem().toString());

            FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_RECORDS_LOCATION)
                    .push()
                    .setValue(record);

            loginSpinner.setSelection(0);
            descriptionEditText.setText("");
            observationEditText.setText("");

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
