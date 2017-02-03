package com.arbo.gaogao;

import android.app.Application;
import android.content.Context;


/**
 * @name 
 * @class name：
 * @class describe
 * @anthor Xujianbo E-mail: xuarbo@qq.com
 * @time 2017/2/3 11:41
 * @change
 * @chang time
 * @class describe
 */
public class MyApp extends Application {

    private static MyApp myApp;

    public static Context getContext(){
        return myApp;
    }

    public static Application get(){
        return myApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
    }
}
