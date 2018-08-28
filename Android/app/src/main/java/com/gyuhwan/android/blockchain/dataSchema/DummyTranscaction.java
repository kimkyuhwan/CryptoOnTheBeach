package com.gyuhwan.android.blockchain.dataSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DummyTranscaction {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("gas")
    @Expose
    private Integer gas;
    @SerializedName("uuid")
    @Expose
    private String uuid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Integer getGas() {
        return gas;
    }

    public void setGas(Integer gas) {
        this.gas = gas;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
