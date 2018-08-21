package com.gyuhwan.android.blockchain.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyuhwan.android.blockchain.R;

public class TransactionHolder extends RecyclerView.ViewHolder {
    private Context context;
    private View view;
    public TextView numberView;
    public TextView bodyView;

    public TransactionHolder(View _itemView, Context _context) {
        super(_itemView);
        context = _context;
        view = _itemView;
        numberView = view.findViewById(R.id.transactionNum);
        bodyView = view.findViewById(R.id.transcactionBody);
    }

    public void setData(String _num, String _from,String _to,String _gas) {
        numberView.setText("Transaction #"+_num);
        if(_from.length()>8)
            _from=_from.substring(0,8)+"...";
        if(_to.length()>8)
            _to=_to.substring(0,8)+"...";
        String text="From "+_from+"\r\n"+"To "+_to+"\r\n"+"Gas "+_gas;
        bodyView.setText(text);
    }
}