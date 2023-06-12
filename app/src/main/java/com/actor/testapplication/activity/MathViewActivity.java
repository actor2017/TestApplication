package com.actor.testapplication.activity;

import android.os.Bundle;

import com.actor.myandroidframework.utils.LogUtils;
import com.actor.myandroidframework.widget.webview.BaseWebView;
import com.actor.testapplication.R;
import com.actor.testapplication.databinding.ActivityMathViewBinding;

import io.github.kexanie.library.MathView;

public class MathViewActivity extends BaseActivity<ActivityMathViewBinding> {

    private BaseWebView webView;
    private MathView    mathView;

    private final String MathJye = "<style>\n" +
            ".MathJye {\n" +
            "    border: 0 none;\n" +
            "    direction: ltr;\n" +
            "    line-height: normal;\n" +
            "    display: inline-block;\n" +
            "    float: none;\n" +
            "    font-family: 'Times New Roman', '宋体';\n" +
            "    font-size: 15px;\n" +
            "    font-style: normal;\n" +
            "    font-weight: normal;\n" +
            "    letter-spacing: 1px;\n" +
            "    line-height: normal;\n" +
            "    margin: 0;\n" +
            "    padding: 0;\n" +
            "    text-align: left;\n" +
            "    text-indent: 0;\n" +
            "    text-transform: none;\n" +
            "    white-space: nowrap;\n" +
            "    word-spacing: normal;\n" +
            "    word-wrap: normal;\n" +
            "    -webkit-text-size-adjust: none;\n" +
            "}\n" +
            "\n" +
            ".MathJye div, .MathJye span {\n" +
            "    border: 0 none;\n" +
            "    margin: 0;\n" +
            "    padding: 0;\n" +
            "    line-height: normal;\n" +
            "    text-align: left;\n" +
            "    height: auto;\n" +
            "    _height: auto;\n" +
            "    white-space: normal;\n" +
            "}\n" +
            "\n" +
            ".MathJye table {\n" +
            "    border-collapse: collapse;\n" +
            "    margin: 0;\n" +
            "    padding: 0;\n" +
            "    text-align: center;\n" +
            "    vertical-align: middle;\n" +
            "    line-height: normal;\n" +
            "    font-size: inherit;\n" +
            "    *font-size: 100%;\n" +
            "    _font-size: 100%;\n" +
            "    font-style: normal;\n" +
            "    font-weight: normal;\n" +
            "    border: 0;\n" +
            "    float: none;\n" +
            "    display: inline-block;\n" +
            "    *display: inline;\n" +
            "    zoom: 0;\n" +
            "}\n" +
            "\n" +
            ".MathJye table td {\n" +
            "    padding: 0;\n" +
            "    font-size: inherit;\n" +
            "    line-height: normal;\n" +
            "    white-space: nowrap;\n" +
            "    border: 0 none;\n" +
            "    width: auto;\n" +
            "    _height: auto;\n" +
            "}\n" +
            "\n" +
            ".MathJye_mi {\n" +
            "    font-style: italic;\n" +
            "}\n" +
            "\n" +
            ".selectoption {\n" +
            "    /*vertical-align: middle;*/\n" +
            "    /*font-size: 14px;*/\n" +
            "    float: left;\n" +
            "    width: 100% !important;\n" +
            "}\n" +
            "</style>";

    //3.带图片&<table 公式的形式(第6题)
    private final String value =
            MathJye +
                    "<span class=\"qseq\"></span>\n" +
                    "某学校共有教师200名，其中老年教师25名，中年教师75名，青年教师100名，若采用分层是抽样的方法从这200名教师中抽取40名教师进行座谈，则在青年教师中英抽取的人数为（　　）<br/>\n" +
                    "<table style=\"width:100%\" class=\"ques quesborder\">\n" +
                    "    <tr>\n" +
                    "        <td style=\"width:23%\" class=\"selectoption\"><label class=\"\">A．15人</label></td>\n" +
                    "        <td style=\"width:23%\" class=\"selectoption\"><label class=\" s\">B．20人</label></td>\n" +
                    "        <td style=\"width:23%\" class=\"selectoption\"><label class=\"\">C．25人</label></td>\n" +
                    "        <td style=\"width:23%\" class=\"selectoption\"><label class=\"\">D．30人</label></td>\n" +
                    "    </tr>\n" +
                    "</table>\n" +
                    "\n" +
                    "<!-- 答案解析: --> <br />\n" +
                    "<div>试题分析：先计算抽样比\n" +
                    "    <span class=\"MathJye\" mathtag=\"math\" style=\"whiteSpace:nowrap;wordSpacing:normal;wordWrap:normal\">\n" +
                    "        <table cellpadding=\"-1\" cellspacing=\"-1\" style=\"margin-right:1px\">\n" +
                    "            <tr><td style=\"border-bottom:1px solid black\">40</td></tr>\n" +
                    "            <tr><td>200</td></tr>\n" +
                    "        </table>=\n" +
                    "        <table cellpadding=\"-1\" cellspacing=\"-1\" style=\"margin-right:1px\">\n" +
                    "            <tr><td style=\"border-bottom:1px solid black\">1</td></tr>\n" +
                    "            <tr><td>5</td></tr>\n" +
                    "        </table>\n" +
                    "    </span>，而分层抽样中各层抽取的比例相等，利用此抽样比计算各层所需抽取人数即可．\n" +
                    "</div>\n" +
                    "<div>试题解析：抽样比<span class=\"MathJye\" mathtag=\"math\" style=\"whiteSpace:nowrap;wordSpacing:normal;wordWrap:normal\">\n" +
                    "    <table cellpadding=\"-1\" cellspacing=\"-1\" style=\"margin-right:1px\">\n" +
                    "        <tr><td style=\"border-bottom:1px solid black\">40</td></tr>\n" +
                    "        <tr><td>200</td></tr>\n" +
                    "    </table>=\n" +
                    "    <table cellpadding=\"-1\" cellspacing=\"-1\" style=\"margin-right:1px\">\n" +
                    "        <tr><td style=\"border-bottom:1px solid black\">1</td></tr>\n" +
                    "        <tr><td>5</td></tr>\n" +
                    "    </table>\n" +
                    "</span>，青年教师100名，故在青年教师中应抽取的人数为100×\n" +
                    "    <span class=\"MathJye\" mathtag=\"math\" style=\"whiteSpace:nowrap;wordSpacing:normal;wordWrap:normal\">\n" +
                    "        <table cellpadding=\"-1\" cellspacing=\"-1\" style=\"margin-right:1px\">\n" +
                    "            <tr><td style=\"border-bottom:1px solid black\">1</td></tr>\n" +
                    "            <tr><td>5</td></tr>\n" +
                    "        </table>\n" +
                    "    </span>=20<br>故选B\n" +
                    "</div>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_view);
        webView = viewBinding.webView;
        mathView = viewBinding.mathView;

        webView.loadData(value);

        mathView.config("MathJax.Hub.Config({\n"+
                "  CommonHTML: { linebreaks: { automatic: true } },\n"+
                "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n"+
                "         SVG: { linebreaks: { automatic: true } }\n"+
                "});");

        // TODO: 2023/6/12 load MathJye.css
//        LogUtils.error("loadUrl: MathJye.css");
//        mathView.loadUrl("file:///android_asset/www/MathJye.css");
        LogUtils.error("mathView.setText");
        mathView.setText(value);
    }
}