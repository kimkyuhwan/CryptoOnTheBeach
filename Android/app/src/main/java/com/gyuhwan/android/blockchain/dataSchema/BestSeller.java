package com.gyuhwan.android.blockchain.dataSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BestSeller {

    @SerializedName("result")
    @Expose
    private List<UserData> result = null;

    public List<UserData> getResult() {
        return result;
    }

    public void setResult(List<UserData> result) {
        this.result = result;
    }
}
