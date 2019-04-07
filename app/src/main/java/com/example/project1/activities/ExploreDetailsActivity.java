package com.example.project1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.project1.R;
import com.example.project1.adapters.ExploreDetailsAdapter;
import com.example.project1.models.Places;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ExploreDetailsActivity extends AppCompatActivity {
    

    private List<Places> places;
    ListView mListView;
    ExploreDetailsAdapter exploreAdapter;
    String[] title ;
    int[] id ;
    String[] desc ;
    int[] image_name ;
    String[] lat;
    String[] lang;
    String[] start;
    String[] end;
    String[] category;

    private static final String EXPLORE_CATEGORY_KEY = "explore_category_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_explore);
        Intent intent = getIntent();
        String c = intent.getStringExtra(EXPLORE_CATEGORY_KEY);

//        id = new int[]{1, 2, 3};
//        title = new String[]{"Prophets Mosque", "Uhud Mountain", "Taiba Restaurant"};
//        desc = new String[]{"Visit and know more about Islamic places in Almadinah!", "Discover more historical places!", "Find out where to eat!"};
//        image_name = new int[]{R.drawable.prophetsmosque, R.drawable.uhud_mount, R.drawable.taiba_rest};
//        lat = new String[]{"32.456", "23.98", "22.987"};
//        lang = new String[]{"32.456", "23.98", "22.987"};
//        start = new String[]{"2019-09-02", "2019-09-02", "2019-09-02"};
//        end = new String[]{"2019-09-22", "2019-09-22", "2019-09-22"};
//        category = new String[]{"Islamic", "Historical", "Museum"
//                , "Restaurant", "Shopping", "Event"};
        places = new ArrayList<>();
        mListView = findViewById(R.id.listview);
         exploreAdapter = new ExploreDetailsAdapter(this, places);

        mListView.setAdapter(exploreAdapter);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        fillplaces(c);

    }


    public void fillplaces(String category){


        String url = "http://nomow.tech/tiba/api/place/read_category.php?category="+ category;
        Log.e("Response: " , url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray obj = response.getJSONArray("category");
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject jsonObject = obj.getJSONObject(i);
                                Places p = new Places( jsonObject.getString("Title"),
                                        jsonObject.getString("image_name") ,
                                        jsonObject.getString("lat"),
                                        jsonObject.getString("lang"),
                                        jsonObject.getString("Start"),
                                        jsonObject.getString("End"),
                                        jsonObject.getString("Description"),
                                        jsonObject.getInt("PID") ,
                                        jsonObject.getString("Category"));
                                Log.e("Response: " , p.getCategory());

                                places.add(p);
                            }

                            exploreAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // As of f605da3 the following should work
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                                Log.i("obj : ", obj.toString());
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }

                    }
                });

// Access the RequestQueue through your singleton class.

        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(jsonObjectRequest);

//        for (int i = 0; i < 2 ; i++) {
//            RetroPlace c = new RetroPlace( title[i],
//                image_name[i], lat[i], lang[i], start[i],end[i] ,desc[i], id[i], category[i]);
//            places.add(c);
//        }

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
