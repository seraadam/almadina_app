package com.example.project1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
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
import com.example.project1.models.ItemDetails;
import com.example.project1.models.Places;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ItemDetailsActivity extends AppCompatActivity {
    private static final String ITEM_DETAILS_KEY = "item_details_key";
    final ArrayList<ItemDetails> mItems = new ArrayList<>();
    ImageView itemImage;
    TextView itemTitle,itemLocation,itemHighlight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        int itemId = intent.getIntExtra(ITEM_DETAILS_KEY,0);
        Log.e("places",itemId+"");
        getplace(itemId);

         itemImage = findViewById(R.id.item_image);
         itemTitle = findViewById(R.id.item_title);
         itemLocation = findViewById(R.id.item_location);
         itemHighlight = findViewById(R.id.item_hightlights);

    }

    public void getplace(int id){


        String url = "http://nomow.tech/tiba/api/place/read_one.php?id="+ id;
        Log.e("Response: " , url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                                Places p = new Places( response.getString("Title"),
                                        response.getString("image_name") ,
                                        response.getString("lat"),
                                        response.getString("lang"),
                                        response.getString("Start"),
                                        response.getString("End"),
                                        response.getString("Description"),
                                        response.getInt("PID") ,
                                        response.getString("Category"));

                            if(response.getString("image_name") .equals(null)){
                                itemImage.setImageResource(R.drawable.ppp);
                            }
                            else{
                                // loading movie cover using Glide library
                                Log.e("image",response.getString("image_name") );
                                Picasso.with(getApplicationContext())
                                        .load(response.getString("image_name") ).into(itemImage);

                            }

                            itemTitle.setText(response.getString("Title"));
                            itemLocation.setText(response.getString("Category"));
                            itemHighlight.setText(response.getString("Description"));

                                Log.e("Response: " , p.getCategory());





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