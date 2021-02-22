package com.actor.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.actor.myandroidframework.widget.QuickSearchBar;
import com.actor.testapplication.R;
import com.actor.testapplication.widget.ItemAddMinusLayout;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomViewActivity extends BaseActivity {

    @BindView(R.id.item_add_minus)
    ItemAddMinusLayout itemAddMinus;
    @BindView(R.id.recycler_view)
    RecyclerView       recyclerView;
    @BindView(R.id.quick_search_bar)
    QuickSearchBar     quickSearchBar;
    @BindView(R.id.tv_letter)
    TextView           tvLetter;

    private List<BirthItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);

        setTitle("自定义View");
        items.add(new BirthItem("李小俊", "1971-04-18"));//阳历
        items.add(new BirthItem("李大江", "2001-05-04"));
        items.add(new BirthItem("陈正祥", "1973-05-16"));
        items.add(new BirthItem("张熊洋", "1988-07-01"));
        items.add(new BirthItem("娄经会", "1994-06-29"));
        items.add(new BirthItem("叶启晓", "1992-06-24"));
        items.add(new BirthItem("江文文", "1994-07-30"));
        items.add(new BirthItem("罗太珍", "1936-08-12"));
        items.add(new BirthItem("朱海龙", "1986-08-12"));
        items.add(new BirthItem("李世万", TimeUtils.string2Date("07-07", "MM-dd")));//阴历
        items.add(new BirthItem("李淼淼", "2006-08-14"));
        items.add(new BirthItem("文雅", "1986-09-15"));
        items.add(new BirthItem("罗婷婷", "1992-08-28"));
        items.add(new BirthItem("雷鸣芳", TimeUtils.string2Date("08-08", "MM-dd")));
        items.add(new BirthItem("何敏杰", "1994-09-27"));
        items.add(new BirthItem("黄利群", TimeUtils.string2Date("08-20", "MM-dd")));
        items.add(new BirthItem("李大兴", "1993-10-10"));
        items.add(new BirthItem("程琳琳", "1987-10-25"));
        items.add(new BirthItem("李才敏", "1990-10-27"));
        items.add(new BirthItem("罗庆欢", "1992-10-07"));
        items.add(new BirthItem("李焱焱", "1999-11-24"));
        items.add(new BirthItem("周茂辉", "1978-12-11"));
        items.add(new BirthItem("唐小玲(唐柳)", "1989-12-11"));
        items.add(new BirthItem("李雪曼", "1992-12-11"));
        items.add(new BirthItem("李SB祭日", TimeUtils.string2Date("12-01", "MM-dd")));
        items.add(new BirthItem("傅静香", "1988-02-04"));
        items.add(new BirthItem("王大同", "1937-02-08"));
        items.add(new BirthItem("李国才", "1963-01-24"));
        items.add(new BirthItem("张飞健", "1994-03-07"));
        items.add(new BirthItem("罗礼", "1993-02-20"));
        items.add(new BirthItem("李琦琳", "1990-03-13"));
        items.add(new BirthItem("娄长梅", "1990-03-02"));
        items.add(new BirthItem("霍之相", "1966-02-26"));
        items.add(new BirthItem("赵幸", "1989-03-21"));
        items.add(new BirthItem("罗庆芝", "1994-04-09"));
        items.add(new BirthItem("赵莉娜", "1992-04-01"));
        items.add(new BirthItem("梁隆燕", "1993-04-04"));

        recyclerView.setAdapter(new MyAdapter());
        quickSearchBar.setOnLetterChangedListener(recyclerView, new QuickSearchBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                tvLetter.setVisibility(View.VISIBLE);
                tvLetter.setText(letter);
            }

            @Override
            public void onActionUp() {
                tvLetter.setVisibility(View.GONE);
            }
        });
    }

    private class MyAdapter extends BaseQuickAdapter<BirthItem, BaseViewHolder> {

        public MyAdapter() {
            super(R.layout.item_birthday, items);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, BirthItem item) {
            helper.setText(R.id.tv_name, item.name)
                    .setText(R.id.tv_age, "年龄: " + item.age)
                    .setText(R.id.tv_birthday, item.birthDay == null ?
                            TimeUtils.date2String(item.lunarCalendar, "MM-dd") : item.birthDay);
        }
    }

    //生日Item
    private class BirthItem {
        private String name;
        private int age;
        private int coundDownDay;//生日倒数天数
        private String birthDay;//当年阳历
        private Date lunarCalendar;//阴历
        private String chineseZodiac;//生肖
        private String constellation;//星座

        public BirthItem(String name, String birthDay) {
            this.name = name;
            this.birthDay = birthDay;
        }

        public BirthItem(String name, Date lunarCalendar) {
            this.name = name;
            this.lunarCalendar = lunarCalendar;
        }
    }

    @OnClick(R.id.btn_drag_layout)
    public void onViewClicked(View view) {
        switch (view.getId()) {
        case R.id.btn_drag_layout:
            startActivity(new Intent(this, DragLayoutActivity.class));
            break;
        default:
            break;
        }
    }
}
