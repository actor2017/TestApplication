package com.actor.testapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.actor.myandroidframework.widget.webview.BaseWebView;
import com.actor.testapplication.activity.BaseActivity;
import com.actor.testapplication.databinding.ActivityAppLinksBinding;

/**
 * Tools -> App Links Assistant(App Links助手)
 * 点击链接, 直接跳转app的这个页面
 * https://blog.csdn.net/jack_bear_csdn/article/details/80579474
 * 看教程, 比较麻烦, 还需要后台配合.
 *
 * <activity android:name=".AppLinksActivity">
 *     <intent-filter>
 *         <action android:name="android.intent.action.VIEW" />
 *         <category android:name="android.intent.category.DEFAULT" />
 *         <category android:name="android.intent.category.BROWSABLE" />
 *         <data
 *             android:host="api.gitee.com"
 *             android:path="/TestApplication"
 *             android:scheme="https" />
 *     </intent-filter>
 * </activity>
 */
public class AppLinksActivity extends BaseActivity<ActivityAppLinksBinding> {

    private BaseWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("App Links(比较麻烦,需后台配合)");
        webView = viewBinding.webView;

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
//        webView.init(new BaseWebViewClient(), new BaseWebChromeClient());
    }
}