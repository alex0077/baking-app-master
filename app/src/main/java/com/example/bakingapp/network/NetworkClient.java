package com.example.bakingapp.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    private static Retrofit retrofit;

   public static Retrofit getRetrofit() {

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
