package com.gyuhwan.android.blockchain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.activity.MainActivity;
import com.gyuhwan.android.blockchain.adapter.ItemAdapter;
import com.gyuhwan.android.blockchain.dataSchema.ItemSearchResult;
import com.gyuhwan.android.blockchain.dataSchema.ItemThing;
import com.gyuhwan.android.blockchain.util.SharedPreferenceBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ItemListFragment extends Fragment {
    @BindView(R.id.searchBar)
    EditText searchBar;
    @BindView(R.id.searchBtn)
    ImageButton searchBtn;
    @BindView(R.id.itemList)
    RecyclerView itemList;
    Unbinder unbinder;

    ItemAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void setAdapter(){
        adapter=new ItemAdapter(getActivity() );
        List<ItemThing> result=SharedPreferenceBase.getItemListSharedPreference("itemlist").getResult();
        Log.w("DEBUGYU","R: "+ result.size());
        for(int i=0;i<result.size();i++){
            ItemThing temp=result.get(i);
            Log.w("DEBUGYU","ID : "+temp.getId());
            adapter.addItem(temp);
        }
        LinearLayoutManager llm = new GridLayoutManager(getActivity(),2);
        llm.setOrientation(GridLayoutManager.VERTICAL);
        itemList.setLayoutManager(llm);
        itemList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapter();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_list, container, false);
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

    @OnClick(R.id.searchBtn)
    public void onViewClicked() {
        ((MainActivity)getActivity()).searchByTitle(searchBar.getText().toString());
        setAdapter();
    }
}