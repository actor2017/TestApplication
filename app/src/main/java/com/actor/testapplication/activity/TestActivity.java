package com.actor.testapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.actor.testapplication.R;
import com.actor.testapplication.widget.EaseSidebar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description: Test测试页面
 * Author     : 李大发
 * Date       : 2019/12/30 on 11:38
 */
public class TestActivity extends BaseActivity {

    @BindView(R.id.tv_result)//显示结果
            TextView    tvResult;
    @BindView(R.id.edit_text)
            EditText    editText;
    @BindView(R.id.btn)
            Button      btn;

    @BindView(R.id.list)
            ListView    list;
    @BindView(R.id.sidebar)
            EaseSidebar sidebar;
    @BindView(R.id.floating_header)
            TextView    floatingHeader;
    private List<String> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        setTitle("Test测试页面");
        sidebar.setListView(list);
        for (int i = 0; i < 30; i++) {
            items.add("item " + i);
        }
        list.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends BaseAdapter implements SectionIndexer {

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.ease_row_contact, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.nameView.setText(items.get(position));
            return convertView;
        }

        @Override
        public Object[] getSections() {
            return new Object[0];
        }

        @Override
        public int getPositionForSection(int sectionIndex) {
            return 0;
        }

        @Override
        public int getSectionForPosition(int position) {
            return 0;
        }
    }

    private static class ViewHolder {
        ImageView avatar;
        TextView nameView;
        public ViewHolder(View view) {
            this.avatar = view.findViewById(R.id.iv);
            this.nameView = view.findViewById(R.id.tv);
        }
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
    }
}
