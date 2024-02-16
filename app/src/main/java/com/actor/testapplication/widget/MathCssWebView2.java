package com.actor.testapplication.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Keep;

import com.actor.myandroidframework.utils.LogUtils;
import com.actor.myandroidframework.utils.TextUtils2;
import com.actor.myandroidframework.widget.webview.BaseWebChromeClient;
import com.actor.myandroidframework.widget.webview.BaseWebView;
import com.actor.myandroidframework.widget.webview.BaseWebViewClient;
import com.blankj.utilcode.util.GsonUtils;

/**
 * description: Katex, MathJax 公式等
 * company    :
 * @author    : ldf
 * date       : 2024/2/2 on 13
 * @see MathCssView
 */
public class MathCssWebView2 extends BaseWebView {

    public MathCssWebView2(Context context) {
        super(context);
    }

    public MathCssWebView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MathCssWebView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MathCssWebView2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MathCssWebView2(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        init(new BaseWebViewClient(), new BaseWebChromeClient());
        loadUrl("file:///android_asset/demo.html");
//        loadUrl("https://picture.halzwl.cn/demo.html");
//        loadUrl("https://picture.halzwl.cn/picture/error/1fe006c467814ab3bc70c69f2c316d25demo.html");

//        loadUrl("https://picture.halzwl.cn/picture/error/2ac3e65bb3884116a2f00de51a45fb59test2.html");

//        addJavascriptInterface(this, "Android");
    }

    /**
     * 设置服务器返回的内容, 字符串, 不是网址
     */
    public void setContent(String str) {
        LogUtils.error(str);
        if (str == null) str = "";

        // TODO: \n不能自动换行
//        loadUrl(TextUtils2.getStringFormat("javascript:setContent('%s')", str));


//        loadUrl("javascript:setContent('${str?.replace("\\frac", "\\\\frac")
//            ?.replace("\\sqrt", "\\\\sqrt")
//            ?.replace("\\left", "\\\\left")
//            ?: ""}')")

//        loadUrl("javascript:setContent('${str?.replace("\n", "<br />") ?: ""}')");


        JsonWeb jsonWeb = new JsonWeb(str);
        String json = GsonUtils.toJson(jsonWeb);
        LogUtils.error(json);
        loadUrl(TextUtils2.getStringFormat("javascript:setContent(%s)", json));
    }

    @Keep
    private static class JsonWeb {
        String value;
        public JsonWeb(String value) {
            this.value = value;
        }
    }
}