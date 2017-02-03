package com.arbo.gaogao.model.toutiao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/2/1.
 */

public class NewsBean {
    @SerializedName("title")
    private String title;
    @SerializedName("imgsrc")
    private String imgsrc;
    @SerializedName("source")
    private String source;
    @SerializedName("ptime")
    private String time;
    @SerializedName("docid")
    private String docid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }
}
