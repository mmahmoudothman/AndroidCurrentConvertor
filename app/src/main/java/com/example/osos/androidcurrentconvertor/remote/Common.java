package com.example.osos.androidcurrentconvertor.remote;

import com.example.osos.androidcurrentconvertor.remote.CoinService;
import com.example.osos.androidcurrentconvertor.remote.RetofitVlient;

public class Common {

    private static final String API_URL="https://min-api.cryptocompare.com";

    public static CoinService getCoinService(){
        return RetofitVlient.getClient(API_URL).create(CoinService.class);
    }

}
