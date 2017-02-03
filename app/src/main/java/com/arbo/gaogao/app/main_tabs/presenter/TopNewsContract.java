package com.arbo.gaogao.app.main_tabs.presenter;

import com.arbo.gaogao.model.toutiao.NewsList;
import com.arbo.lib.base.mvp.BasePresenter;
import com.arbo.lib.base.mvp.BaseView;

import rx.Subscription;

/**
 * Created by Administrator on 2017/2/1.
 */

public interface TopNewsContract {
    interface Presenter extends BasePresenter{
        void getTopNews();

        void getMoreNews(int index);

        void addSubscription(Subscription subscription);
    }

    interface View<T> extends BaseView<Presenter>{
        void updateList(NewsList newsList);

        void showProgressDialog();

        void hidProgressDialog();

        void showError(String error);
    }
}
