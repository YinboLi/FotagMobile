package com.example.yinboli.fotagmobile;

/**
 * Created by yinboli on 4/3/16.
 */
public class Image {
    private int iconId;
    private float rating;

    public Image(int iconId, float rating) {
        super();
        this.iconId = iconId;
        this.rating = rating;
    }

    public int getIconId() {
        return iconId;
    }
    public float getRating() {
        return rating;
    }
    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }
}
