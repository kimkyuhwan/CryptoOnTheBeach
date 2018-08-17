package com.gyuhwan.android.blockchain.network;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    public static final String URL = "http://timetotogether.us-east-1.elasticbeanstalk.com/";
    @POST("addThing/")
    Call<ResponseBody> addThing(@Body RequestBody body);
    //title, category, seller_id, uuid, file

    @GET("getThing/")
    Call<ResponseBody> getThing(@Body RequestBody body);
    // thingId

    @GET("search/")
    Call<ResponseBody> search(@Body RequestBody body);
    // title or category, seller_id, buyer_id

    @POST("login/")
    Call<ResponseBody> login(@Body RequestBody body);
    // username, password

    @POST("addUser/")
    Call<ResponseBody> addUser(@Body RequestBody body);
    // username, password, displayName

    @GET("gettest/")
    Call<ResponseBody> getTest();



}