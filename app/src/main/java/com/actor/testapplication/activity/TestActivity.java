package com.actor.testapplication.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.actor.myandroidframework.utils.LogUtils;
import com.actor.myandroidframework.utils.ThreadUtils;
import com.actor.testapplication.databinding.ActivityTestBinding;
import com.github.barteksc.pdfviewer.PDFView;

/**
 * Description: Test测试页面
 * Date       : 2019/12/30 on 11:38
 */
public class TestActivity extends BaseActivity<ActivityTestBinding> {

    private TextView    tvResult;//显示结果
    private EditText    editText;
    private PDFView pdfView;
    private Button      btn;

    private static final String PDF_URL = "http://qxdxz.mtwlkj.net:8021/20211223/3fdaf3d05f7a6a416dd35e672588f40c.pdf";
    private static final String DOC_URL = "http://qxdxz.mtwlkj.net:8021/real_topic/20230628/0f8fd59e4603d4bd268da4eede5332fb.doc";
    private static final String DOCX_URL = "http://qxdxz.mtwlkj.net:8021/real_topic/20230628/dace1521f18acca8d5b617b9528833f4.docx";

    private final Handler mainHandler = new Handler(Looper.getMainLooper()/*, @Nullable Callback callback*/) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            LogUtils.error(msg);
        }
    };
    private Handler subHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvResult = viewBinding.tvResult;
        pdfView = viewBinding.pdfView;
        btn = viewBinding.btn;
        setTitle("Test测试页面");

        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 0;
                message.arg1 = 1;
                message.arg2 = 2;
                message.obj = "子线程 -> 主线程: 发消息!";
                mainHandler.sendMessage(message);

                //子线程Handler
                Looper looper = Looper.myLooper();
                if (looper != null) {
                    Looper.prepare();       //初始化Looper
                    subHandler = new Handler(looper) {
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            super.handleMessage(msg);
                            LogUtils.error(msg);
                        }
                    };
                    Looper.loop();          //启动消息循环
                }
            }
        });

        //
//        MyOkHttpUtils.getFile(DOC_URL, null, null, new GetFileCallback(this, GetFileCallback.getFileNameFromUrl(DOC_URL)) {
//            @Override
//            public void onOk(@NonNull File info, int requestId, boolean isRefresh) {
//                LogUtils.error(info.getAbsolutePath());
//                dismissNetWorkLoadingDialog();
//                BasicSet basicSet = new BasicSet(activity,
//                        info.getAbsolutePath(),//word file path
//                        PathUtils.getFilesPathExternalFirst(),//after conver html file storage path
//                        info.getName().concat(".html"));//html fileName
//
//                //Some configuration can be added...
//                //The concrete in BasicSet.class
//                //basicSet.setHtmlBegin(htmlBegin);
//                String htmlSavePath = WordUtils.getInstance(basicSet).word2html();
//
//                //...
//                //Render in a webview
//                webView.loadUrl("file://" + htmlSavePath);
//            }
//        });
    }

    @Override
    public void onViewClicked(View view) {
        Message message = new Message();
        message.what = 3;
        message.arg1 = 4;
        message.arg2 = 5;
        message.obj = "主线程 -> 子线程: 发消息!";
        subHandler.sendMessage(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainHandler.removeCallbacksAndMessages(null);
        if (subHandler != null) subHandler.removeCallbacksAndMessages(null);
    }
}
