package com.gyuhwan.android.blockchain.dataSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("buyerTier")
    @Expose
    private String buyerTier;
    @SerializedName("sellerTier")
    @Expose
    private String sellerTier;
    @SerializedName("etherAddress")
    @Expose
    private String etherAddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBuyerTier() {
        return buyerTier;
    }

    public void setBuyerTier(String buyerTier) {
        this.buyerTier = buyerTier;
    }

    public String getSellerTier() {
        return sellerTier;
    }

    public void setSellerTier(String sellerTier) {
        this.sellerTier = sellerTier;
    }

    public String getEtherAddress() {
        return etherAddress;
    }

    public void setEtherAddress(String etherAddress) {
        this.etherAddress = etherAddress;
    }

}