package com.gyuhwan.android.blockchain.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.activity.ItemDetailActivity;
import com.gyuhwan.android.blockchain.dataSchema.Item;
import com.gyuhwan.android.blockchain.dataSchema.ItemThing;
import com.gyuhwan.android.blockchain.viewHolder.ItemHolder;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

    Context context;
    ArrayList<ItemThing> itemList;


    public ItemAdapter(Context context) {
        this.context = context;
        itemList=new ArrayList<ItemThing>() ;
    }
    public void clear(){
        itemList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_holder,parent,false);
        ItemHolder holder=new ItemHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        ItemThing posItem = itemList.get(position);
        String price = String.format("%,d",posItem.getPrice());
        holder.setData(posItem.getTitle(),price,posItem.getPicDir(),posItem.getSellerTier());
        holder.itemView.setOnClickListener((View v) -> {
            Intent it=new Intent(context, ItemDetailActivity.class);
            it.putExtra("item_thing",posItem.getId());
            context.startActivity(it);
        });
        if(posItem.getStatus()==1){
            holder.itemView.setBackgroundResource(R.drawable.textlines);
        }
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
