package com.example.project1.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.models.ItemDetails;
import com.example.project1.models.Places;

import java.util.ArrayList;

public class ItemDetailsActivity extends AppCompatActivity {
    private static final String ITEM_DETAILS_KEY = "item_details_key";
    final ArrayList<ItemDetails> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle itemTitle = getIntent().getExtras();
        Log.e("places",itemTitle.toString());

        //setTitle(itemTitle);
        //initItems(mItems);
       // Places currentItemIndex = getItemIndexByTitle(itemTitle);

       // inflateLayout(currentItemIndex);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    private void inflateLayout(int index) {
        ImageView itemImage = findViewById(R.id.item_image);
        itemImage.setImageResource(mItems.get(index).getImageResourceId());
        TextView itemTitle = findViewById(R.id.item_title);
        itemTitle.setText(mItems.get(index).getTitle());
        TextView itemLocation = findViewById(R.id.item_location);
        itemLocation.setText(mItems.get(index).getLocation());
        TextView itemHighlight = findViewById(R.id.item_hightlights);
        String[] highlights = mItems.get(index).getHighlights();
        String highlights_text = "";

        //Loop through the value and manipulate the string list
//        for (int i = 0; i < highlights.length; i++) {
//            //Adds * to each list, and break at the end
//            highlights_text += "* " + highlights[i] + "\n\n";
//        }
        itemHighlight.setText(highlights_text);
    }


    private int getItemIndexByTitle(String title) {
        try {
            for (int i = 0; i < mItems.size(); i++) {
                ItemDetails currentItem = mItems.get(i);
                String currentTitle = currentItem.getTitle();
                if (title.equals(currentTitle)) {
                    return i;
                }
            }
        } catch (Error error) {
            //skip error checking
        }
        return -1;
    }

    private void initItems(ArrayList<ItemDetails> items) {
        items.add(new ItemDetails(getString(R.string.poi_islamic_landmarks_1_title),
                R.drawable.sights_chicago_architecture_tour,
                getString(R.string.poi_islamic_landmarks_1_location),
                Integer.parseInt(getString(R.string.poi_islamic_landmarks_1_review)),
                getResources().getStringArray(R.array.poi_islamic_landmarks_1_highlights)));

        items.add(new ItemDetails(getString(R.string.poi_historical_places_1_title),
                R.drawable.sights_millennium_park,
                getString(R.string.poi_historical_places_1_location),
                Integer.parseInt(getString(R.string.poi_historical_places_1_review)),
                getResources().getStringArray(R.array.poi_historical_places_1_highlights)));


        items.add(new ItemDetails(getString(R.string.poi_restaurants_1_title),
                R.drawable.sights_millennium_park,
                getString(R.string.poi_restaurants_1_location),
                Integer.parseInt(getString(R.string.poi_restaurants_1_review)),
                getResources().getStringArray(R.array.poi_restaurants_1_highlights)));



        items.add(new ItemDetails(getString(R.string.poi_hotels_1_title),
                R.drawable.sights_navy_pier,
                getString(R.string.poi_hotels_1_location),
                Integer.parseInt(getString(R.string.poi_hotels_1_review)),
                getResources().getStringArray(R.array.poi_restaurants_1_highlights)));
    }
}