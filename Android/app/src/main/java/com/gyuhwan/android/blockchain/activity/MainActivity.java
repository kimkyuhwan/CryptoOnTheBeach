package com.gyuhwan.android.blockchain.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.gyuhwan.android.blockchain.MApplication;
import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.dataSchema.EthereumAccount;
import com.gyuhwan.android.blockchain.dataSchema.ItemSearchResult;
import com.gyuhwan.android.blockchain.dataSchema.Keystore;
import com.gyuhwan.android.blockchain.fragment.CategoryFragment;
import com.gyuhwan.android.blockchain.fragment.HomeFragment;
import com.gyuhwan.android.blockchain.fragment.ItemListFragment;
import com.gyuhwan.android.blockchain.fragment.MyPageFragment;
import com.gyuhwan.android.blockchain.util.SharedPreferenceBase;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String currentFragmentName;

    HomeFragment homeFragment;
    CategoryFragment categoryFragment;
    MyPageFragment myPageFragment;
    ItemListFragment itemListFragment;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.mainTab)
    TabLayout mainTab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        homeFragment = new HomeFragment();
        categoryFragment = new CategoryFragment();
        myPageFragment = new MyPageFragment();
        itemListFragment=new ItemListFragment();
        setTabLayout();
        if (savedInstanceState == null) {
            onFragmentChanage("home");
        }
    }

    void setTabLayout() {
        mainTab.addTab(mainTab.newTab().setText("HOME"));
        mainTab.addTab(mainTab.newTab().setText("CATEGORY"));
        mainTab.addTab(mainTab.newTab().setText("MY"));
        mainTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int idx = tab.getPosition();
                FragmentChange(idx);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int idx = tab.getPosition();
                FragmentChange(idx);
            }
        });
    }

    public void searchByTitle(String title){
        MApplication.getInstance().getApiService().searchByTitle(title).enqueue(new Callback<ItemSearchResult>() {
            @Override
            public void onResponse(Call<ItemSearchResult> call, Response<ItemSearchResult> response) {
                if(response.isSuccessful()){
                    SharedPreferenceBase.putItemListSharedPreference("itemlist",response.body());
                    Log.d("DEBUGYU","SUCCESS!!") ;
                    onFragmentChanage("itemlist");
                }
                else{
                    Log.d("DEBUGYU","DONE!!") ;

                }
            }

            @Override
            public void onFailure(Call<ItemSearchResult> call, Throwable t) {

            }
        });
    }

    public void FragmentChange(int idx){
        switch (idx) {
            case 0:
                onFragmentChanage("home");
                break;
            case 1:
                onFragmentChanage("category");
                break;
            case 2:
                onFragmentChanage("mypage");
                // getEthereumAccount();
                break;
        }
    }

    public void onFragmentChanage(String fragmentName) {
        if (!fragmentName.equals("itemlist") && fragmentName.equals(currentFragmentName)){
            Log.w("DEBUGYU","Return!: "+fragmentName);
            return;
        }
        currentFragmentName = fragmentName;
        Log.w("DEBUGYU","Current : "+currentFragmentName);
        if (fragmentName.equals("home")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        } else if (fragmentName.equals("category")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, categoryFragment).commit();
        } else if (fragmentName.equals("mypage")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myPageFragment).commit();
        } else if(fragmentName.equals("itemlist")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, itemListFragment).commit();

        }
    }




}
