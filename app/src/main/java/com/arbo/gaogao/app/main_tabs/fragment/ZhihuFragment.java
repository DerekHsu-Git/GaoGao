package com.arbo.gaogao.app.main_tabs.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arbo.gaogao.R;
import com.arbo.gaogao.app.main_tabs.adapter.ZhihuAdapter;
import com.arbo.gaogao.app.main_tabs.presenter.ZhihuContract;
import com.arbo.gaogao.app.main_tabs.presenter.ZhihuPresenterImpl;
import com.arbo.gaogao.model.zhihu.ZhihuDaily;
import com.arbo.gaogao.view.GridItemDividerDecoration;
import com.arbo.gaogao.widget.WrapContentLinearLayoutManager;
import com.arbo.lib.base.ui.BaseFragment;
import com.arbo.lib.util.LogUtil;

/**
 * Created by Administrator on 2017/1/28.
 */

public class ZhihuFragment extends BaseFragment implements ZhihuContract.View {

    ZhihuPresenterImpl mPresenter;

    RecyclerView rvZhihu;
    ProgressBar progressBar;

    TextView noConnectionText;
    RecyclerView.OnScrollListener loadingMoreListener;
    private ConnectivityManager.NetworkCallback connectivityCallback;

    private String currentLoadDate;

    LinearLayoutManager mLinearLayoutManager;
    ZhihuAdapter zhihuAdapter;
    private boolean loading;
    private boolean isconnected = true;
    boolean monitoringConnectivity;

    @Override
    protected int initLayoutID() {
        return R.layout.zhihu_fragment_layout;
    }

    private void myinitViews() {
        rvZhihu = findView(R.id.rv_zhihu);
        progressBar = findView(R.id.progressBar);
        mPresenter = new ZhihuPresenterImpl(getContext(), this);
        zhihuAdapter = new ZhihuAdapter(getContext());
        /**
         * RV的滑动监听事件
         */
        initScrollListener();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mLinearLayoutManager = new WrapContentLinearLayoutManager(getContext());

        } else {
            mLinearLayoutManager = new LinearLayoutManager(getContext());
        }
        rvZhihu.setLayoutManager(mLinearLayoutManager);
        rvZhihu.setHasFixedSize(true);
        //添加分割线
        rvZhihu.addItemDecoration(new GridItemDividerDecoration(getContext(), R.dimen.divider_height, R.color.divider));
        // TODO: 16/8/13 add  animation
        rvZhihu.setItemAnimator(new DefaultItemAnimator());
        rvZhihu.setAdapter(zhihuAdapter);//添加数据源
        rvZhihu.addOnScrollListener(loadingMoreListener);
        //如果当前网络可用，则默认请求最近的数据
        if (isconnected) {
            loadData();
        }


    }


    private void initScrollListener() {
        loadingMoreListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) //向下滚动
                {
                    int visibleItemCount = mLinearLayoutManager.getChildCount();//当前页面显示的条数
                    int totalItemCount = mLinearLayoutManager.getItemCount();//本次页面中总条数
                    int pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();//可视页面中，最上面一条的位置（也就是看过的数目）
                    //如果当前看到的条数+前面看过的条数 >= 总的条数，那么则需要请求更多的历史数据
                    if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        loadMoreData();
                    }
                }
            }
        };

        /**
         * 网络状态监听
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    isconnected = true;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            noConnectionText.setVisibility(View.GONE);
                            loadData();//加载最新的日报
                        }
                    });
                }

                @Override
                public void onLost(Network network) {
                    isconnected = false;
                }
            };

        }
    }

    @Override
    public void setPresenter(Object presenter) {
        this.mPresenter = (ZhihuPresenterImpl) presenter;
    }


    @Override
    public void updateList(ZhihuDaily zhihuDaily) {
        if (loading) {//更新数据的时候，要先停止继续加载
            loading = false;
            zhihuAdapter.loadingFinish();
        }
        currentLoadDate = zhihuDaily.getDate();
        zhihuAdapter.addItems(zhihuDaily.getStoriesList());
//  如果请求到的新日报(20条)不足以加载满整个页面，则加载更多数据进来
//      if the new data is not full of the screen, need load more data
        if (!rvZhihu.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)) {
            loadMoreData();
        }
    }

    @Override
    public void showProgressDialog() {
        if (progressBar != null && !progressBar.isShown()) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hidProgressDialog() {
        if (progressBar != null && progressBar.isShown()) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 请求数据异常的时候，弹出此方框
     *
     * @param error
     */
    @Override
    public void showError(String error) {
        if (rvZhihu != null) {
            Snackbar.make(rvZhihu, getString(R.string.snack_infor), Snackbar.LENGTH_INDEFINITE)
                    .setAction("重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (currentLoadDate.equals("0")) {
                                mPresenter.getLatesDailys();
                            } else {
                                mPresenter.getMoreDailys(currentLoadDate);
                            }
                        }
                    }).show();
        }
    }


    private void checkConnectivity(View view) {
        final ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        isconnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if (!isconnected && progressBar != null) {//不判断容易抛出空指针异常
            progressBar.setVisibility(View.INVISIBLE);
            if (noConnectionText == null) {

                ViewStub stub_text = (ViewStub) view.findViewById(R.id.stub_noConnectionText);
                noConnectionText = (TextView) stub_text.inflate();
            }
            /**
             * 添加网络监听
             * */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                connectivityManager.registerNetworkCallback(
                        new NetworkRequest.Builder()
                                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(),
                        connectivityCallback);

                monitoringConnectivity = true;
            }

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        checkConnectivity(rootView);
        initLayoutID();
        myinitViews();
        initViewListener();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubcrible();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    private void loadData() {
        LogUtil.e("loadData ---------- fragment");
        currentLoadDate = "0";
        mPresenter.getLatesDailys();
    }

    private void loadMoreData() {

        mPresenter.getMoreDailys(currentLoadDate);
    }
}
