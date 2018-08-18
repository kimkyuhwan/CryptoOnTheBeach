package com.gyuhwan.android.blockchain.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.activity.AddItemActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
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

    @OnClick({R.id.searchBtn, R.id.imageViewPager})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.searchBtn:
                break;
            case R.id.imageViewPager:
                Intent it=new Intent(getActivity(), AddItemActivity.class);
                startActivity(it);

                break;
        }
    }
}
