package pmoreira.kikayon.model;

public class Information {

    private int imageId;
    private String description;

    public Information() {
    }

    public Information(final int imageId, final String description) {
        this.imageId = imageId;
        this.description = description;
    }

    public int getImageId() {
        return imageId;
    }

    public String getDescription() {
        return description;
    }

}
