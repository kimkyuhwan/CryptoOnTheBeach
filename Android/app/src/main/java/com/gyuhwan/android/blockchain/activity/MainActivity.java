package com.gyuhwan.android.blockchain.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.gyuhwan.android.blockchain.MApplication;
import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.fragment.CategoryFragment;
import com.gyuhwan.android.blockchain.fragment.HomeFragment;
import com.gyuhwan.android.blockchain.fragment.MyPageFragment;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.btnHome)
    Button btnHome;
    @BindView(R.id.btnCategory)
    Button btnCategory;
    @BindView(R.id.btnMyPage)
    Button btnMyPage;

    String currentFragmentName;

    HomeFragment homeFragment;
    CategoryFragment categoryFragment;
    MyPageFragment myPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        homeFragment = new HomeFragment();
        categoryFragment = new CategoryFragment();
        myPageFragment = new MyPageFragment();
        if (savedInstanceState == null) {
            onFragmentChanage("home");
        }
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

    @OnClick({R.id.btnHome, R.id.btnCategory, R.id.btnMyPage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnHome:
                onFragmentChanage("home");
                break;
            case R.id.btnCategory:
                onFragmentChanage("category");
                addUser();
                break;
            case R.id.btnMyPage:
                onFragmentChanage("mypage");
                break;
        }
    }
    public void addUser() {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", "hello")
                .addFormDataPart("password", "world")
                .addFormDataPart("displayName","규환")
                .build();
        MApplication.getInstance().getApiService().addUser(requestBody)
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