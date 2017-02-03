package com.arbo.gaogao.model.zhihu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/1/28.
 */

public class ZhihuStory {

    //http://news-at.zhihu.com/api/4/news/9167739(文章id)

    @SerializedName("body")
    private String body;
    @SerializedName("title")
    private String title;
    @SerializedName("image")
    private String image;
    @SerializedName("share_url")
    private String mShareUrl;
    @SerializedName("css")
    private String[] css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getmShareUrl() {
        return mShareUrl;
    }

    public void setmShareUrl(String mShareUrl) {
        this.mShareUrl = mShareUrl;
    }

    public String[] getCss() {
        return css;
    }

    public void setCss(String[] css) {
        this.css = css;
    }
}
