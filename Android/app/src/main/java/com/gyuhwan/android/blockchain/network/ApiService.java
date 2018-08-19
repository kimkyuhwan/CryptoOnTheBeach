package com.gyuhwan.android.blockchain.network;

import com.gyuhwan.android.blockchain.dataSchema.BestSeller;
import com.gyuhwan.android.blockchain.dataSchema.Code;
import com.gyuhwan.android.blockchain.dataSchema.ItemSearchResult;
import com.gyuhwan.android.blockchain.dataSchema.ItemThing;
import com.gyuhwan.android.blockchain.dataSchema.User;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    public static final String URL = "http://timetotogether.us-east-1.elasticbeanstalk.com/";
    @POST("addThing/")
    Call<ResponseBody> addThing(@Body RequestBody body);
    //title, category, seller_id, uuid, file

    @GET("getThing/")
    Call<ItemThing> getThing(@Query("thingId") int thing_id);
    // thingId

    @GET("search")
    Call<ItemSearchResult> searchByTitle(@Query("title") String title);

    @GET("search")
    Call<ItemSearchResult> searchByCategory(@Query("category") String category);

    @GET("search")
    Call<ItemSearchResult> searchBySellerId(@Query("seller_id") int seller_id);

    @GET("search")
    Call<ItemSearchResult> searchByBuyerId(@Query("buyer_id") int buyer_id);
    // title or category, seller_id, buyer_id

    @GET("recentThing")
    Call<ItemSearchResult> getRecentThing();

    @GET("bestTier")
    Call<BestSeller> getBestSeller();

    @POST("login/")
    Call<User> login(@Body RequestBody body);
    // username, password

    @POST("addUser/")
    Call<Code> addUser(@Body RequestBody body);
    // username, password, displayName

}