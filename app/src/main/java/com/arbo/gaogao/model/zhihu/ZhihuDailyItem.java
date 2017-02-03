package com.arbo.gaogao.model.zhihu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/1/28.
 */

public class ZhihuDailyItem {
    @SerializedName("images")
    private String[] images;
    @SerializedName("type")
    private String type;
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;

    private String date;        //整个外层的日期

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
