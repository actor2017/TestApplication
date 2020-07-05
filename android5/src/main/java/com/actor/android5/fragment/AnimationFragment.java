package com.actor.android5.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;

import com.actor.android5.R;
import com.actor.android5.activity.TransitionsActivity;
import com.actor.base.BaseFragment;

/**
 * 全新动画
 */
public class AnimationFragment extends BaseFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animation, container, false);
        view.findViewById(R.id.btn1).setOnClickListener(this);
        view.findViewById(R.id.bt_circle).setOnClickListener(this);
        view.findViewById(R.id.btn2).setOnClickListener(this);
        view.findViewById(R.id.ll_btn).setOnClickListener(this);
        view.findViewById(R.id.curved).setOnClickListener(this);
        view.findViewById(R.id.vector_anim).setOnClickListener(this);
        view.findViewById(R.id.transitions).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn1 || id == R.id.bt_circle || id == R.id.ll_btn) {/**
         * View view        你要进行圆形缩放的view
         * int centerX      开始缩放点的x坐标
         * int centerY      开始缩放点的y坐标
         * float startRadius开始缩放时的半径
         * float endRadius  结束缩放后的半径
         */
            Animator animator1 = ViewAnimationUtils.createCircularReveal(v, v.getWidth() / 2, v.getHeight() / 2,
                    v.getWidth(), 0);
            animator1.setInterpolator(new LinearInterpolator());//线性匀速改变
            animator1.setDuration(1000);
            animator1.start();
        } else if (id == R.id.btn2) {
            Animator animator2 = ViewAnimationUtils.createCircularReveal(v, 0, v.getHeight(),//开始坐标
                    0, (float) Math.hypot(v.getWidth(), v.getHeight()));//结束位置
            animator2.setDuration(1000);
            animator2.start();
        } else if (id == R.id.curved) {   //曲线动画
            curved(v);
        } else if (id == R.id.vector_anim) {//矢量图动画
            Drawable drawable = v.getBackground();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            }
        } else if (id == R.id.transitions) { //转场动画
            Intent intent = new Intent(getActivity(), TransitionsActivity.class);
            getActivity().startActivity(intent);
        }
    }

    private void curved(View view) {
        Path path = new Path();
        //path.moveTo(view.getX(), view.getY());
        //path.lineTo(200, 200);
        //path.lineTo(600, 600);
        //path.arcTo(r,0,180,false);
        //path.rQuadTo(500,300,300,700);
        //path.cubicTo(100,100,500,300,300,700);
        //path.rCubicTo(100,100,500,300,300,700);
        //path.addArc(100,100,1000,700,-180,180);
        //path.cubicTo(100,100,200,200,300,300);
        path.moveTo(100, 100);
        path.quadTo(1000, 300, 300, 700);

        ObjectAnimator mAnimator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);

        Path p = new Path();
        p.lineTo(0.6f, 0.9f);
        p.lineTo(0.75f, 0.2f);
        p.lineTo(1f, 1f);
        mAnimator.setInterpolator(new PathInterpolator(p));
        mAnimator.setDuration(3000);
        mAnimator.start();
    }

    public String getUrl() {
        return "file:///android_asset/animation.html";
    }
}
