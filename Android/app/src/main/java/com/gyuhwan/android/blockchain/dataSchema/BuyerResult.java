package com.gyuhwan.android.blockchain.dataSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BuyerResult {
    @SerializedName("result")
    @Expose
    private List<Buyer> result = null;

    public List<Buyer> getResult() {
        return result;
    }

    public void setResult(List<Buyer> result) {
        this.result = result;
    }
}
