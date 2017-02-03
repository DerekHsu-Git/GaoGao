package com.arbo.gaogao.app.main_tabs.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arbo.gaogao.R;
import com.arbo.gaogao.app.main_tabs.fragment.TopNewsFragment;
import com.arbo.gaogao.app.main_tabs.fragment.ZhihuFragment;
import com.arbo.gaogao.app.main_tabs.presenter.MainContract;
import com.arbo.gaogao.app.main_tabs.presenter.MainPresenterImpl;
import com.arbo.gaogao.base.AppBaseActivity;
import com.arbo.gaogao.model.cipa.One;
import com.arbo.gaogao.util.AnimUtils;
import com.arbo.gaogao.util.SharePreferenceUtil;
import com.arbo.gaogao.util.ViewUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnApplyWindowInsetsListener,MainContract.View{

    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer)
    DrawerLayout drawer;

    MenuItem currentMenuItem;
    Fragment currentFragment;
    SimpleArrayMap<Integer,String> mTitleMap = new SimpleArrayMap<>();
    private MainContract.Presenter mPresenter;

    @Override
    protected int initLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        super.initViews();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = getDrawerToggle();
        toggle.syncState();

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            animateToolbar();
        }

        addTitle();

        drawer.addDrawerListener(toggle);
        drawer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        if (currentMenuItem!=null){
            Fragment fragment = getFragmentById(currentMenuItem.getItemId());
            String title = mTitleMap.get((Integer) currentMenuItem.getItemId());
            if (fragment != null) {
                switchFragment(fragment, title);
            }
        }else {
            switchFragment(new ZhihuFragment(), " ");
            currentMenuItem=navView.getMenu().findItem(R.id.zhihuitem);

        }

        navView.setNavigationItemSelectedListener(this);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            drawer.setOnApplyWindowInsetsListener(this);
        }
    }

    private void switchFragment(Fragment fragment, String title) {

        if (currentFragment == null || !currentFragment
                .getClass().getName().equals(fragment.getClass().getName()))
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                    .commit();
        currentFragment = fragment;

    }

    private Fragment getFragmentById(int id){
        Fragment fragment = null;
        switch (id){
            case R.id.zhihuitem:
                fragment = new ZhihuFragment();
                break;
            case R.id.topnewsitem:
                fragment = new TopNewsFragment();
                break;
        }
        return fragment;
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        super.process(savedInstanceState);
        mPresenter = new MainPresenterImpl(this);
        mPresenter.getNavData();
    }

    private void addTitle() {
        mTitleMap.put(R.id.zhihuitem,"知乎日报");
        mTitleMap.put(R.id.topnewsitem,"网易头条");
       // mTitleMap.put(R.id.meiziitem,"每日看看");
    }

    private ActionBarDrawerToggle getDrawerToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        return toggle;
    }

    @Override
    protected void initViewListener() {
        super.initViewListener();
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void animateToolbar() {
        // this is gross but toolbar doesn't expose it's children to animate them :(
        View t = toolbar.getChildAt(0);
        if (t != null && t instanceof TextView) {
            TextView title = (TextView) t;

            // fade in and space out the title.  Animating the letterSpacing performs horribly so
            // fake it by setting the desired letterSpacing then animating the scaleX ¯\_(ツ)_/¯
            title.setAlpha(0f);
            title.setScaleX(0.8f);

            title.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .setStartDelay(500)
                    .setDuration(900)
                    .setInterpolator(AnimUtils.getFastOutSlowInInterpolator(this)).start();
        }
        View amv = toolbar.getChildAt(1);
        if (amv != null & amv instanceof ActionMenuView) {
            ActionMenuView actions = (ActionMenuView) amv;
            popAnim(actions.getChildAt(0), 500, 200); // filter
            popAnim(actions.getChildAt(1), 700, 200); // overflow
        }
    }

    private void popAnim(View v, int startDelay, int duration) {
        if (v != null) {
            v.setAlpha(0f);
            v.setScaleX(0f);
            v.setScaleY(0f);

            v.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setStartDelay(startDelay)
                    .setDuration(duration)
                    .setInterpolator(AnimationUtils.loadInterpolator(this,
                            android.R.interpolator.overshoot)).start();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return false;//return false则不显示右边的按钮
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.nav_theme || item.getItemId() == R.id.nav_send){
            return false;
        }
        if (currentMenuItem != item && currentMenuItem != null) {
            currentMenuItem.setChecked(false);
            int id = item.getItemId();
            SharePreferenceUtil.putNevigationItem(MainActivity.this, id);
            currentMenuItem = item;
            currentMenuItem.setChecked(true);
            switchFragment(getFragmentById(currentMenuItem.getItemId()),mTitleMap.get(currentMenuItem.getItemId()));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 适配frameLayout与toolbar的距离：toolbar的高度
     * @param v
     * @param insets
     * @return
     */
    @Override
    public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
            // inset the toolbar down by the status bar height
            ViewGroup.MarginLayoutParams lpToolbar = (ViewGroup.MarginLayoutParams) toolbar
                    .getLayoutParams();
            lpToolbar.topMargin += insets.getSystemWindowInsetTop();
            lpToolbar.rightMargin += insets.getSystemWindowInsetRight();
            //toolbar.setLayoutParams(lpToolbar);

            // inset the grid top by statusbar+toolbar & the bottom by the navbar (don't clip)
            fragmentContainer.setPadding(fragmentContainer.getPaddingLeft(),
                    insets.getSystemWindowInsetTop() + ViewUtils.getActionBarSize
                            (MainActivity.this),
                    fragmentContainer.getPaddingRight() + insets.getSystemWindowInsetRight(), // landscape
                    fragmentContainer.getPaddingBottom() + insets.getSystemWindowInsetBottom());

            // we place a background behind the status bar to combine with it's semi-transparent
            // color to get the desired appearance.  Set it's height to the status bar height
            View statusBarBackground = findViewById(R.id.status_bar_background);
            FrameLayout.LayoutParams lpStatus = (FrameLayout.LayoutParams)
                    statusBarBackground.getLayoutParams();
            lpStatus.height = insets.getSystemWindowInsetTop();
            statusBarBackground.setLayoutParams(lpStatus);
            statusBarBackground.setBackgroundColor(0xbcf40b0b);//此项设置状态栏的颜色

            // inset the filters list for the status bar / navbar
            // need to set the padding end for landscape case

            // clear this listener so insets aren't re-applied
            drawer.setOnApplyWindowInsetsListener(null);
            return insets.consumeSystemWindowInsets();
    }

    @Override
    public void setPresenter(Object presenter) {
        this.mPresenter = (MainContract.Presenter) presenter;
    }

    @Override
    public void setNavData(One one) {
        View headerLayout = navView.getHeaderView(0);
        final LinearLayout background =  (LinearLayout) headerLayout.findViewById(R.id.background_layout);
        Glide.with(this)
                .load(one.getPicture())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        background.setBackground(resource);
                    }
                });
        TextView eng = (TextView)headerLayout.findViewById(R.id.english_tv);
        TextView cha = (TextView)headerLayout.findViewById(R.id.chinese_tv);
        eng.setText(one.getContent());
        cha.setText(one.getNote());
    }

}
