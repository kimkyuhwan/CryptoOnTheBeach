package com.gyuhwan.android.blockchain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.gyuhwan.android.blockchain.MApplication;
import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.activity.MainActivity;
import com.gyuhwan.android.blockchain.dataSchema.ItemSearchResult;
import com.gyuhwan.android.blockchain.util.SharedPreferenceBase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {


    String category[] = {"스마트폰", "노트북", "가전제품"};
    String categoryProduct[][] = new String[][]{{"SAMSUNG", "IPHONE", "LG"}, {"MAC", "SAMSUNG", "LG", "DELL", "HP"}, {"세탁기", "냉장고", "청소기"}};
    @BindView(R.id.searchBar)
    EditText searchBar;
    @BindView(R.id.searchBtn)
    ImageButton searchBtn;
    @BindView(R.id.categoryPhone)
    ImageButton categoryPhone;
    @BindView(R.id.categoryLaptop)
    ImageButton categoryLaptop;
    @BindView(R.id.categoryElectronicProduct)
    ImageButton categoryElectronicProduct;
    @BindView(R.id.categoryLarge)
    TextView categoryLarge;
    @BindView(R.id.categoryList)
    ListView categoryList;
    Unbinder unbinder;

    int current_idx=0;

    ArrayAdapter<String> adapter;


    void setAdapter(int idx){
        ArrayList<String> list=new ArrayList<String>();
        for(int i=0;i<categoryProduct[idx].length;i++){
            list.add(categoryProduct[idx][i]);
        }
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchByCategory(i);
            }
        });
        categoryList.setAdapter(adapter);
    }

    public void searchByCategory(int pos){
        MApplication.getInstance().getApiService().searchByCategory(categoryProduct[current_idx][pos]).
                enqueue(new Callback<ItemSearchResult>() {
                    @Override
                    public void onResponse(Call<ItemSearchResult> call, Response<ItemSearchResult> response) {
                        if(response.isSuccessful()){
                            SharedPreferenceBase.putItemListSharedPreference("itemlist",response.body());
                            Log.d("DEBUGYU","SUCCESS!!") ;
                            ((MainActivity)getActivity()).onFragmentChanage("itemlist");
                        }
                        else{
                            Log.d("DEBUGYU","DONE!!") ;

                        }
                    }

                    @Override
                    public void onFailure(Call<ItemSearchResult> call, Throwable t) {
                        Log.d("DEBUGYU","FAIL!!") ;
                        Log.w("DEBUGYU","LOG : "+t.getLocalizedMessage());

                    }
                });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);

        unbinder = ButterKnife.bind(this, rootView);
        categoryLarge.setText(category[0]);
        setAdapter(0);

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

    @OnClick({R.id.searchBtn, R.id.categoryPhone, R.id.categoryLaptop, R.id.categoryElectronicProduct})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.searchBtn:
                ((MainActivity)getActivity()).searchByTitle(searchBar.getText().toString());
                break;
            case R.id.categoryPhone:
                categoryLarge.setText(category[0]);
                current_idx=0;
                setAdapter(current_idx);
                break;
            case R.id.categoryLaptop:
                categoryLarge.setText(category[1]);
                current_idx=1;
                setAdapter(current_idx);
                break;
            case R.id.categoryElectronicProduct:
                categoryLarge.setText(category[2]);
                current_idx=2;
                setAdapter(current_idx);
                break;
        }
    }
}
