package com.actor.testapplication.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.actor.base.BaseActivity;
import com.actor.testapplication.R;
import com.actor.testapplication.bean.ExpandableItemAdapter;
import com.actor.testapplication.bean.Level0Item;
import com.actor.testapplication.bean.Level1Item;
import com.actor.testapplication.bean.Person;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpandableItemActivity extends BaseActivity {

    @BindView(R.id.recycler_view_item_menus)
    RecyclerView recyclerView;

    private ExpandableItemAdapter expandableAdapter;
    private List<MultiItemEntity> items2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_item);
        ButterKnife.bind(this);

        setTitle("分组的伸缩栏(ExpandableItemAdapter)");

        for (int i = 0; i < 10; i++) {
            Level0Item level0Item = new Level0Item("第1层" + i, "sub");
            for (int j = 0; j < 5; j++) {
                Level1Item level1Item = new Level1Item("第2层" + j, "上一层:" + i);
                for (int k = 0; k < 10; k++) {
                    level1Item.addSubItem(new Person("上一层: " + j, k));
                }
                level0Item.addSubItem(level1Item);
            }
            items2.add(level0Item);
        }
        expandableAdapter = new ExpandableItemAdapter(items2);
        final GridLayoutManager manager = new GridLayoutManager(activity, 3);
        //设置步长
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return expandableAdapter.getItemViewType(position) == ExpandableItemAdapter.TYPE_PERSON ? 1 : manager.getSpanCount();
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(expandableAdapter);
//        expandableAdapter.expandAll();//全部展开
    }
}
