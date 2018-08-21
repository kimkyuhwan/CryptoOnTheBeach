package com.gyuhwan.android.blockchain.viewHolder;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyuhwan.android.blockchain.R;

public class tradeHolder extends RecyclerView.ViewHolder {
    private Context context;
    private View view;
    public TextView nameView;
    public TextView stateView;
    public ConstraintLayout layout;

    public tradeHolder(View _itemView, Context _context) {
        super(_itemView);
        context = _context;
        view = _itemView;
        nameView = view.findViewById(R.id.tradeTitle);
        stateView = view.findViewById(R.id.tradeState);
        layout = view.findViewById(R.id.trade_layout);
    }

    public void setData(String _name, int state) {
        nameView.setText(_name);
        String status="거래완료";
        if(state==0){
            status="거래중";
            layout.setBackgroundResource(R.drawable.ing_textlines);
        }
        stateView.setText(status);

    }
}