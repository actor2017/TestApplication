package com.actor.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.actor.testapplication.R;
import com.actor.testapplication.databinding.ActivityReferenceBinding;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * description: 引用
 *  经测试, gc & 跳转页面后, weakReference & weakReference.get() 都 != null
 *                         phantomReference.get() === null
 *  另外可参考: {@link com.luck.picture.lib.basic.PictureSelector#mActivity} 使用的是 SoftReference
 *
 * company    : xxx有限公司 http://www.xxx.com/
 * @author    : ldf
 * date       : 2023/5/12 on 13:17
 */
public class ReferenceActivity extends BaseActivity<ActivityReferenceBinding> {

    private TextView                         tv;

    /**
     * 引用队列
     * 引用可以和一个引用队列（ReferenceQueue）联合使用，
     * 如果软引用所引用的对象被垃圾回收，Java虚拟机就会把这个软引用加入到与之关联的引用队列中。
     */
    private ReferenceQueue<ReferenceActivity> referenceQueue;
    //软引用
    private SoftReference<ReferenceActivity> softReference;
    //弱引用
    private WeakReference<ReferenceActivity> weakReference;
    //虚引用
    private PhantomReference<ReferenceActivity> phantomReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("引用测试");
//        referenceQueue = new ReferenceQueue<>();

        softReference = new SoftReference<>(this/*, ReferenceQueue<? super T> q*/);
        weakReference = new WeakReference<>(this/*, ReferenceQueue<? super T> q*/);
        phantomReference = new PhantomReference<>(this, referenceQueue);

        tv = viewBinding.tv;
    }

    @Override
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get:
                ReferenceActivity activity0 = softReference.get();
                ReferenceActivity activity1 = weakReference.get();
                ReferenceActivity activity2 = phantomReference.get();
                tv.setText("");
                tv.append(getStringFormat("软引用 softReference = %s\r\n\n", activity0));
                tv.append(getStringFormat("弱引用 weakReference = %s\r\n\n", activity1));
                tv.append(getStringFormat("虚引用 phantomReference = %s\r\n", activity2));
                break;
            case R.id.btn_gc:
                System.gc();
                break;
            case R.id.btn_jump:
                startActivity(new Intent(this, TestActivity.class));
                break;
            default:
                break;
        }
    }
}