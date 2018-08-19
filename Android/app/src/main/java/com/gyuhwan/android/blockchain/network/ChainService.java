package com.gyuhwan.android.blockchain.network;

import com.gyuhwan.android.blockchain.dataSchema.EthereumAccount;
import com.gyuhwan.android.blockchain.dataSchema.TranscationData;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ChainService {
    public static final String URL = "http://192.168.43.122:9000/";
    public static final String subURL = "/blockchain-api/";

    @POST(subURL+"get-ethereum-account/")
    Call<EthereumAccount> getEthereumAccount(@Body RequestBody body);
    //title, category, seller_id, uuid, file

    @POST(subURL+"register-item/")
    Call<ResponseBody> registerItem(@Body RequestBody body);

    @GET(subURL+"trace-product")
    Call<TranscationData> TraceProduct(@Query("sn") String uuid);
}
