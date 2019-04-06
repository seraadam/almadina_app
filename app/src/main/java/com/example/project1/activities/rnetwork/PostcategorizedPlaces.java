package com.example.project1.activities.rnetwork;

import com.example.project1.models.Places;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PostcategorizedPlaces {
    @GET("place/read_category.php?")
    Call<List<Places>> getCategoryPlace(
            @Query("category") String c
    );

}
