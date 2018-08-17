package com.gyuhwan.android.blockchain.dataSchema;

public class Item {
    private int id;
    private String name;
    private String imageUrl;
    private String tier;

    public Item(int id, String name, String imageUrl, String tier) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.tier = tier;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTier() {
        return tier;
    }
}
