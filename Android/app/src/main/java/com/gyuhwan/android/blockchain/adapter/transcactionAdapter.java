package com.gyuhwan.android.blockchain.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.dataSchema.UserData;
import com.gyuhwan.android.blockchain.dataSchema.transaction;
import com.gyuhwan.android.blockchain.viewHolder.sellerHolder;
import com.gyuhwan.android.blockchain.viewHolder.transactionHolder;

import java.util.ArrayList;

public class transcactionAdapter extends RecyclerView.Adapter<transactionHolder> {

    Context context;
    ArrayList<transaction> userList;


    public transcactionAdapter(Context context) {
        this.context = context;
        userList=new ArrayList<transaction>() ;
    }
    public void clear(){
        userList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public transactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.transaction_holder,parent,false);
        transactionHolder holder=new transactionHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull transactionHolder holder, int position) {
        transaction posUser = userList.get(position);
        holder.setData(String.valueOf(position+1),posUser.getFrom(),posUser.getTo(),String.valueOf(posUser.getPrice()));
    }

    public void addItem(transaction thing){
        userList.add(thing);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
