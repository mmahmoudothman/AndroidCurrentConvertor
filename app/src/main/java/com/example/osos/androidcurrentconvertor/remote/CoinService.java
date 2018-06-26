package com.example.osos.androidcurrentconvertor.remote;

import com.example.osos.androidcurrentconvertor.model.Coin;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoinService {


    @GET("data/price")
    Call<Coin> calculateValue(@Query("fsym") String from ,@Query("tsyms") String to);
}
