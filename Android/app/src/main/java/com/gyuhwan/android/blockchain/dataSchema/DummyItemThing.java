package com.gyuhwan.android.blockchain.dataSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DummyItemThing {
    @SerializedName("thing")
    @Expose
    private Thing thing;
    @SerializedName("trans")
    @Expose
    private List<DummyTranscaction> trans = null;

    public Thing getThing() {
        return thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }

    public List<DummyTranscaction> getTrans() {
        return trans;
    }

    public void setTrans(List<DummyTranscaction> trans) {
        this.trans = trans;
    }
}
