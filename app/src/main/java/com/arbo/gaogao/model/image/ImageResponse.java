package com.arbo.gaogao.model.image;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/1/28.
 */

public class ImageResponse {
    @SerializedName("data")
    private ImageData data;

    public ImageData getData() {
        return data;
    }

    public void setData(ImageData data) {
        this.data = data;
    }
}
