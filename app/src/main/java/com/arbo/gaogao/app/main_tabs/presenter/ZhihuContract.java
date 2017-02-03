package com.arbo.gaogao.app.main_tabs.presenter;

import com.arbo.gaogao.model.zhihu.ZhihuDaily;
import com.arbo.lib.base.mvp.BasePresenter;
import com.arbo.lib.base.mvp.BaseView;

import rx.Subscription;

/**
 * Created by Administrator on 2017/1/28.
 */

public interface ZhihuContract {
    interface Presenter extends BasePresenter{

        void getLatesDailys();//获取当天的日报,max = 20

        void getMoreDailys(String date);//获取date之前的一天的日报 20

        void addSubscription(Subscription subscription);
    }

    interface View<T> extends BaseView<Presenter>{
        void updateList(ZhihuDaily zhihuDaily);

        void showProgressDialog();

        void hidProgressDialog();

        void showError(String error);
    }
}
