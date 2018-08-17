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
import com.gyuhwan.android.blockchain.dataSchema.Item;
import com.gyuhwan.android.blockchain.viewHolder.ItemHolder;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

    Context context;
    ArrayList<Item> itemList;



    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_holder,parent,false);
        ItemHolder holder=new ItemHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Item posItem = itemList.get(position);
        holder.setData(posItem.getName(),posItem.getImageUrl(),posItem.getTier());
        holder.imageView.setOnClickListener((View v) -> {
            Intent it=new Intent(context, ItemDetailActivity.class);
            it.putExtra("item_id",posItem.getId());
            context.startActivity(it);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
