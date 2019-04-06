package com.example.project1.activities.rnetwork;


import com.example.project1.models.RetroPlaceList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataPlaces {

    @GET("/place/read.php")
    Call<RetroPlaceList> getAllPlaces();
}