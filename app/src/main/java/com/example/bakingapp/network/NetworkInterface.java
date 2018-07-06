package com.example.bakingapp.network;

import com.example.bakingapp.models.Recipe;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NetworkInterface {
    @GET("59121517_baking/baking.json")
    Observable<List<Recipe>> getRecipes();

}
