package com.gyuhwan.android.blockchain.dataSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kdfparams {
    @SerializedName("dklen")
    @Expose
    private Integer dklen;
    @SerializedName("salt")
    @Expose
    private String salt;
    @SerializedName("n")
    @Expose
    private Integer n;
    @SerializedName("r")
    @Expose
    private Integer r;
    @SerializedName("p")
    @Expose
    private Integer p;

    public Integer getDklen() {
        return dklen;
    }

    public void setDklen(Integer dklen) {
        this.dklen = dklen;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Integer getR() {
        return r;
    }

    public void setR(Integer r) {
        this.r = r;
    }

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

}
