package com.gyuhwan.android.blockchain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyuhwan.android.blockchain.MApplication;
import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.adapter.tradeAdapter;
import com.gyuhwan.android.blockchain.dataSchema.ItemSearchResult;
import com.gyuhwan.android.blockchain.dataSchema.ItemThing;
import com.gyuhwan.android.blockchain.dataSchema.UserData;
import com.gyuhwan.android.blockchain.util.SharedPreferenceBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageFragment extends Fragment {

    Unbinder unbinder;
    tradeAdapter adapter;
    @BindView(R.id.userId)
    TextView userId;
    @BindView(R.id.valueCoin)
    TextView valueCoin;
    @BindView(R.id.valueAccount)
    TextView valueAccount;
    @BindView(R.id.sellerTier)
    ImageView sellerTier;
    @BindView(R.id.sellerLayout)
    LinearLayout sellerLayout;
    @BindView(R.id.buyerTier)
    ImageView buyerTier;
    @BindView(R.id.buyerLayout)
    LinearLayout buyerLayout;
    @BindView(R.id.mypageTab)
    LinearLayout mypageTab;
    @BindView(R.id.tradeList)
    RecyclerView tradeList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mypage, container, false);

        unbinder = ButterKnife.bind(this, rootView);
        UserData userData = SharedPreferenceBase.getUserDataSharedPreference("user");
        userId.setText(userData.getUsername());
        //       valueCoin.setText(userData.get);
        valueAccount.setText(userData.getEtherAddress());
        sellerTier.setBackgroundResource(getTierImageResource(userData.getSellerTier()));
        buyerTier.setBackgroundResource(getTierImageResource(userData.getBuyerTier()));
        getSellItemList();
        return rootView;
    }

    private int getTierImageResource(String _tier) {
        if (_tier.equals("1")) {
            return R.drawable.bronze;
        } else if (_tier.equals("2")) {
            return (R.drawable.silver);
        } else if (_tier.equals("3")) {
            return (R.drawable.gold);
        } else if (_tier.equals("4")) {
            return (R.drawable.platinum);
        } else if (_tier.equals("5")) {
            return (R.drawable.dia);
        }
        return -1;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    void setAdapter(List<ItemThing> result) {
        adapter = new tradeAdapter(getActivity());
        if(result==null )return;
        for (int i = 0; i < result.size(); i++) {
            ItemThing temp = result.get(i);
            Log.w("DEBUGYU", "ID : " + temp.getId());
            adapter.addItem(temp);
        }
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        if (tradeList != null) {
            tradeList.setLayoutManager(llm);
            tradeList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    void getSellItemList() {
        int user_id=SharedPreferenceBase.getUserDataSharedPreference("user").getId();
        Log.d("DEBUGYU","user_id : "+user_id);
        MApplication.getInstance().getApiService().searchBySellerId(user_id).enqueue(new Callback<ItemSearchResult>() {
            @Override
            public void onResponse(Call<ItemSearchResult> call, Response<ItemSearchResult> response) {
                if(response.isSuccessful()){
                    Log.w("DEBUGYU_AAA",call.request().url().toString());
                    setAdapter(response.body().getResult());
                }
                else{
                    Log.d("DEBUGYU","DONE!!") ;

                }
            }

            @Override
            public void onFailure(Call<ItemSearchResult> call, Throwable t) {

            }
        });
        /*
        ItemThing dummy[] = new ItemThing[3];
        dummy[0] = new ItemThing();
        dummy[1] = new ItemThing();
        dummy[2] = new ItemThing();
        dummy[0].setStatus(0);
        dummy[0].setTitle("아이폰7 128GB");
        dummy[1].setStatus(1);
        dummy[1].setTitle("아이폰6 32GB");
        dummy[2].setStatus(1);
        dummy[2].setTitle("갤럭시 S7 32GB");
        ArrayList<ItemThing> list = new ArrayList<ItemThing>();
        for (int i = 0; i < 3; i++)
            list.add(dummy[i]);
        setAdapter(list);*/
    }

    void getBuyItemList() {
        int user_id=SharedPreferenceBase.getUserDataSharedPreference("user").getId();
        MApplication.getInstance().getApiService().searchByBuyerId(user_id).enqueue(new Callback<ItemSearchResult>() {
            @Override
            public void onResponse(Call<ItemSearchResult> call, Response<ItemSearchResult> response) {
                if(response.isSuccessful()){
                    setAdapter(response.body().getResult());
                }
                else{
                    Log.d("DEBUGYU","DONE!!") ;

                }
            }

            @Override
            public void onFailure(Call<ItemSearchResult> call, Throwable t) {

            }
        });
        /*
        ItemThing dummy[] = new ItemThing[3];
        dummy[0] = new ItemThing();
        dummy[1] = new ItemThing();
        dummy[2] = new ItemThing();

        dummy[0].setStatus(1);
        dummy[0].setTitle("아이폰7 128GB");

        dummy[1].setStatus(1);
        dummy[1].setTitle("아이폰6 32GB");

        dummy[2].setStatus(1);
        dummy[2].setTitle("갤럭시 S7 32GB");
        ArrayList<ItemThing> list = new ArrayList<ItemThing>();
        for (int i = 0; i < 3; i++)
            list.add(dummy[i]);
        setAdapter(list);*/
    }

    @OnClick({R.id.sellerLayout, R.id.buyerLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sellerLayout:
                getSellItemList();
                sellerLayout.setBackgroundResource(R.drawable.ing_textlines);
                buyerLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
                break;
            case R.id.buyerLayout:
                getBuyItemList();
                buyerLayout.setBackgroundResource(R.drawable.ing_textlines);
                sellerLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
                break;
        }
    }
}
