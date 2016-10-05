package pmoreira.kikayon.model;

import android.support.annotation.Nullable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

@IgnoreExtraProperties
public class RecordInformation extends Information {

    public static final int STATUS_DEAD = 0;
    public static final int STATUS_LIVE = 1;

    private String observation;
    private String login;
    private int status;
    private Object lastChange;

    public RecordInformation() {
    }

    public RecordInformation(final int imageId, final String description, @Nullable final String observation, final String login, final int status) {
        super(imageId, description);
        this.observation = observation;
        this.login = login;
        this.status = status;
        lastChange = ServerValue.TIMESTAMP;
    }

    public RecordInformation(final int imageId, final String description, @Nullable final String observation, final String login, final int status, final Long lastChange) {
        this(imageId, description, observation, login, status);
        if (lastChange != null)
            this.lastChange = lastChange;
    }

    public String getObservation() {
        return observation;
    }

    public String getLogin() {
        return login;
    }

    public int getStatus() {
        return status;
    }

    public Object getLastChange() {
        return lastChange;
    }

    @Exclude
    public Long getLastChangeLong() {
        return lastChange != null ? (Long) lastChange : null;
    }
}
