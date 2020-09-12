package com.actor.testapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actor.testapplication.R;
import com.actor.testapplication.widget.BaseNestedScrollView;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description: 类的描述
 *
 * @author : 李大发
 * date       : 2020/6/2 on 10:55
 * @version 1.0
 */
public class NestedScrollViewActivity extends BaseActivity {

    @BindView(R.id.nested_scroll_view)
    BaseNestedScrollView nestedScrollView;
    @BindView(R.id.tv_top_1)
    TextView tvTop1;
    @BindView(R.id.tv_top_2)
    TextView tvTop2;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll_view);
        ButterKnife.bind(this);

        recyclerView.setAdapter(new SimpleTestAdapter());

        View rootView = findViewById(android.R.id.content);

        tvTop2.post(new Runnable() {
            @Override
            public void run() {
                int actionBarHeight = BarUtils.getActionBarHeight();
                int statusBarHeight = BarUtils.getStatusBarHeight();
                int screenHeight = ScreenUtils.getScreenHeight();
                logFormat("statusbarH=%d, actionbarH=%d, screenH=%d", statusBarHeight, actionBarHeight, screenHeight);

                int height1 = tvTop1.getHeight();
                int heightRoot = rootView.getHeight();
                int height2 = tvTop2.getHeight();
                logFormat("rootH=%d, tv1H=%d, tv2H=%d", heightRoot, height1, height2);

                nestedScrollView.setMyScrollHeight(height1);
                int rvNewHeight = heightRoot - height2;

                ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
                layoutParams.height = rvNewHeight;
                recyclerView.setLayoutParams(layoutParams);
            }
        });
    }

    public class SimpleTestAdapter extends RecyclerView.Adapter<TextViewHolder> {

        ArrayList<String> data;

        @Override
        public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TextViewHolder(new TextView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(TextViewHolder holder, int position) {
            TextView tv = (TextView) holder.itemView;
            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100));

            String val = data == null? "TextView " + position : data.get(position);
            tv.setText(val);
        }


        @Override
        public int getItemCount() {
            return data == null ? 30 : data.size();
        }






    }
    static class TextViewHolder extends RecyclerView.ViewHolder{
        public TextViewHolder(View itemView) {
            super(itemView);
        }
    }
}
