package com.example.project1.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.project1.R;
import com.example.project1.adapters.ExploreDetailsAdapter;
import com.example.project1.models.ExploreCategory;

import java.util.ArrayList;

public class ExploreDetailsActivity extends AppCompatActivity {
    final ArrayList<ExploreCategory> mItems = new ArrayList<>();
    ListView mListView;
    String[] OPTION_NAMES = {"Prophets Mosque", "Uhud Mountain", "Taiba Restaurant"};
    String[] OPTION_DESCRIPTION = {"Visit and know more about Islamic places in Almadinah!", "Discover more historical places!", "Find out where to eat!"};
    Integer[] OPTIONS_PICTURES = {R.drawable.prophetsmosque, R.drawable.uhud_mount, R.drawable.taiba_rest};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_explore);
        mListView = findViewById(R.id.listview);
        ExploreDetailsAdapter exploreAdapter = new ExploreDetailsAdapter(this, OPTION_NAMES, OPTION_DESCRIPTION, OPTIONS_PICTURES);
        mListView.setAdapter(exploreAdapter);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
}
