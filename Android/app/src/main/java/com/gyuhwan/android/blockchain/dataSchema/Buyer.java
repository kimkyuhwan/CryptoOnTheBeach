package com.gyuhwan.android.blockchain.dataSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Buyer {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("itemId")
    @Expose
    private Integer itemId;
    @SerializedName("buyerId")
    @Expose
    private Integer buyerId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("buyerTier")
    @Expose
    private Integer buyerTier;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getBuyerTier() {
        return buyerTier;
    }

    public void setBuyerTier(Integer buyerTier) {
        this.buyerTier = buyerTier;
    }
}
