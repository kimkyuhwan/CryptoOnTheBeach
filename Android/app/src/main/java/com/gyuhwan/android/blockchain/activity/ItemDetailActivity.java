package com.gyuhwan.android.blockchain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyuhwan.android.blockchain.MApplication;
import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.adapter.ItemAdapter;
import com.gyuhwan.android.blockchain.adapter.transcactionAdapter;
import com.gyuhwan.android.blockchain.dataSchema.ItemThing;
import com.gyuhwan.android.blockchain.dataSchema.TranscationData;
import com.gyuhwan.android.blockchain.dataSchema.transaction;
import com.gyuhwan.android.blockchain.util.SharedPreferenceBase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetailActivity extends AppCompatActivity {

    @BindView(R.id.itemImage)
    ImageView itemImage;
    @BindView(R.id.itemName)
    TextView itemName;
    @BindView(R.id.itemPrice)
    TextView itemPrice;
    @BindView(R.id.itemTracking)
    RecyclerView itemTracking;

    String uuid;

    transcactionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);
        Intent posIntent = getIntent();
        int idx = posIntent.getIntExtra("item_thing", -1);
        Log.d("DEBUGYU", String.valueOf(idx));
        getItemInfo(idx);
        getTransaction();
    }

    void getTransaction(){
        uuid=(System.currentTimeMillis()%2==0) ? "fffffff-ef26-d3aa-ffff-ffff929ffa99": "fffffff-ef26-d3aa-ffff-ffff929ffb17";

        Log.d("DEBUGYU","UUID :"+uuid);
       MApplication.getInstance().getChainService().TraceProduct(uuid).enqueue(new Callback<TranscationData>() {
            @Override
            public void onResponse(Call<TranscationData> call, Response<TranscationData> response) {

                if(response.isSuccessful()) {
                    TranscationData data = response.body();
                    if (data.getData().size() != 0) {
                        setAdapter(data.getData());
                    }
                    Log.d("DEBUGYU","SUCCESS");
                }
                Log.d("DEBUGYU","DONE");
            }

            @Override
            public void onFailure(Call<TranscationData> call, Throwable t) {
                Log.d("DEBUGYU","FAIL");
            }
        });
    }

    void setAdapter(List<transaction> result){
        adapter=new transcactionAdapter(this );

        Log.w("DEBUGYU","R: "+ result.size());
        for(int i=0;i<result.size();i++){
            transaction temp=result.get(i);
            adapter.addItem(temp);

        }
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        itemTracking.setLayoutManager(llm);
        itemTracking.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void getItemInfo(int idx) {
        MApplication.getInstance().getApiService().getThing(idx).
                enqueue(new Callback<ItemThing>() {
                    @Override
                    public void onResponse(Call<ItemThing> call, Response<ItemThing> response) {
                        Log.w("DEBUGYU", "SUCCESSS : " + call.request().url().toString());
                        if (response.isSuccessful()) {
                            ItemThing posItem = response.body();
                            String str = posItem.getPicDir();//
                            if (str != null) {
                                Log.w("DEBUGYU", posItem.getPicDir());
                                Picasso.get()
                                        .load(str)
                                        .resize(360, 360)
                                        .centerCrop()
                                        .into(itemImage);
                            }
                            itemName.setText(posItem.getTitle());
                            String price = String.format("%,d원", posItem.getPrice());
                            itemPrice.setText(price);
                            uuid = posItem.getUuid();
                        }
                    }

                    @Override
                    public void onFailure(Call<ItemThing> call, Throwable t) {
                        Log.w("DEBUGYU", "FAIL : " + call.request().url().toString());
                        Log.w("DEBUGYU", "Log : " + t.getMessage());
                    }
                });

    }

}
