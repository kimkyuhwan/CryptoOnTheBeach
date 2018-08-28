package com.gyuhwan.android.blockchain.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyuhwan.android.blockchain.MApplication;
import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.activity.AddItemActivity;
import com.gyuhwan.android.blockchain.activity.MainActivity;
import com.gyuhwan.android.blockchain.adapter.ItemAdapter;
import com.gyuhwan.android.blockchain.adapter.BestSellerAdapter;
import com.gyuhwan.android.blockchain.dataSchema.BestSeller;
import com.gyuhwan.android.blockchain.dataSchema.ItemSearchResult;
import com.gyuhwan.android.blockchain.dataSchema.ItemThing;
import com.gyuhwan.android.blockchain.dataSchema.UserData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    @BindView(R.id.searchBar)
    EditText searchBar;
    @BindView(R.id.searchBtn)
    ImageButton searchBtn;
    @BindView(R.id.homeItemList)
    RecyclerView homeItemList;
    @BindView(R.id.imageViewPager)
    ImageView imageViewPager;
    @BindView(R.id.textSeller)
    TextView textSeller;
    @BindView(R.id.homeSellerList)
    RecyclerView homeSellerList;
    Unbinder unbinder;
    @BindView(R.id.addItemBtn)
    Button addItemBtn;

    ItemAdapter adapter;
    BestSellerAdapter bestSellerAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        Log.d("DEBUGYU_AAA","onCreateView");
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_SEARCH){
                    ((MainActivity)getActivity()).searchByTitle(searchBar.getText().toString());

                }
                return false;
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("DEBUGYU_AAA","onResume");
        getRecentThing();
        getBestSeller();
    }

    void setAdapter(List<ItemThing> result){
        adapter=new ItemAdapter(getActivity() );
        for(int i=0;i<result.size();i++){
            ItemThing temp=result.get(i);
            Log.w("DEBUGYU","ID : "+temp.getId());
            adapter.addItem(temp);
        }
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        if(homeItemList!=null){
            homeItemList.setLayoutManager(llm);
            homeItemList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    void setBestSellerAdapter(List<UserData> result) {
        bestSellerAdapter = new BestSellerAdapter(getActivity());
        for (int i = 0; i < result.size(); i++) {
            UserData temp = result.get(i);
            Log.w("DEBUGYU", "ID : " + temp.getId());
            bestSellerAdapter.addItem(temp);
        }
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (homeSellerList != null) {
            homeSellerList.setLayoutManager(llm);
            homeSellerList.setAdapter(bestSellerAdapter);
            bestSellerAdapter.notifyDataSetChanged();
        }
    }
    void getRecentThing(){
        MApplication.getInstance().getApiService().getRecentThing().enqueue(new Callback<ItemSearchResult>() {
            @Override
            public void onResponse(Call<ItemSearchResult> call, Response<ItemSearchResult> response) {
                if(response.isSuccessful()){
                    ItemSearchResult temp=response.body();
                    setAdapter(temp.getResult());
                }
            }

            @Override
            public void onFailure(Call<ItemSearchResult> call, Throwable t) {

            }
        });
    }

    void getBestSeller(){
        MApplication.getInstance().getApiService().getBestSeller().enqueue(new Callback<BestSeller>() {
            @Override
            public void onResponse(Call<BestSeller> call, Response<BestSeller> response) {
                if(response.isSuccessful()){
                    BestSeller temp=response.body();
                    setBestSellerAdapter(temp.getResult());
                }
            }

            @Override
            public void onFailure(Call<BestSeller> call, Throwable t) {

            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("DEBUGYU_AAA","onDestoryView");

        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.searchBtn, R.id.imageViewPager, R.id.addItemBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.searchBtn:
                ((MainActivity)getActivity()).searchByTitle(searchBar.getText().toString());
                break;
            case R.id.imageViewPager:
                break;
            case R.id.addItemBtn:
                Intent it=new Intent(getActivity(), AddItemActivity.class);
                startActivity(it);
                break;
        }
    }

  /*  @OnClick({R.id.searchBtn, R.id.imageViewPager})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.searchBtn:
                break;
            case R.id.imageViewPager:


                break;
        }
    }*/
}
