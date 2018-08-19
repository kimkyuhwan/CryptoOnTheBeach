package com.gyuhwan.android.blockchain.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyuhwan.android.blockchain.R;
import com.squareup.picasso.Picasso;

public class ItemHolder extends RecyclerView.ViewHolder {
    private Context context;
    private View view;
    public TextView nameView;
    public ImageView imageView;
    public ImageView tierView;

    public ItemHolder(View _itemView, Context _context) {
        super(_itemView);
        context = _context;
        view = _itemView;
        nameView = view.findViewById(R.id.item_name);
        imageView = view.findViewById(R.id.item_image);
        tierView = view.findViewById(R.id.item_tier);
    }

    public void setData(String _name, String _imageurl, String _tier) {
        nameView.setText(_name);
        Picasso.get()
                .load(_imageurl)
                .resize(200, 160)
                .centerCrop()
                .into(imageView);
        if (_tier.equals("1")) {
            tierView.setBackgroundResource(R.drawable.bronze);
        } else if (_tier.equals("2")) {
            tierView.setBackgroundResource(R.drawable.silver);
        } else if (_tier.equals("3")) {
            tierView.setBackgroundResource(R.drawable.gold);
        } else if (_tier.equals("4")) {
            tierView.setBackgroundResource(R.drawable.platinum);
        } else if (_tier.equals("5")) {
            tierView.setBackgroundResource(R.drawable.dia);
        }

    }
}