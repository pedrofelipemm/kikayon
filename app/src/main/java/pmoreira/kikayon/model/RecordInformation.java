package pmoreira.kikayon.model;

import java.util.Date;

import pmoreira.kikayon.Information;

public class RecordInformation extends Information{

    private Date date;

    public RecordInformation(final int imageId, final String description, final Date date) {
        super(imageId, description);
        this.date=date;
    }

    public Date getDate() {
        return date;
    }
}
