package pmoreira.kikayon.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pmoreira.kikayon.R;
import pmoreira.kikayon.model.RecordInformation;

public class AdminTools {

    private AdminTools() {
    }

    public static void insertData() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_RECORDS_LOCATION);
        for (RecordInformation info : RECORDS) {
            database.push().setValue(new RecordInformation(
                    R.drawable.ic_account_circle_white_48dp,
                    info.getDescription(),
                    info.getObservation(),
                    info.getLogin()));

        }
    }

    public static void wipeData() {
        //TODO
    }

    private static List<RecordInformation> mock() {
        List<RecordInformation> data = new ArrayList<>();
        int img = R.drawable.ic_account_circle_white_48dp;
        Calendar cal = Calendar.getInstance();
        cal.clear();

        cal.set(2015, 3, 3);
        data.add(new RecordInformation(img, "Software livre é para quem tem tempo livre", null, "carlosh"));

        cal.set(2015, 7, 18);
        data.add(new RecordInformation(img, "O que a gente não faz por hora extra?", null, "dmenon"));

        cal.set(2016, 1, 2);
        data.add(new RecordInformation(img, "Estava mijando pensando em você", "Falando com o dmenon durante o almoço sobre a pedra no rim", "murilop"));

        cal.set(2016, 7, 31);
        data.add(new RecordInformation(img, "Sim, não só dei, como até amassou. Tive até que trocar o portão", "Quando o lmoretti perguntou se ele já havia dado ré sem dó de si", "alans"));

        cal.set(2015, 8, 18);
        data.add(new RecordInformation(img, "O bom senso que é difíce", "Antes de comer absurdo no pesqueiro do rolinha", "rmariano"));

        return data;
    }

    private static final ArrayList<RecordInformation> RECORDS = new ArrayList<RecordInformation>() {{
        addAll(mock());
    }};
}
