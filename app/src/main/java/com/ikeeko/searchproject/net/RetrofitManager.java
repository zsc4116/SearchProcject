package com.ikeeko.searchproject.net;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by ZSC on 2020-06-23.
 */
public class RetrofitManager {

    private static RetrofitManager INSTANCE = new RetrofitManager();

    private RetrofitManager() {

    }

    public static RetrofitManager getInstance() {
        return INSTANCE;
    }

    public WandroidServices getServices() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl("https://www.wanandroid.com")
                .build();
        return retrofit.create(WandroidServices.class);
    }
}
