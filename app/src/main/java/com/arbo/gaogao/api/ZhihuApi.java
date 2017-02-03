package com.arbo.gaogao.api;

import com.arbo.gaogao.model.image.ImageResponse;
import com.arbo.gaogao.model.zhihu.ZhihuDaily;
import com.arbo.gaogao.model.zhihu.ZhihuStory;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2017/1/28.
 */

public interface ZhihuApi {

    @GET("api/4/news/{id}")
    Observable<ZhihuStory> getStory(@Path("id") String id);

    @GET("api/4/news/latest")
    Observable<ZhihuDaily> getLatestDaily();

    @GET("api/4/news/before/{date}")
    Observable<ZhihuDaily> getMoreDaily(@Path("date") String date);

    @GET("http://lab.zuimeia.com/wallpaper/category/1/?page_size=1")
    Observable<ImageResponse> getImage();

}
