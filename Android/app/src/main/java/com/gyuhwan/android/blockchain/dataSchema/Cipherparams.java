package com.gyuhwan.android.blockchain.dataSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cipherparams {
    @SerializedName("iv")
    @Expose
    private String iv;

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

}