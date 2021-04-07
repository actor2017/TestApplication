package com.actor.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actor.testapplication.R;
import com.actor.testapplication.service.CheckUpdateService;
import com.actor.testapplication.widget.BasePopupWindow;
import com.blankj.utilcode.util.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_version)//版本
    TextView  tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setTitle("主页");
        AppUtils.AppInfo appInfo = AppUtils.getAppInfo();
        tvVersion.setText(getStringFormat("VersionName: %s(VersionCode: %d)", appInfo.getVersionName(), appInfo.getVersionCode()));//版本
        startService(new Intent(this, CheckUpdateService.class));//检查更新
    }

    @OnClick({R.id.btn_path, R.id.btn_rich_text,
            R.id.btn_c_call_java, R.id.btn_custom_view, R.id.btn_surface_view,
            R.id.btn_nested_scroll_view, R.id.btn_encrypt, R.id.btn_go2_test})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_path://Path绘制测试
                startActivity(new Intent(this, PathDrawActivity.class));
                break;
            case R.id.btn_rich_text://富文本测试
                startActivity(new Intent(this, RichTextActivity.class));
                break;
            case R.id.btn_c_call_java://C&Java互调测试
                startActivity(new Intent(this, CCallJavaActivity.class));
                break;
            case R.id.btn_custom_view://自定义View
                startActivity(new Intent(this, CustomViewActivity.class));
                break;
            case R.id.btn_surface_view://SurfaceView
                startActivity(new Intent(this, SurfaceViewActivity.class));
                break;
            case R.id.btn_nested_scroll_view://NestedScrollView
                startActivity(new Intent(this, NestedScrollViewActivity.class));
                break;
            case R.id.btn_encrypt://加密解密
                startActivity(new Intent(this, EncryptActivity.class));
                break;
            case R.id.btn_go2_test://Test测试页面
                startActivity(new Intent(this, TestActivity.class), false, view);
//                showPopupWindow(view);
                break;
            default:
                break;
        }
    }

    private void showPopupWindow(View v) {
        BasePopupWindow popup = new BasePopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setLayout(activity, R.layout.item_add_minus_layout);

        //显示在某个位置
//        popup.showAtLocation(v, Gravity.BOTTOM, 0, 0);

        //显示在某个控件正下方
        popup.showAsDropDown(v, 0, 0, Gravity.TOP | Gravity.END);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, CheckUpdateService.class));
    }
}
