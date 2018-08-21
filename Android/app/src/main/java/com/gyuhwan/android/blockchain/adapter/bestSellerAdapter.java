package com.gyuhwan.android.blockchain.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyuhwan.android.blockchain.MApplication;
import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.activity.MainActivity;
import com.gyuhwan.android.blockchain.dataSchema.ItemSearchResult;
import com.gyuhwan.android.blockchain.dataSchema.UserData;
import com.gyuhwan.android.blockchain.fragment.ItemListFragment;
import com.gyuhwan.android.blockchain.util.SharedPreferenceBase;
import com.gyuhwan.android.blockchain.viewHolder.SellerHolder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gyuhwan.android.blockchain.MApplication.setTextBar;

public class BestSellerAdapter extends RecyclerView.Adapter<SellerHolder> {

    Context context;
    ArrayList<UserData> userList;


    public BestSellerAdapter(Context context) {
        this.context = context;
        userList=new ArrayList<UserData>() ;
    }
    public void clear(){
        userList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SellerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.seller_holder,parent,false);
        SellerHolder holder=new SellerHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SellerHolder holder, int position) {
        UserData posUser = userList.get(position);
        holder.setData(posUser.getUsername(),posUser.getSellerTier());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int user_id = posUser.getId();
                MApplication.getInstance().getApiService().searchBySellerId(user_id).enqueue(new Callback<ItemSearchResult>() {
                    @Override
                    public void onResponse(Call<ItemSearchResult> call, Response<ItemSearchResult> response) {
                        if(response.isSuccessful()){
                            SharedPreferenceBase.putItemListSharedPreference("itemlist",response.body());
                            Log.d("DEBUGYU","SUCCESS!!") ;
                            SharedPreferenceBase.putSharedPreference("searchText",setTextBar("seller_id",posUser.getUsername(),response.body().getResult().size()));
                            ((MainActivity)context).onFragmentChanage("itemlist");
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
        });
    }

    public void addItem(UserData thing){
        userList.add(thing);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
