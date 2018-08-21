package com.gyuhwan.android.blockchain.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.dataSchema.ItemThing;
import com.gyuhwan.android.blockchain.dataSchema.UserData;
import com.gyuhwan.android.blockchain.viewHolder.sellerHolder;
import com.gyuhwan.android.blockchain.viewHolder.tradeHolder;

import java.util.ArrayList;

public class tradeAdapter extends RecyclerView.Adapter<tradeHolder> {

    Context context;
    ArrayList<ItemThing> itemList;


    public tradeAdapter(Context context) {
        this.context = context;
        itemList=new ArrayList<ItemThing>() ;
    }
    public void clear(){
        itemList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public tradeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.trade_holder,parent,false);
        tradeHolder holder=new tradeHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull tradeHolder holder, int position) {
        ItemThing posItem = itemList.get(position);
        holder.setData(posItem.getTitle(),posItem.getStatus());
    }

    public void addItem(ItemThing thing){
        itemList.add(thing);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
