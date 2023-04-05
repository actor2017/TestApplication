package com.actor.testapplication.adapter;

import androidx.annotation.NonNull;

import com.actor.testapplication.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

/**
 * description: 描述
 * company    :
 *
 * @author : ldf
 * date       : 2021/9/19 on 13
 * @version 1.0
 */
public class TextViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TextViewAdapter() {
        super(R.layout.item_textview);
        List<String> data = getData();
        for (int i = 0; i < getItemCount(); i++) {
            data.add("TextView " + i);
        }
        replaceData(data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.tv, item);
    }

    @Override
    public int getItemCount() {
        return 30;
    }
}
