package com.arbo.gaogao.app.main_tabs.presenter;

import android.content.Context;

import com.arbo.gaogao.model.UserCase;
import com.arbo.gaogao.model.zhihu.ZhihuStory;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/1/31.
 */

public class ZhihuStoryPresenterImpl implements ZhihuStoryContract.Presenter {

    private Context mContext;
    private ZhihuStoryContract.View mView;
    //private UserCase userCase;

    public ZhihuStoryPresenterImpl(Context mContext, ZhihuStoryContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
        mView.setPresenter(this);
      //  userCase = new UserCase();
    }

    /**
     * 解决Observable持有Context导致的内存泄露
     * 常规用法是：使用CompositeSubscription来持有所有Subscription
     * 在生命周期的某个时刻取消订阅:onDestroy()或者onDestroyView()
     */
    private CompositeSubscription mCompositeSubscription;



    @Override
    public void start() {}

    @Override
    public void stop() {}

    @Override
    public void unsubcrible() {
        if(mCompositeSubscription!=null){
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void resume() {}

    @Override
    public void getZhihuStory(String id) {
        Subscription subscription = UserCase.getInstance().getStoryById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        addSubscription(subscription);
    }

    Observer observer = new Observer<ZhihuStory>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(ZhihuStory zhihuStory) {
            mView.showTitle(zhihuStory.getTitle());
            mView.showImage(zhihuStory.getImage());
            mView.showWebView(zhihuStory);
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
