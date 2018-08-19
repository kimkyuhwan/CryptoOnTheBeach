package com.gyuhwan.android.blockchain.dataSchema;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemSearchResult {

    @SerializedName("result")
    @Expose
    private List<ItemThing> result = null;

    public List<ItemThing> getResult() {
        return result;
    }

    public void setResult(List<ItemThing> result) {
        this.result = result;
    }
}
