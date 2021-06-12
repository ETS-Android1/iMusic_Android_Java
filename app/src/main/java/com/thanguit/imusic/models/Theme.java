package com.thanguit.imusic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Theme {
    @SerializedName("idTheme")
    @Expose
    private int idTheme;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("img")
    @Expose
    private String img;

    public int getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(int idTheme) {
        this.idTheme = idTheme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}