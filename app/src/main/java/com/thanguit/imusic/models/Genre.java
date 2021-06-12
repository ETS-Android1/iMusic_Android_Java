package com.thanguit.imusic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Genre {

    @SerializedName("idGenre")
    @Expose
    private int idGenre;
    @SerializedName("idTheme")
    @Expose
    private String idTheme;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("img")
    @Expose
    private String img;

    public int getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(int idGenre) {
        this.idGenre = idGenre;
    }

    public String getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(String idTheme) {
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
