package com.gyuhwan.android.blockchain.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyuhwan.android.blockchain.R;
import com.squareup.picasso.Picasso;

public class sellerHolder extends RecyclerView.ViewHolder {
    private Context context;
    private View view;
    public TextView nameView;
    public ImageView tierView;

    public sellerHolder(View _itemView, Context _context) {
        super(_itemView);
        context = _context;
        view = _itemView;
        nameView = view.findViewById(R.id.sellerName);
        tierView = view.findViewById(R.id.sellerTier);
    }

    public void setData(String _name, String _tier) {
        nameView.setText(_name);
        if (_tier.equals("1")) {
            tierView.setBackgroundResource(R.drawable.circle_bronze);
        } else if (_tier.equals("2")) {
            tierView.setBackgroundResource(R.drawable.circle_silver);
        } else if (_tier.equals("3")) {
            tierView.setBackgroundResource(R.drawable.circle_gold);
        } else if (_tier.equals("4")) {
            tierView.setBackgroundResource(R.drawable.circle_platinum);
        } else if (_tier.equals("5")) {
            tierView.setBackgroundResource(R.drawable.circle_dia);
        }

    }
}