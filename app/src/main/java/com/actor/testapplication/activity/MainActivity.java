package com.actor.testapplication.activity;

import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.VideoView;

import com.actor.testapplication.R;
import com.actor.testapplication.databinding.ActivityMainBinding;
import com.actor.testapplication.utils.CheckUpdateUtils;
import com.blankj.utilcode.util.AppUtils;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    // TODO: 2021/8/6 Surface
    Surface       surface;//extends Object implements Parcelable
    SurfaceView   surfaceView = null;//extends View
    GLSurfaceView glSurfaceView;//extends SurfaceView
    VideoView     videoView;//extends SurfaceView

    SurfaceTexture surfaceTexture;//extends Object
    TextureView    textureView = null;//extends View

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.AppInfo appInfo = AppUtils.getAppInfo();
        viewBinding.tvVersion.setText(getStringFormat("VersionName: %s(VersionCode: %d)", appInfo.getVersionName(), appInfo.getVersionCode()));//版本
        new CheckUpdateUtils().check(this);//检查更新
    }

    @Override
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_rich_text://富文本测试
                startActivity(new Intent(this, RichTextActivity.class));
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
            case R.id.btn_reference:
                startActivity(new Intent(this, ReferenceActivity.class));
                break;
            case R.id.btn_go2_test://Test测试页面
                startActivity(new Intent(this, TestActivity.class));
                break;
            case R.id.btn_go2_math_view://MathView测试
                startActivity(new Intent(this, MathViewActivity.class), false, null, null, view);
                break;
            case R.id.btn_novel://小说
                startActivity(new Intent(this, NovelActivity.class));
                break;
            default:
                break;
        }
    }
}
