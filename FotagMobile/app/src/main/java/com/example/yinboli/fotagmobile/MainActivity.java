package com.example.yinboli.fotagmobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {
    private Model model;
    private LayoutInflater layoutInflater;
    private RelativeLayout relative;
    private RatingBar topRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new Model();
        model.addObserver(this);
        relative = (RelativeLayout) findViewById(R.id.relativeLayout);

        ImageButton clearButton = (ImageButton) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setLoaded_flag(false);
            }
        });

        ImageButton loadButton = (ImageButton) findViewById(R.id.loadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setLoaded_flag(true);
            }
        });

        topRatingBar = (RatingBar) findViewById(R.id.topRatingBar);
        topRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                model.setFilter(topRatingBar.getRating());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
//            case R.id.clearButton:
//                topRatingBar.setRating(0);
//                model.setFilter(0);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class MyListAdapter extends ArrayAdapter<Image> {
        public MyListAdapter() {
            super(MainActivity.this, R.layout.one_image, model.getDisplayList());
        }

        @Override
        public View getView(int position, final View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.one_image, parent, false);
            }

            // Find the item
            final Image curItem = model.getDisplayList().get(position);

            // Fill the view
            ImageView view = (ImageView) itemView.findViewById(R.id.small_icon);
            view.setImageResource(curItem.getIconId());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.large, null);

                    ImageView largeIcon = (ImageView) container.findViewById(R.id.largeView);
                    largeIcon.setImageResource(curItem.getIconId());

                    final PopupWindow window = new PopupWindow(container, 1000, 1000, true);
                    window.showAtLocation(relative, Gravity.NO_GRAVITY, 250, 400);

                    container.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            window.dismiss();
                            return true;
                        }
                    });
                }
            });

            // Configure the rating bar
            final RatingBar itemRatingBar = (RatingBar) itemView.findViewById(R.id.item_RatingBar);
            itemRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    float newRating = itemRatingBar.getRating();
                    model.setRating(curItem.getIconId(), newRating);
                }
            });
            itemRatingBar.setRating(curItem.getRating());

            return itemView;
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        ArrayAdapter<Image> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
    }
}
