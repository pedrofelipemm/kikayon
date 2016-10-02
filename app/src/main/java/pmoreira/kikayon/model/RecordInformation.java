package pmoreira.kikayon.model;

import android.support.annotation.Nullable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

@IgnoreExtraProperties
public class RecordInformation extends Information {

    private String observation;
    private String login;
    private Object lastChange;

    public RecordInformation() {
    }

    public RecordInformation(final int imageId, final String description, @Nullable final String observation, final String login) {
        super(imageId, description);
        this.observation = observation;
        this.login = login;
        lastChange = ServerValue.TIMESTAMP;
    }

    public RecordInformation(final int imageId, final String description, @Nullable final String observation, final String login, final Long lastChange) {
        this(imageId, description, observation, login);
        if (lastChange != null)
            this.lastChange = lastChange;
    }

    public String getObservation() {
        return observation;
    }

    public String getLogin() {
        return login;
    }

    public Object getLastChange() {
        return lastChange;
    }

    @Exclude
    public Long getLastChangeLong() {
        return lastChange != null ? (Long) lastChange : null;
    }
}
