package com.gyuhwan.android.blockchain.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.activity.BuyerSelectActivity;
import com.gyuhwan.android.blockchain.dataSchema.Buyer;
import com.gyuhwan.android.blockchain.viewHolder.SellerHolder;

import java.util.ArrayList;

public class BuyerSelectAdapter extends RecyclerView.Adapter<SellerHolder> {

    Context context;
    ArrayList<Buyer> userList;


    public BuyerSelectAdapter(Context context) {
        this.context = context;
        userList=new ArrayList<Buyer>() ;
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
        Buyer posUser = userList.get(position);
        holder.setData(posUser.getUsername(),String.valueOf(posUser.getBuyerTier()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int user_id = posUser.getBuyerId();
                ((BuyerSelectActivity)context).AcceptTrade(user_id);
            }
        });
    }

    public void addItem(Buyer thing){
        userList.add(thing);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
