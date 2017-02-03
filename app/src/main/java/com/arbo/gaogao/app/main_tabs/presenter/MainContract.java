package com.arbo.gaogao.app.main_tabs.presenter;

import com.arbo.gaogao.model.cipa.One;
import com.arbo.lib.base.mvp.BasePresenter;
import com.arbo.lib.base.mvp.BaseView;

import rx.Subscription;

/**
 * Created by Administrator on 2017/2/1.
 */

public interface MainContract {
    interface Presenter extends BasePresenter{
        void getNavData();
        void addSubscription(Subscription subscription);

    }

    interface View<T> extends BaseView<Presenter>{
        void setNavData(One one);
    }

}
