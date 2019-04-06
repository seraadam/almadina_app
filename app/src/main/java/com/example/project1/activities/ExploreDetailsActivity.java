package com.example.project1.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project1.R;
import com.example.project1.activities.rnetwork.GetDataPlaces;
import com.example.project1.activities.rnetwork.RetrofitClientInstance;
import com.example.project1.adapters.ExploreDetailsAdapter;
import com.example.project1.models.Places;
import com.example.project1.models.Record;
import com.example.project1.models.RetroPlaceList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreDetailsActivity extends AppCompatActivity {
    

    private List<Record> places;
    ListView mListView;

    String[] title ;
    int[] id ;
    String[] desc ;
    int[] image_name ;
    String[] lat;
    String[] lang;
    String[] start;
    String[] end;
    String[] category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_explore);

        id = new int[]{1,2,3};
        title = new String[] {"Prophets Mosque", "Uhud Mountain", "Taiba Restaurant"};
        desc = new String[]{"Visit and know more about Islamic places in Almadinah!", "Discover more historical places!", "Find out where to eat!"};
        image_name = new int[] {R.drawable.prophetsmosque, R.drawable.uhud_mount, R.drawable.taiba_rest};
        lat = new String[]{"32.456","23.98","22.987"};
        lang =new String[]{"32.456","23.98","22.987"} ;
        start = new String[]{"2019-09-02","2019-09-02","2019-09-02"};
        end =  new String[]{"2019-09-22","2019-09-22","2019-09-22"};
        category = new String[]{"Islamic","Historical","Museum"
                ,"Restaurant","Shopping","Event"};


       // places = new ArrayList<>();
        //fillplaces();

        /*Create handle for the RetrofitInstance interface*/
        //GetDataPlaces service = RetrofitClientInstance.getRetrofitInstance().create(GetDataPlaces.class);
       // Call<RetroPlaceList> call = service.getAllPlaces();
        //Log.d("onResponse:",call.toString() );

        GetDataPlaces api = RetrofitClientInstance.getApiService();
        Call<RetroPlaceList> call = api.getAllPlaces();

        call.enqueue(new Callback<RetroPlaceList>() {
            @Override
            public void onResponse(Call<RetroPlaceList> call, Response<RetroPlaceList> response) {
                if (response.isSuccessful()) {

                    places = response.body().getRecords();
                    generateDataList(places);
                }else{Log.e("response is ","false");}

            }

            @Override
            public void onFailure(Call<RetroPlaceList> call, Throwable t) {
                    Log.e(call.toString(), t.toString());
            }



        });



    }

    public void generateDataList(List<Record> places){
        mListView = findViewById(R.id.listview);
        ExploreDetailsAdapter exploreAdapter = new ExploreDetailsAdapter(this, places);

        mListView.setAdapter(exploreAdapter);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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
