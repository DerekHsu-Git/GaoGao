package com.arbo.gaogao.app.main_tabs.presenter;

import android.content.Context;

import com.arbo.gaogao.Config;
import com.arbo.gaogao.model.UserCase;
import com.arbo.gaogao.model.toutiao.NewsList;
import com.arbo.gaogao.util.CacheUtil;
import com.google.gson.Gson;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/2/2.
 */

public class TopNewsPresenterImpl implements TopNewsContract.Presenter{

    TopNewsContract.View mView ;
    private CacheUtil mCacheUtil;
    private Gson gson = new Gson();

    private CompositeSubscription mCompositeSubscription;


    public TopNewsPresenterImpl(Context context,TopNewsContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
        mCacheUtil = CacheUtil.get(context);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void unsubcrible() {
        if(mCompositeSubscription!=null){
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void getTopNews() {
        mView.showProgressDialog();
        Subscription subscription = UserCase.getInstance()
                .getTopNews(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        addSubscription(subscription);
    }

    Observer<NewsList> observer = new Observer<NewsList>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            mView.hidProgressDialog();
            mView.showError(e.getMessage());
        }

        @Override
        public void onNext(NewsList newsList) {
            mView.hidProgressDialog();
            mCacheUtil.put(Config.TOPNEWS,gson.toJson(newsList));
            mView.updateList(newsList);
        }
    };

    @Override
    public void getMoreNews(int index) {
        mView.showProgressDialog();
        Subscription subscription = UserCase.getInstance()
                .getTopNews(index)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mobserver);
        addSubscription(subscription);
    }

    Observer<NewsList> mobserver = new Observer<NewsList>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            mView.hidProgressDialog();
            mView.showError(e.getMessage());
        }

        @Override
        public void onNext(NewsList newsList) {
            mView.hidProgressDialog();
            mView.updateList(newsList);
        }
    };

    @Override
    public void addSubscription(Subscription subscription) {
        if(mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }
}
