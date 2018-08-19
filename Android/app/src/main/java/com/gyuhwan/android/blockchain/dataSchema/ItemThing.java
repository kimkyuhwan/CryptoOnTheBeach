package com.gyuhwan.android.blockchain.dataSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemThing {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("picDir")
    @Expose
    private String picDir;
    @SerializedName("sellerId")
    @Expose
    private Integer sellerId;
    @SerializedName("buyerId")
    @Expose
    private Object buyerId;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("etherAddress")
    @Expose
    private Object etherAddress;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("sellerTier")
    @Expose
    private String sellerTier;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPicDir() {
        return picDir;
    }

    public void setPicDir(String picDir) {
        this.picDir = picDir;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Object getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Object buyerId) {
        this.buyerId = buyerId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Object getEtherAddress() {
        return etherAddress;
    }

    public void setEtherAddress(Object etherAddress) {
        this.etherAddress = etherAddress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSellerTier() {
        return sellerTier;
    }

    public void setSellerTier(String sellerTier) {
        this.sellerTier = sellerTier;
    }
}