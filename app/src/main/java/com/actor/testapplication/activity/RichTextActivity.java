package com.actor.testapplication.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.actor.testapplication.R;
import com.actor.testapplication.utils.RichUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: 富文本测试
 * date       : 2020/12/10 on 15:46
 */
public class RichTextActivity extends BaseActivity {

    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich_text);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.image_btn, R.id.url_btn, R.id.color_btn1, R.id.color_btn2, R.id.font_btn,
            R.id.style_btn, R.id.strike_btn, R.id.underline_btn, R.id.btn_relative_size,
            R.id.btn_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_btn:
                tv.append(RichUtils.getBuilder("").addImageSpan(R.mipmap.ic_launcher, 10, 10).build());
                break;
            case R.id.url_btn:
                tv.setMovementMethod(LinkMovementMethod.getInstance());//否则没有点击响应
                tv.append(RichUtils.getBuilder("百度").addUrlSpan("https://www.baidu.com").build());
                break;
            case R.id.color_btn1:
                tv.append(RichUtils.getBuilder("蓝色").addForegroundColorSpan(Color.BLUE).build());
                break;
            case R.id.color_btn2:
                tv.append(RichUtils.getBuilder("背景黄").addBackgroundColorSpan(Color.YELLOW).build());
                break;
            case R.id.font_btn:
                tv.append(RichUtils.getBuilder("36号字体").addAbsoluteSizeSpan(36).build());
                break;
            case R.id.style_btn:
                tv.append(RichUtils.getBuilder("斜体加粗").addStyleBoldItalicSpan().build());
                break;
            case R.id.strike_btn:
                tv.append(RichUtils.getBuilder("删除线").addStrikethroughSpan().build());
                break;
            case R.id.underline_btn:
                tv.append(RichUtils.getBuilder("下划线").addUnderLineSpan().build());
                break;
            case R.id.btn_relative_size:
                float[] floats = new float[]{1.2f, 1.4f, 1.6f, 1.8f, 1.6f, 1.4f, 1.2f};
                tv.append(RichUtils.getBuilder("万丈高楼平地起").addRelativeSizeSpan(floats).build());
                break;
            case R.id.btn_click:
                tv.setMovementMethod(LinkMovementMethod.getInstance());//否则没有点击响应
                tv.setHighlightColor(getResources().getColor(R.color.colorAccent));//点击时的背景色,没用啊??
                tv.append(RichUtils.getBuilder("点击").addClickableSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        toast(widget.getClass().toString());
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {//可设置下划线和字体颜色
//                        super.updateDrawState(ds);
                        ds.setUnderlineText(true);
                    }
                }).build());
                System.out.println(tv.getText());
                break;
            default:
                break;
        }
    }
}