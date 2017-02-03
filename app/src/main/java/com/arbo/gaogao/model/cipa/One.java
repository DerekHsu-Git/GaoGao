package com.arbo.gaogao.model.cipa;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/2/1.
 */

public class One {

    @SerializedName("content")
    private String content;
    @SerializedName("note")
    private String note;
    @SerializedName("picture")
    private String picture;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
