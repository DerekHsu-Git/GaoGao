package com.arbo.gaogao.app.main_tabs.presenter;

import com.arbo.gaogao.model.toutiao.NewsDetail;
import com.arbo.lib.base.mvp.BasePresenter;
import com.arbo.lib.base.mvp.BaseView;

import rx.Subscription;

/**
 * Created by Administrator on 2017/2/2.
 */

public interface TopNewsDetailContract {

    interface Presenter extends BasePresenter {
        void getNewsDetail(String id);
        void addSubscription(Subscription subscription);
    }

    interface View<T> extends BaseView<Presenter> {
        void showWebView(NewsDetail newsDetail);
        void showProgressDialog();
        void hidProgressDialog();
    }
}
