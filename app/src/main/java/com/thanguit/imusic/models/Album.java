package com.thanguit.imusic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Album {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("singer")
    @Expose
    private String singer;
    @SerializedName("img")
    @Expose
    private String img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
