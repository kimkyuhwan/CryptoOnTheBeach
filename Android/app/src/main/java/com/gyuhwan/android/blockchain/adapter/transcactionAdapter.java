package com.gyuhwan.android.blockchain.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.dataSchema.Transaction;
import com.gyuhwan.android.blockchain.viewHolder.TransactionHolder;

import java.util.ArrayList;

public class TranscactionAdapter extends RecyclerView.Adapter<TransactionHolder> {

    Context context;
    ArrayList<Transaction> userList;


    public TranscactionAdapter(Context context) {
        this.context = context;
        userList=new ArrayList<Transaction>() ;
    }
    public void clear(){
        userList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.transaction_holder,parent,false);
        TransactionHolder holder=new TransactionHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHolder holder, int position) {
        Transaction posUser = userList.get(position);
        holder.setData(String.valueOf(position+1),posUser.getFrom(),posUser.getTo(),String.valueOf(posUser.getPrice()));
    }

    public void addItem(Transaction thing){
        userList.add(thing);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
