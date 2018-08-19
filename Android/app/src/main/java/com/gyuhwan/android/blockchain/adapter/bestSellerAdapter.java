package com.gyuhwan.android.blockchain.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.activity.ItemDetailActivity;
import com.gyuhwan.android.blockchain.dataSchema.ItemThing;
import com.gyuhwan.android.blockchain.dataSchema.UserData;
import com.gyuhwan.android.blockchain.viewHolder.ItemHolder;
import com.gyuhwan.android.blockchain.viewHolder.sellerHolder;

import java.util.ArrayList;

public class bestSellerAdapter extends RecyclerView.Adapter<sellerHolder> {

    Context context;
    ArrayList<UserData> userList;


    public bestSellerAdapter(Context context) {
        this.context = context;
        userList=new ArrayList<UserData>() ;
    }
    public void clear(){
        userList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public sellerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.seller_holder,parent,false);
        sellerHolder holder=new sellerHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull sellerHolder holder, int position) {
        UserData posUser = userList.get(position);
        holder.setData(posUser.getUsername(),posUser.getSellerTier());
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
