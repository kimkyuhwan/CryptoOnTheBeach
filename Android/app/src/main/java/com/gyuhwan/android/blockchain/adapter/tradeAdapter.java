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
import com.gyuhwan.android.blockchain.viewHolder.TradeHolder;

import java.util.ArrayList;

public class TradeAdapter extends RecyclerView.Adapter<TradeHolder> {

    Context context;
    ArrayList<ItemThing> itemList;


    public TradeAdapter(Context context) {
        this.context = context;
        itemList=new ArrayList<ItemThing>() ;
    }
    public void clear(){
        itemList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TradeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.trade_holder,parent,false);
        TradeHolder holder=new TradeHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TradeHolder holder, int position) {
        ItemThing posItem = itemList.get(position);
        holder.setData(posItem.getTitle(),posItem.getStatus());
        holder.itemView.setOnClickListener((View v) -> {
            Intent it=new Intent(context, ItemDetailActivity.class);
            it.putExtra("item_thing",posItem.getId());
            context.startActivity(it);
        });
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
