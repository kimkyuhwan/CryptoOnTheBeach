package com.gyuhwan.android.blockchain.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.gyuhwan.android.blockchain.MApplication;
import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.dataSchema.EthereumAccount;
import com.gyuhwan.android.blockchain.dataSchema.Keystore;
import com.gyuhwan.android.blockchain.fragment.CategoryFragment;
import com.gyuhwan.android.blockchain.fragment.HomeFragment;
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
                switch (idx) {
                    case 0:
                        onFragmentChanage("home");
                        break;
                    case 1:
                        onFragmentChanage("category");
                        break;
                    case 2:
                        onFragmentChanage("mypage");
                        getEthereumAccount();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    void onFragmentChanage(String fragmentName) {
        if (fragmentName == currentFragmentName) return;
        currentFragmentName = fragmentName;
        if (fragmentName == "home") {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        } else if (fragmentName == "category") {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, categoryFragment).commit();
        } else if (fragmentName == "mypage") {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myPageFragment).commit();
        }
    }
/*
    @OnClick({R.id.btnHome, R.id.btnCategory, R.id.btnMyPage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnHome:
                break;
            case R.id.btnCategory:
                onFragmentChanage("category");
                addUser();
                break;
            case R.id.btnMyPage:

                //getCategory("Samsung");
                break;
        }
    }*/


    public void getEthereumAccount() {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("password", "world")
                .build();
        Log.w("DEBUGYU", requestBody.toString());
        MApplication.getInstance().getChainService().getEthereumAccount(requestBody)
                .enqueue(new Callback<EthereumAccount>() {
                    @Override
                    public void onResponse(Call<EthereumAccount> call, Response<EthereumAccount> response) {
                        if (response.isSuccessful()) {
                            EthereumAccount temp = response.body();
                            Keystore beSavedKeyStore = temp.getData().getKeystore();
                            Log.d("DEBUGYU", beSavedKeyStore.getAddress());
                            SharedPreferenceBase.putKeyStoreSharedPreference("keystore", beSavedKeyStore);

                        } else {

                            Log.w("DEBUGYU", "IN : " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<EthereumAccount> call, Throwable t) {
                        Log.w("DEBUGYU", "FAIL : " + t.getMessage());
                        Log.w("DEBUGYU", "URL : " + call.request().url().toString());

                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void getCategory(String value) {
        MApplication.getInstance().getApiService().searchByCategory(value)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String value = response.body().string();
                                Log.d("DEBUGYU", value);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

/*
    void initManager(){
        manager=SimpleStroageTranscationManager.getInstance();
    }


    @OnClick({R.id.btnsetValue, R.id.btngetValue})
    public void onViewClicked(View view){
        switch (view.getId()) {
            case R.id.btnsetValue:
                manager.setContractValueExecute(editValue.getText().toString());
                break;
            case R.id.btngetValue:
                manager.getContractValueAndSetTextView(txValue);
                break;
        }
    }*/