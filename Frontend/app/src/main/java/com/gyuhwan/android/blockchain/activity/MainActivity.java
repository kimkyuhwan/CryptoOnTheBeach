package com.gyuhwan.android.blockchain.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.fragment.CategoryFragment;
import com.gyuhwan.android.blockchain.fragment.HomeFragment;
import com.gyuhwan.android.blockchain.fragment.MyPageFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        homeFragment=new HomeFragment();
        categoryFragment=new CategoryFragment();
        myPageFragment=new MyPageFragment();
        if(savedInstanceState==null){
            onFragmentChanage("home");
        }
    }

    void onFragmentChanage(String fragmentName){
        if(fragmentName==currentFragmentName) return;
        currentFragmentName=fragmentName;
        if(fragmentName=="home"){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        }
        else if(fragmentName=="category"){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, categoryFragment).commit();
        }
        else if(fragmentName=="mypage"){
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
                break;
            case R.id.btnMyPage:
                onFragmentChanage("mypage");
                break;
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
}