package com.arbo.gaogao.model.zhihu;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/28.
 */

public class ZhihuDaily {

    //

    @SerializedName("date")
    private String date;
    @SerializedName("stories")
    private ArrayList<ZhihuDailyItem> storiesList;
    @SerializedName("top_stories")
    private ArrayList<ZhihuDailyItem> topStoryList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ZhihuDailyItem> getStoriesList() {
        return storiesList;
    }

    public void setStoriesList(ArrayList<ZhihuDailyItem> storiesList) {
        this.storiesList = storiesList;
    }

    public ArrayList<ZhihuDailyItem> getTopStoryList() {
        return topStoryList;
    }

    public void setTopStoryList(ArrayList<ZhihuDailyItem> topStoryList) {
        this.topStoryList = topStoryList;
    }
}
