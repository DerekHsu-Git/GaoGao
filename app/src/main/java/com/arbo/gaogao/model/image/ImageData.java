package com.arbo.gaogao.model.image;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/28.
 */

public class ImageData {
    @SerializedName("base_url")
    private String base_url;//http://wpstatic.zuimeia.com 其实可以固定
    // ?imageMogr/v2/auto-orient/thumbnail/480x320/quality/100 请求小尺寸的图片
    @SerializedName("images")
    private ArrayList<imageItem> images;

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public ArrayList<imageItem> getImages() {
        return images;
    }

    public void setImages(ArrayList<imageItem> images) {
        this.images = images;
    }
}
