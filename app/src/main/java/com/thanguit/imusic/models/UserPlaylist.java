package com.thanguit.imusic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPlaylist {
    @SerializedName("youID")
    @Expose
    private int youID;
    @SerializedName("useID")
    @Expose
    private String useID;
    @SerializedName("name")
    @Expose
    private String name;

    public int getYouID() {
        return youID;
    }

    public void setYouID(int youID) {
        this.youID = youID;
    }

    public String getUseID() {
        return useID;
    }

    public void setUseID(String useID) {
        this.useID = useID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}