package com.arbo.gaogao.api;

import com.arbo.gaogao.model.toutiao.NewsDetail;
import com.arbo.gaogao.model.toutiao.NewsList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2017/2/1.
 */

public interface TopNewsApi {
    @GET("headline/T1348647909107/{id}-20.html")
    Observable<NewsList> getNewById(@Path("id") int id);

    @GET("{id}/full.html")
    Observable<NewsDetail> getNewsDetail(@Path("id") String id);
}
