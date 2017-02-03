package com.arbo.gaogao.model.image;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/1/28.
 */

public class imageItem {
    @SerializedName("description")
    private String description;
    @SerializedName("image_url")
    private String image_url;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
