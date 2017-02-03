package com.arbo.gaogao.app.main_tabs.presenter;

import com.arbo.gaogao.model.UserCase;
import com.arbo.gaogao.model.cipa.One;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/2/1.
 */

public class MainPresenterImpl implements MainContract.Presenter {

    private CompositeSubscription mCompositeSubscription;

    private MainContract.View mView;
    public MainPresenterImpl(MainContract.View view) {
        this.mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void unsubcrible() {
        if(mCompositeSubscription !=null){
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void getNavData() {
        Subscription subscription = UserCase.getInstance()
                .getOne()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        addSubscription(subscription);
    }

    Observer<One> observer = new Observer<One>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(One one) {
            mView.setNavData(one);

        }
    };

    @Override
    public void addSubscription(Subscription subscription) {
        if(mCompositeSubscription==null){
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }
}
