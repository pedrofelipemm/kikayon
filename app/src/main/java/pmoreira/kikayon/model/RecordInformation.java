package pmoreira.kikayon.model;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.firebase.client.ServerValue;

public class RecordInformation extends Information {

    private String observation;
    private String login;

    @JsonProperty
    private Object lastChange;

    public RecordInformation() {
    }

    public RecordInformation(final int imageId, final String description, @Nullable final String observation, final String login) {
        super(imageId, description);
        this.observation = observation;
        this.login = login;
        lastChange = ServerValue.TIMESTAMP;
    }

    public String getObservation() {
        return observation;
    }

    public String getLogin() {
        return login;
    }

    @JsonIgnore
    public Long getLastChangeLong() {
        return (Long) lastChange;
    }
}
