package com.arbo.gaogao.app.main_tabs.presenter;

import android.content.Context;

import com.arbo.gaogao.model.UserCase;
import com.arbo.gaogao.model.zhihu.ZhihuDaily;
import com.arbo.gaogao.util.CacheUtil;
import com.arbo.lib.util.LogUtil;
import com.google.gson.Gson;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/1/28.
 */

public class ZhihuPresenterImpl implements ZhihuContract.Presenter {

    private String TAG = "ZhihuPresenterImpl";
    private ZhihuContract.View mView;
    private CacheUtil mCacheUtil;
    private Gson gson = new Gson();

    public ZhihuPresenterImpl(Context context, ZhihuContract.View View) {
        this.mView = View;
        mView.setPresenter(this);
        mCacheUtil = CacheUtil.get(context);
       // mUserCase = new UserCase();
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void resume() {

    }

    /**
     * 解决Observable持有Context导致的内存泄露
     * 常规用法是：使用CompositeSubscription来持有所有Subscription
     * 在生命周期的某个时刻取消订阅:onDestroy()或者onDestroyView()
     */
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscription);
    }

    @Override
    public void unsubcrible() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }


    @Override
    public void getLatesDailys() {
        mView.showProgressDialog();
        LogUtil.e(TAG + "getLatesDailys");
        Subscription subscription = UserCase.getInstance().getZhihuLatestObserVable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(myObserver);
        addSubscription(subscription);
    }

    Observer<ZhihuDaily> myObserver = new Observer<ZhihuDaily>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            LogUtil.e(TAG + "----->onError" + e.getMessage());
            e.printStackTrace();
            mView.hidProgressDialog();
            mView.showError(e.getMessage());
        }

        @Override
        public void onNext(ZhihuDaily zhihuDaily) {
            mView.hidProgressDialog();
            mCacheUtil.put("zhihu", gson.toJson(zhihuDaily));
            mView.updateList(zhihuDaily);
        }
    };

    @Override
    public void getMoreDailys(String date) {
        mView.showProgressDialog();
        LogUtil.e("getMoreDailys----->" + date);
        Subscription mSubscription = UserCase.getInstance().getZhihuMoreObserVable(date)
                .subscribeOn(Schedulers.io())//必须，否则error
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuDaily>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG + "getMoreDailys----->onError" + e.getMessage());
                        e.printStackTrace();
                        mView.hidProgressDialog();
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ZhihuDaily zhihuDaily) {
                        mView.hidProgressDialog();
                        mView.updateList(zhihuDaily);
                    }
                });
        addSubscription(mSubscription);
    }


    /**
     * 从缓存里面加载日报列表
     * (未使用到)
     */
    public void getLastFromCache() {
        if (mCacheUtil.getAsJSONObject("zhihu") != null) {
            ZhihuDaily zhihuDaily = gson.fromJson(
                    mCacheUtil.getAsJSONObject("zhihu").toString(), ZhihuDaily.class);
            mView.updateList(zhihuDaily);
        }
    }
}
