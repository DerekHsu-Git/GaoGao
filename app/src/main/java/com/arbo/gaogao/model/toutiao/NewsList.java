package com.arbo.gaogao.model.toutiao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/1.
 */

public class NewsList {

    @SerializedName("T1348647909107")
    ArrayList<NewsBean> newsList;

    public ArrayList<NewsBean> getNewsList() {
        return newsList;
    }

    public void setNewsList(ArrayList<NewsBean> newsList) {
        this.newsList = newsList;
    }
}
