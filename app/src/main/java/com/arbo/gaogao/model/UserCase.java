package com.arbo.gaogao.model;

import com.arbo.gaogao.api.ApiManager;
import com.arbo.gaogao.model.cipa.One;
import com.arbo.gaogao.model.toutiao.NewsDetail;
import com.arbo.gaogao.model.toutiao.NewsList;
import com.arbo.gaogao.model.zhihu.ZhihuDaily;
import com.arbo.gaogao.model.zhihu.ZhihuDailyItem;
import com.arbo.gaogao.model.zhihu.ZhihuStory;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/1/29.
 */

public class UserCase {

    private static UserCase userCase;

    private UserCase() {
    }

    public static UserCase getInstance(){
        if(userCase == null){
            synchronized (UserCase.class){
                if ((userCase==null)){
                    userCase = new UserCase();
                }
            }
        }
        return userCase;
    }

    public Observable<ZhihuDaily> getZhihuLatestObserVable() {
        return ApiManager.getInstance()
                .getZhihuApiService()
                .getLatestDaily()
                .map(new Func1<ZhihuDaily, ZhihuDaily>() {
                    @Override
                    public ZhihuDaily call(ZhihuDaily zhihuDaily) {
                        String date = zhihuDaily.getDate();
                        for (ZhihuDailyItem zhihuDailyItem : zhihuDaily.getStoriesList()) {
                            zhihuDailyItem.setDate(date);
                        }
                        return zhihuDaily;
                    }
                });
    }

    public Observable<ZhihuDaily> getZhihuMoreObserVable(String date) {
        return ApiManager.getInstance()
                .getZhihuApiService()
                .getMoreDaily(date)
                .map(new Func1<ZhihuDaily, ZhihuDaily>() {
                    @Override
                    public ZhihuDaily call(ZhihuDaily zhihuDaily) {
                        String date = zhihuDaily.getDate();
                        for (ZhihuDailyItem zhihuDailyItem : zhihuDaily.getStoriesList()) {
                            zhihuDailyItem.setDate(date);
                        }
                        return zhihuDaily;
                    }
                });
    }


    public Observable<ZhihuStory> getStoryById(String id){
        return ApiManager.getInstance()
                .getZhihuApiService()
                .getStory(id);
    }

    public Observable<One> getOne(){
        return ApiManager.getInstance()
                .getCibaApiService()
                .getOne();
    }

    public Observable<NewsList> getTopNews(int id){
        return ApiManager.getInstance()
                .getTopNewsApiService()
                .getNewById(id);
    }

    public Observable<NewsDetail> getNewsDetail(String docid){
        return ApiManager.getInstance()
                .getTopNewsApiService()
                .getNewsDetail(docid);
    }

}
