package com.example.yinboli.fotagmobile;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by yinboli on 4/3/16.
 */
public class Model extends Observable {
    private float filter = 0;
    private boolean loaded_flag = false;

    private List<Image> imageList;
    private List<Image> displayList;

    public Model() {
        imageList = new ArrayList<Image>();
        imageList.add(new Image(R.drawable.bug, 0));
        imageList.add(new Image(R.drawable.up, 0));
        imageList.add(new Image(R.drawable.heart, 0));
        imageList.add(new Image(R.drawable.star, 0));
        imageList.add(new Image(R.drawable.help, 0));
        imageList.add(new Image(R.drawable.smile, 0));
        imageList.add(new Image(R.drawable.lightning, 0));
        imageList.add(new Image(R.drawable.ic_launcher, 0));
        imageList.add(new Image(R.drawable.fish, 0));
        imageList.add(new Image(R.drawable.down, 0));
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public List<Image> getDisplayList() {
        return displayList;
    }

    public void setDisplayList(List<Image> displayList) {
        this.displayList = displayList;
        setChanged();
        notifyObservers();
    }

    public float getFilter() {
        return filter;
    }

    public void setFilter(float filter) {
        this.filter = filter;

        displayList = new ArrayList<Image>();
        for (int i=0; i < imageList.size(); i++) {
            Image image = imageList.get(i);
            if (image.getRating() == filter)
                displayList.add(image);
        }

        setChanged();
        notifyObservers();
    }

    public boolean getLoaded_flag() {
        return loaded_flag;
    }

    public void setLoaded_flag(boolean loaded_flag_) {
        this.loaded_flag = loaded_flag_;
        if (loaded_flag_) {
            setDisplayList(imageList);
        } else {
            setDisplayList(new ArrayList<Image>());
        }
    }

    public void setRating(int iconId, float rating) {
        for (int i=0; i < imageList.size(); i++) {
            Image image = imageList.get(i);
            if (image.getIconId() == iconId)
                image.setRating(rating);
        }
    }
}
