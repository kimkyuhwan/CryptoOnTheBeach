package com.gyuhwan.android.blockchain.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyuhwan.android.blockchain.MApplication;
import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.adapter.TranscactionAdapter;
import com.gyuhwan.android.blockchain.dataSchema.Buyer;
import com.gyuhwan.android.blockchain.dataSchema.BuyerResult;
import com.gyuhwan.android.blockchain.dataSchema.Code;
import com.gyuhwan.android.blockchain.dataSchema.DummyItemThing;
import com.gyuhwan.android.blockchain.dataSchema.DummyTranscaction;
import com.gyuhwan.android.blockchain.dataSchema.ItemThing;
import com.gyuhwan.android.blockchain.dataSchema.Thing;
import com.gyuhwan.android.blockchain.dataSchema.Transaction;
import com.gyuhwan.android.blockchain.dataSchema.TranscationData;
import com.gyuhwan.android.blockchain.util.SharedPreferenceBase;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    public static ItemDetailActivity activity;
    String uuid;
    boolean isSeller=false;
    int item_id=-1;
    int buyerId=-1;
    TranscactionAdapter adapter;
    @BindView(R.id.tradeBtn)
    Button tradeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        activity=this;
        ButterKnife.bind(this);
        Intent posIntent = getIntent();
        int idx = posIntent.getIntExtra("item_thing", -1);
        Log.d("DEBUGYU", String.valueOf(idx));
        getItemInfo(idx);
        //getTransaction();
    }

    void getTransaction() {
        uuid = (System.currentTimeMillis() % 2 == 0) ? "fffffff-ef26-d3aa-ffff-ffff929ffa99" : "fffffff-ef26-d3aa-ffff-ffff929ffb17";
        setAdapter(null);

        Log.d("DEBUGYU", "UUID :" + uuid);
        MApplication.getInstance().getChainService().TraceProduct(uuid).enqueue(new Callback<TranscationData>() {
            @Override
            public void onResponse(Call<TranscationData> call, Response<TranscationData> response) {

                if (response.isSuccessful()) {
                    TranscationData data = response.body();
                    if (data.getData().size() != 0) {
                    }
                    Log.d("DEBUGYU", "SUCCESS");
                }
                Log.d("DEBUGYU", "DONE");
            }

            @Override
            public void onFailure(Call<TranscationData> call, Throwable t) {
                Log.d("DEBUGYU", "FAIL");
            }
        });
    }

    void setAdapter(List<DummyTranscaction> result) {
        adapter = new TranscactionAdapter(this);

        if (result != null) {
            Log.w("DEBUGYU", "R: " + result.size());
            for (int i = 0; i < result.size(); i++) {
                DummyTranscaction temp = result.get(i);
                adapter.addItem(temp);

            }
        }
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        itemTracking.setLayoutManager(llm);
        itemTracking.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void getItemInfo(int idx) {
        MApplication.getInstance().getApiService().getThing(idx).
                enqueue(new Callback<DummyItemThing>() {
                    @Override
                    public void onResponse(Call<DummyItemThing> call, Response<DummyItemThing> response) {
                        Log.w("DEBUGYU", "SUCCESSS : " + call.request().url().toString());
                        if (response.isSuccessful()) {
                            Thing posItem = response.body().getThing();
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
                            item_id=posItem.getId();
                            buyerId=SharedPreferenceBase.getUserDataSharedPreference("user").getId();
                            int status=posItem.getStatus();
                            isSeller=(posItem.getSellerId()==buyerId);
                            if(status==1){
                                tradeBtn.setClickable(false);
                                tradeBtn.setText("거래완료");
                                tradeBtn.setBackgroundColor(Color.rgb(0x2f,0x2f,0x2f));
                            }
                            else if(isSeller){
                                tradeBtn.setText("판매하기");
                            }
                            else{
                                tradeBtn.setText("구매하기");
                            }
                            setAdapter(response.body().getTrans());

                        }
                    }

                    @Override
                    public void onFailure(Call<DummyItemThing> call, Throwable t) {
                        Log.w("DEBUGYU", "FAIL : " + call.request().url().toString());
                        Log.w("DEBUGYU", "Log : " + t.getMessage());
                    }
                });

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

    void Sell() {
        MApplication.getInstance().getApiService().listAskTrade(item_id).
                enqueue(new Callback<BuyerResult>() {
                    @Override
                    public void onResponse(Call<BuyerResult> call, Response<BuyerResult> response) {
                        if (response.isSuccessful()) {
                            BuyerResult result = response.body();
                            SharedPreferenceBase.putBuyerListSharedPreference("buyerlist", result);
                            List<Buyer> list = result.getResult();
                            Log.w("DEBUGYU`", "SUCCESS!");
                            if (list == null || list.size() == 0) {
                                Toast.makeText(getApplicationContext(), "구매 신청자가 없습니다.", Toast.LENGTH_LONG).show();
                            } else {
                                Intent it=new Intent(ItemDetailActivity.this,BuyerSelectActivity.class);
                                it.putExtra("itemId",item_id);
                                startActivity(it);
                            }
                        }
                        Log.w("DEBUGYU`", "DONE!!");

                    }

                    @Override
                    public void onFailure(Call<BuyerResult> call, Throwable t) {
                        Log.w("DEBUGYU`", "FAIL!!");

                    }
                });
    }

    @OnClick(R.id.tradeBtn)
    public void onViewClicked() {
        if(isSeller){
            Sell();
        }
        else{
            Log.d("DEBUGYU_AAA",""+item_id+", "+buyerId);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("itemId", String.valueOf(item_id))
                    .addFormDataPart("buyerId", String.valueOf(buyerId))
                    .build();
            MApplication.getInstance().getApiService().requestTrade(requestBody).
                    enqueue(new Callback<Code>() {
                        @Override
                        public void onResponse(Call<Code> call, Response<Code> response) {
                            if(response.isSuccessful()){
                                String result=response.body().getCode();
                                Log.w("DEBUGYU`","SUCCESS"+response.body().getCode());
                                if(result.equals("DUPLICATE")){
                                    Toast.makeText(getApplicationContext(),"이미 구매신청 한 거래입니다",Toast.LENGTH_LONG).show();
                                    finish();
                                }
                                else if(result.equals("OK")){
                                    Toast.makeText(getApplicationContext(),"구매 신청 되었습니다",Toast.LENGTH_LONG).show();
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
}
