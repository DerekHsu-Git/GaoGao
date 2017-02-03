package com.arbo.gaogao.app.main_tabs.presenter;

import com.arbo.gaogao.model.zhihu.ZhihuStory;
import com.arbo.lib.base.mvp.BasePresenter;
import com.arbo.lib.base.mvp.BaseView;

import rx.Subscription;

/**
 * Created by Administrator on 2017/1/31.
 */

public interface ZhihuStoryContract {
    interface Presenter extends BasePresenter{
        void getZhihuStory(String id);
        void addSubscription(Subscription subscription);
    }

    interface View<T> extends BaseView<Presenter>{
        void showWebView(ZhihuStory zhihuStory);
        void showTitle(String title);
        void showImage(String imageUrl);
    }
}
