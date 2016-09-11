package pmoreira.kikayon.model;

import android.support.annotation.Nullable;

import java.util.Date;

import pmoreira.kikayon.Information;

public class RecordInformation extends Information {

    private String observation;
    private String login;
    private Date date;

    public RecordInformation(final int imageId, final String description, @Nullable final String observation, final String login, final Date date) {
        super(imageId, description);
        this.observation = observation;
        this.login = login;
        this.date = date;
    }

    public String getObservation() {
        return observation;
    }

    public String getLogin() {
        return login;
    }

    public Date getDate() {
        return date;
    }
}
