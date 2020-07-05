package com.actor.android5.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.actor.android5.Constants;
import com.actor.android5.R;
import com.actor.android5.fragment.AnimationFragment;
import com.actor.android5.fragment.DrawableFragment;
import com.actor.android5.fragment.DrawerFragment;
import com.actor.android5.fragment.ShadowFragment;
import com.actor.android5.fragment.StyleFragment;
import com.actor.android5.fragment.SupportFragment;
import com.actor.android5.fragment.WidgetFragment;
import com.actor.base.BaseActivity;
import com.actor.base.BaseFragment;
import com.blankj.utilcode.util.FragmentUtils;


public class Android5MainActivity extends BaseActivity {

    DrawerLayout mDrawerLayout;//侧边栏

    private DrawerFragment        mDrawerFragment;
    private ActionBarDrawerToggle mToggle;
    //设置样式子类StyleFragment
    private BaseFragment          mCurrentFragment;
    private FragmentManager       fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Constants.M_THEME != -1) {
            //点击修改主题后，会重新启动MainActivity
            //加载新的Material Design主题
            setTheme(Constants.M_THEME);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android5_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        //从左往右滑
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            /**
             * Drawer的回调方法
             * 打开drawer
             * 需要在该方法中对Toggle做对应的操作
             */
            @Override
            public void onDrawerOpened(View drawerView) {
                mToggle.onDrawerOpened(drawerView);//需要把开关也变为打开
                invalidateOptionsMenu();
            }

            /**
             * 关闭drawer
             */
            @Override
            public void onDrawerClosed(View drawerView) {
                mToggle.onDrawerClosed(drawerView);//需要把开关也变为关闭
                invalidateOptionsMenu();
            }

            /**
             * drawer滑动的回调
             */
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mToggle.onDrawerSlide(drawerView, slideOffset);
            }

            /**
             * drawer状态改变的回调
             */
            @Override
            public void onDrawerStateChanged(int newState) {
                mToggle.onDrawerStateChanged(newState);
            }
        });
        //获取fragment管理器
        fragmentManager = getSupportFragmentManager();
        //侧边栏菜单
        mDrawerFragment = (DrawerFragment) fragmentManager.findFragmentById(R.id.navigation_drawer);

        //设置点击菜单条目监听
        mDrawerFragment.setOnDrawerItemSelectedListener(new DrawerFragment.OnDrawerItemSelectedListener() {
            /**
             * 侧边栏 抽屉菜单 的回调方法
             * 需要在该方法中添加对应的Framgment
             */
            @Override
            public void onDrawerItemSelected(int position) {
                //点击条目后关闭抽屉菜单
                mDrawerLayout.closeDrawer(GravityCompat.START);


                //创建fragment
                BaseFragment fragment = createFragment(position);
                mCurrentFragment = fragment;

                FragmentUtils.replace(fragmentManager, fragment, R.id.container);
            }
        });
        //默认选中第一个
        mDrawerFragment.selectItem(0);


        initActionBar();
//      Intent intent = new Intent();
//      intent.setAction("com.google.sample.fade");//Intent.ACTION_DIAL拨号
//		Intent intent = new Intent("com.google.sample.fade");
//      intent.setData(Uri.parse("callPhone://buzhidao"));
//      intent.setType("text/xxx");//和上面方法冲突
//      intent.setDataAndType();//data和type共存...

        //                          action, uri
//        Intent intent = new Intent("com.google.sample.fade", Uri.parse("callPhone://buzhidao"));
//        startActivity(intent);
    }

    /**
     * 初始化toolbar
     */
    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        //设置显示左侧按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
        //设置左侧按钮可点
        actionBar.setHomeButtonEnabled(true);
        //设置显示标题
        actionBar.setDisplayShowTitleEnabled(true);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

        //初始化开关，并和drawer关联
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //该方法会自动和actionBar关联
        mToggle.syncState();

        //设置标题
        actionBar.setTitle(getString(R.string.app_name));
    }

    /**
     * 设置菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            getMenuInflater().inflate(R.menu.main, menu);
            mCurrentFragment.onCreateOptionsMenu(menu, getMenuInflater());
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 选中菜单
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || mCurrentFragment.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    //fragment不能装进List&Map中!
    public BaseFragment createFragment(int position) {
        BaseFragment fragment = null;
        switch (position) {
            case 0:  //样式主题
                fragment = new StyleFragment();
                break;
            case 1:  //阴影
                fragment = new ShadowFragment();
                break;
            case 2:
                fragment = new DrawableFragment();
                break;
            case 3:
                fragment = new AnimationFragment();
                break;
            case 4:
                fragment = new WidgetFragment();
                break;
            case 5:
                fragment = new SupportFragment();
                break;
            default:
                break;
        }
        return fragment;
    }
}
