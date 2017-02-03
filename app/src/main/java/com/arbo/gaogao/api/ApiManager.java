package com.arbo.gaogao.api;


import com.arbo.gaogao.MyApp;
import com.arbo.gaogao.util.NetWorkUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/1/28.
 */

public class ApiManager {

    private volatile static ApiManager apiManager;

    private ApiManager() {}

    public static ApiManager getInstance(){
        if(apiManager==null){
            synchronized (ApiManager.class){
                if(apiManager==null){
                    apiManager = new ApiManager();
                }
            }
        }
        return apiManager;
    }


    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originResponse = chain.proceed(chain.request());
            //若网络可用，则仅保存1min
            int cacheTime;
            String customHeader ;
            if(NetWorkUtil.isNetWorkAvailable(MyApp.getContext())){
                cacheTime = 60;
                customHeader = "public, max-age="+cacheTime;
            }else{
                cacheTime = 60*60*24*28;//四个星期
                customHeader = "public, only-if-cached, max-stale="+cacheTime;
            }
            return originResponse.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-control")
                    .header("Cache-Control",customHeader)
                    .build();
        }
    };

    //定义缓存(路径-缓存空间)->定义客户端
    private static File httpCacheDir = new File(MyApp.getContext().getCacheDir(),"netWorkCache");
    private static int cacheSize = 10 * 1024 *1024;    //10Mb
    private static Cache cache = new Cache(httpCacheDir,cacheSize);

    private static OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache)
            .build();

    //定义各api的请求服务
    public ZhihuApi zhihuApi;
    private final Object monitor = new Object();

    //用于在请求数据的时候使用
    public ZhihuApi getZhihuApiService(){
        if(zhihuApi==null) {
            synchronized (monitor){
                if(zhihuApi==null){
                    zhihuApi = new Retrofit.Builder()
                            .baseUrl("http://news-at.zhihu.com/")
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build().create(ZhihuApi.class);
                }
            }
        }
        return  zhihuApi;
    }

    private CibaApi cibaApi;

    public CibaApi getCibaApiService(){
        if(cibaApi == null){
            synchronized (monitor){
                if(cibaApi == null){
                    cibaApi = new Retrofit.Builder()
                            .baseUrl("http://open.iciba.com/dsapi/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .build().create(CibaApi.class);
                }
            }
        }
        return cibaApi;
    }

    private TopNewsApi topNewsApi;

    public TopNewsApi getTopNewsApiService(){
        if(topNewsApi == null){
            synchronized (monitor){
                if (topNewsApi==null){
                    topNewsApi = new Retrofit.Builder()
                            .baseUrl("http://c.m.163.com/nc/article/")
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build().create(TopNewsApi.class);
                }
            }
        }
        return topNewsApi;
    }

}
