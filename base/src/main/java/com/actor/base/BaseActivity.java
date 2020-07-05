package com.actor.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.View;

import com.actor.myandroidframework.activity.ActorBaseActivity;
/**
 * Description: 类的描述
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019-9-17 on 15:13
 *
 * @version 1.0
 */
public class BaseActivity extends ActorBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //必须允许Activity可以使用Transition，也可在style里面设置(true).不设置一样的效果
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        //下面的内容要在5.0上才好用,启用转换过程都会更加流畅自然,指定进入和退出的动画可以重叠,进入/退出动画是否可以覆盖别的动画
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);
    }

    /**
     * 和overridePendingTransition类似,设置跳转时候的进入动画和退出动画
     * @param intent
     */
    protected void startActivityCustom(Intent intent, int enterResId, int exitResId) {
        startActivity(intent, ActivityOptionsCompat.makeCustomAnimation(this, enterResId, exitResId).toBundle());
    }

    /**
     * 通过把要进入的Activity通过放大的效果过渡进去,新的Activity从view的中心从无到有慢慢放大的过程
     * @param intent
     * @param startX 示例:source.getWidth();
     * @param width 开始宽度?
     */
    protected void startActivityScaleUp(Intent intent, View source, int startX, int startY, int width, int height) {
        startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(source, startX, startY, width, height).toBundle());
    }

    /**
     * 通过放大一个图片过渡到新的Activity
     * @param intent
     */
    protected void startActivityBitmap(Intent intent, View source, Bitmap thumbnail, int startX, int startY) {
        startActivity(intent, ActivityOptionsCompat.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY).toBundle());
    }

    /**
     * 爆炸,从场景中心移入或移出视图
     * @param intent
     */
    protected void startActivityExplode(Intent intent) {
        Transition transition = new Explode();
        transition.setDuration(500);
        getWindow().setEnterTransition(transition);//设置进场动画
        getWindow().setExitTransition(transition);//设置出场动画
        getWindow().setReturnTransition(transition);
        getWindow().setReenterTransition(transition);//设置再次进场动画
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }

    /**
     * 淡入淡出
     * @param intent
     */
    protected void startActivityFade(Intent intent) {
        Transition transition = new Fade();
        transition.setDuration(500);
        getWindow().setEnterTransition(transition);//设置进场动画
        getWindow().setExitTransition(transition);//设置出场动画
        getWindow().setReturnTransition(transition);
        getWindow().setReenterTransition(transition);//设置再次进场动画
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }

    /**
     * 滑动
     * @param intent
     */
    protected void startActivitySlide(Intent intent) {
        Transition transition = new Slide();
        transition.setDuration(500);
        getWindow().setEnterTransition(transition);//设置进场动画
        getWindow().setExitTransition(transition);//设置出场动画
        getWindow().setReturnTransition(transition);
        getWindow().setReenterTransition(transition);//设置再次进场动画
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }

    /**
     * 共享元素
     * @param intent
     * @param views 需要共享哪些view
     */
    protected void startActivityShare(Intent intent, View... views) {
        TransitionSet set = new TransitionSet();
        set.addTransition(new Explode());
        set.addTransition(new Fade());
        set.setDuration(500);
        getWindow().setReturnTransition(set);
        getWindow().setReenterTransition(set);
        getWindow().setEnterTransition(set);
        getWindow().setExitTransition(set);

        Pair[] pairs = new Pair[views.length];
        for (int i = 0; i < views.length; i++) {
            views[i].setTransitionName(String.valueOf(views[i].getId()));
            pairs[i] = Pair.create(views[i], views[i].getTransitionName());
        }
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeTransform());
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTarget("bt4");
        transitionSet.setDuration(500);
        getWindow().setSharedElementEnterTransition(transitionSet);
        getWindow().setSharedElementExitTransition(transitionSet);
        getWindow().setSharedElementReturnTransition(transitionSet);
        getWindow().setSharedElementReenterTransition(transitionSet);

        //只分享一个view可以这样写
//      ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, bt4, bt4.getTransitionName());
        //分享多个view
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        startActivity(intent, options.toBundle());
    }
}
