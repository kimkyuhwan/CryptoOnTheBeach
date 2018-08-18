package com.gyuhwan.android.blockchain.network;

import com.gyuhwan.android.blockchain.dataSchema.EthereumAccount;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ChainService {
    public static final String URL = "http://172.20.10.14:9000/";
    public static final String subURL = "/blockchain-api/";

    @POST(subURL+"get-ethereum-account/")
    Call<EthereumAccount> getEthereumAccount(@Body RequestBody body);
    //title, category, seller_id, uuid, file

    @GET(subURL+"balances")
    Call<ResponseBody> searchByBuyerId(@Query("address") String address);
}
