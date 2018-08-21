package com.gyuhwan.android.blockchain.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.gyuhwan.android.blockchain.MApplication;
import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.activity.ItemDetailActivity;
import com.gyuhwan.android.blockchain.adapter.BuyerSelectAdapter;
import com.gyuhwan.android.blockchain.dataSchema.Buyer;
import com.gyuhwan.android.blockchain.dataSchema.BuyerResult;
import com.gyuhwan.android.blockchain.dataSchema.Code;
import com.gyuhwan.android.blockchain.util.SharedPreferenceBase;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyerSelectActivity extends Activity {

    int item_id=0;
    RecyclerView buyerList;
    BuyerSelectAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.activity_seller_select_dialog);
        Intent it=getIntent();
        item_id= it.getIntExtra("itemId",-1);

        buyerList=(RecyclerView)findViewById(R.id.buyerList);
        setBuyerList();
    }


    void setBuyerList(){
        List<Buyer> list= SharedPreferenceBase.getBuyerListSharedPreference("buyerlist").getResult();
        setAdapter(list);
    }
    void setAdapter(List<Buyer> result) {
        adapter = new BuyerSelectAdapter(this);

        if (result != null) {
            Log.w("DEBUGYU", "R: " + result.size());
            for (int i = 0; i < result.size(); i++) {
                Buyer temp = result.get(i);
                adapter.addItem(temp);

            }
        }
        LinearLayoutManager llm = new GridLayoutManager(this, 3);
        llm.setOrientation(GridLayoutManager.VERTICAL);

        if (buyerList != null) {
            buyerList.setLayoutManager(llm);
            buyerList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    public void AcceptTrade(int buyerId){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("itemId", String.valueOf(item_id))
                .addFormDataPart("buyerId", String.valueOf(buyerId))
                .build();
        MApplication.getInstance().getApiService().acceptTrade(requestBody).enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if(response.isSuccessful()){
                    String result=response.body().getCode();
                    Log.w("DEBUGYU`","SUCCESS"+response.body().getCode());
                    if(result.equals("ERROR")){
                        Toast.makeText(getApplicationContext(),"에러입니다",Toast.LENGTH_LONG).show();
                    }
                    else if(result.equals("OK")){
                        Toast.makeText(getApplicationContext(),"거래되었습니다",Toast.LENGTH_LONG).show();
                        ItemDetailActivity.activity.finish();
                        finish();
                    }

                }
                else{
                    Log.w("DEBUGYU`","DONE "+response.body());
                }
            }

            @Override
            public void onFailure(Call<Code> call, Throwable t) {

            }
        });
    }
}
