package com.actor.testapplication.bean;

import android.view.View;

import com.actor.testapplication.R;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Description: HomeFragment主页多级可收缩的Adapter
 * 环境:
 * implementation 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.50'
 *
 * Author     : 李大发
 * Date       : 2019/11/21 on 16:04
 *
 * @version 1.0
 */
public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_PERSON = 2;

    public ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);
        addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv1);
        addItemType(TYPE_PERSON, R.layout.item_homemenu_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                final Level0Item lv0 = (Level0Item) item;
                helper.setText(R.id.tv, lv0.title + ", " + lv0.subTitle)
                        .setImageResource(R.id.iv, R.drawable.logo);
                helper.itemView.setOnClickListener(v -> {
                    int pos = helper.getAdapterPosition();
                    if (lv0.isExpanded()) {
                        collapse(pos);
                    } else {
                        expand(pos);
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final Level1Item lv1 = (Level1Item) item;
                helper.setText(R.id.tv, lv1.title + ", " + lv1.subTitle)
                        .setImageResource(R.id.iv, R.drawable.icon_add_gray_cdcdcd);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = helper.getAdapterPosition();
                        if (lv1.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }
                    }
                });
                break;
            case TYPE_PERSON:
                final Person person = (Person) item;
                helper.setText(R.id.tv, person.name + ", " + person.age)
                        .setImageResource(R.id.iv, R.drawable.icon_minus_gray_cdcdcd);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = helper.getAdapterPosition();
                        ToastUtils.showShort(String.valueOf(pos));
//                        // 先获取到当前 item 的父 positon，再移除自己
//                        int positionAtAll = getParentPositionInAll(pos);
//                        remove(pos);
//                        if (positionAtAll != -1) {
//                            IExpandable multiItemEntity = (IExpandable) getData().get(positionAtAll);
//                            if (!hasSubItems(multiItemEntity)) {
//                                remove(positionAtAll);
//                            }
//                        }
                    }
                });
                break;
        }
    }
}
