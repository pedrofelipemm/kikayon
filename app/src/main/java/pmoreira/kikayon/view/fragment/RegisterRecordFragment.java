package pmoreira.kikayon.view.fragment;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

import pmoreira.kikayon.R;
import pmoreira.kikayon.model.RecordInformation;

public class RegisterRecordFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register_record, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, mockLogin());

        Spinner login = (Spinner) view.findViewById(R.id.input_login);
        login.setAdapter(adapter);

        AppCompatButton newButton = (AppCompatButton) view.findViewById(R.id.btn_new_record);
        newButton.setOnClickListener(new onClickNewRecord());

        return view;
    }

    class onClickNewRecord implements View.OnClickListener {

        private static final String LOGIN_SPINNER_DEFAULT_VALUE = "Login";

        @Override
        public void onClick(final View v) {

            Spinner loginSpinner = (Spinner) getActivity().findViewById(R.id.input_login);
            EditText descriptionEditText = (EditText) getActivity().findViewById(R.id.input_description);
            EditText observationEditText = (EditText) getActivity().findViewById(R.id.input_obs);

            if (!isValid(loginSpinner, descriptionEditText, observationEditText)) {
                return;
            }

            MainFragment.RECORDS.add(0, new RecordInformation(
                    R.drawable.ic_account_circle_white_48dp,
                    descriptionEditText.getText().toString(),
                    observationEditText.getText().toString(),
                    loginSpinner.getSelectedItem().toString(),
                    new Date()));

            loginSpinner.setSelection(0);
            descriptionEditText.setText("");
            observationEditText.setText("");

            Toast.makeText(getActivity(), R.string.msg_item_add_success, Toast.LENGTH_SHORT).show();
        }

        private boolean isValid(final Spinner loginSpinner, final EditText descriptionEditText, final EditText observationEditText) {
            boolean isValid = true;

            if (LOGIN_SPINNER_DEFAULT_VALUE.equals(loginSpinner.getSelectedItem())) {
                Toast.makeText(getActivity(), R.string.msg_select_one_option, Toast.LENGTH_SHORT).show();
                loginSpinner.requestFocus();
                isValid = false;
            } else if (StringUtils.isBlank(descriptionEditText.getText())) {
                Toast.makeText(getActivity(), getString(R.string.msg_fill_field, "Phrase or thought"), Toast.LENGTH_SHORT).show();
                descriptionEditText.requestFocus();
                isValid = false;
            } else if (StringUtils.isBlank(observationEditText.getText())) {
                Toast.makeText(getActivity(), getString(R.string.msg_fill_field, "Observation"), Toast.LENGTH_SHORT).show();
                observationEditText.requestFocus();
                isValid = false;
            }

            return isValid;
        }
    }

    private String[] mockLogin() {
        return new String[]{
                "alans",
                "fmaldonado",
                "pedrov",
                "pmoreira",
                "rmariano"
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }
}
