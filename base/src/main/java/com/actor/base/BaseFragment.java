package com.actor.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.actor.myandroidframework.fragment.ActorBaseFragment;

/**
 * Description: 类的描述
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019-9-17 on 15:13
 *
 * @version 1.0
 */
public abstract class BaseFragment extends ActorBaseFragment {

    protected View    mDemoView;
    protected WebView mWebView;
    protected boolean isDemoShow = true;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDemoView = view.findViewById(R.id.demo);
        mWebView = view.findViewById(R.id.web);
        String url = getUrl();
        if (mWebView != null && url != null) {
            mWebView.getSettings().setDisplayZoomControls(false);
            mWebView.loadUrl(url);
        }
        refreshView();
    }

    /**
     * 点击切换教程和案例
     */
    protected void refreshView() {
        if (mDemoView != null) mDemoView.setVisibility(isDemoShow ? View.VISIBLE : View.INVISIBLE);
        if (mWebView != null) mWebView.setVisibility(isDemoShow ? View.INVISIBLE : View.VISIBLE);
    }

    /**
     * 右上角 使用教程按钮
     * 点击切换教程和案例
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        if (menu.getItemId() == R.id.action_example) {
            isDemoShow = !isDemoShow;
            refreshView();
            return true;
        }
        return false;
    }

    protected String getUrl() {
        return null;
    }
}
