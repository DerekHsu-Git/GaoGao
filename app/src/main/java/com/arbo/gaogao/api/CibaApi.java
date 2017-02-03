package com.arbo.gaogao.api;

import com.arbo.gaogao.model.cipa.One;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Administrator on 2017/2/1.
 */

public interface CibaApi {
    @GET("http://open.iciba.com/dsapi/")
    Observable<One> getOne();

}
