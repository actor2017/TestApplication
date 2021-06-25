package com.actor.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.actor.myandroidframework.widget.QuickSearchBar;
import com.actor.testapplication.R;
import com.actor.testapplication.adapter.BirthdayAdapter;
import com.actor.testapplication.bean.BirthItem;
import com.actor.testapplication.utils.GreenDaoUtils12;
import com.actor.testapplication.widget.ItemAddMinusLayout;
import com.greendao.gen.BirthItemDao;

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

    private boolean hasShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);

        setTitle("自定义View");
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
        //长按事件
        itemAddMinus.getTextViewItem().setOnLongClickListener(v -> {
            if (!hasShow) {
                BirthItemDao dao = GreenDaoUtils12.getDaoSession().getBirthItemDao();
                List<BirthItem> items = GreenDaoUtils12.queryAll(dao);
                recyclerView.setAdapter(new BirthdayAdapter(items));
                hasShow = true;
            }
            return false;
        });
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
