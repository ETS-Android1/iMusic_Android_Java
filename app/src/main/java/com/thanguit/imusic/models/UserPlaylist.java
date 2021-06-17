package com.thanguit.imusic.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPlaylist implements Parcelable {
    @SerializedName("youID")
    @Expose
    private int youID;
    @SerializedName("useID")
    @Expose
    private String useID;
    @SerializedName("name")
    @Expose
    private String name;

    protected UserPlaylist(Parcel in) {
        youID = in.readInt();
        useID = in.readString();
        name = in.readString();
    }

    public static final Creator<UserPlaylist> CREATOR = new Creator<UserPlaylist>() {
        @Override
        public UserPlaylist createFromParcel(Parcel in) {
            return new UserPlaylist(in);
        }

        @Override
        public UserPlaylist[] newArray(int size) {
            return new UserPlaylist[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(youID);
        dest.writeString(useID);
        dest.writeString(name);
    }
}