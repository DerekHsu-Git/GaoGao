package com.arbo.gaogao.app.main_tabs.presenter;

import com.arbo.gaogao.model.toutiao.NewsDetail;
import com.arbo.gaogao.util.NewsJsonUtils;
import com.arbo.gaogao.util.OkHttpUtils;
import com.arbo.gaogao.util.Urls;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/2/2.
 */

public class TopNewsDetailPresenterImpl implements TopNewsDetailContract.Presenter {

    private TopNewsDetailContract.View mView;

    private CompositeSubscription mCompositeSubscription;

    public TopNewsDetailPresenterImpl(TopNewsDetailContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
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

    private String getDetailUrl(String docId) {
        StringBuffer sb = new StringBuffer(Urls.NEW_DETAIL);
        sb.append(docId).append(Urls.END_DETAIL_URL);
        return sb.toString();
    }

    @Override
    public void getNewsDetail(final String id) {
        mView.showProgressDialog();
        String url = getDetailUrl(id);
        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                mView.hidProgressDialog();
                NewsDetail newsDetailBean = NewsJsonUtils.readJsonNewsDetailBeans(response, id);
                mView.showWebView(newsDetailBean);
            }

            @Override
            public void onFailure(Exception e) {
                mView.hidProgressDialog();
            }
        };
        OkHttpUtils.get(url, loadNewsCallback);

    }

    @Override
    public void addSubscription(Subscription subscription) {
        if(mCompositeSubscription==null){
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }
}
