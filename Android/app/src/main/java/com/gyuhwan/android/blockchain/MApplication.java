package com.gyuhwan.android.blockchain;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gyuhwan.android.blockchain.network.ApiService;
import com.gyuhwan.android.blockchain.network.ChainService;
import com.gyuhwan.android.blockchain.util.SharedPreferenceBase;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MApplication extends Application {
    private static MApplication appInstance;


    Retrofit retrofit;
    ApiService apiService = null;
    ChainService chainService = null;
    //싱글턴 Application & APiService 객체

    public static MApplication getInstance(){
        if(appInstance == null){
            appInstance = new MApplication();
        }
        return appInstance;
    }
    public ApiService getApiService(){
        if(apiService == null){
            OkHttpClient.Builder oktHttpClient = new OkHttpClient.Builder();
            retrofit = new Retrofit.Builder().
                    baseUrl(ApiService.URL).
                    client(oktHttpClient.build()).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    public ChainService getChainService(){
        if(chainService == null){
            Gson gson=new GsonBuilder()
                    .setLenient()
                    .create();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .build();

            OkHttpClient.Builder oktHttpClient = new OkHttpClient.Builder();
            retrofit = new Retrofit.Builder().
                    baseUrl(ChainService.URL).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory.create(gson)).

                    build();
            chainService = retrofit.create(ChainService.class);
        }
        return chainService;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        Log.w("DEBUGYU", SharedPreferenceBase.getKeyStoreSharedPreference("keystore").getAddress());
    }

}
