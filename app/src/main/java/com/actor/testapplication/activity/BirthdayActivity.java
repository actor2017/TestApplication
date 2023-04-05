package com.actor.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.actor.myandroidframework.utils.database.GreenDaoUtils;
import com.actor.testapplication.R;
import com.actor.testapplication.adapter.BirthdayAdapter;
import com.actor.testapplication.bean.BirthItem;
import com.actor.testapplication.databinding.ActivityBirthdayBinding;
import com.actor.testapplication.utils.Global;
import com.greendao.gen.BirthItemDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BirthdayActivity extends BaseActivity<ActivityBirthdayBinding> {

    private TextView   tvAdd;
    private SearchView searchView;
    private RecyclerView recyclerView;

    private final BirthItemDao    dao    = GreenDaoUtils.getDaoSession().getBirthItemDao();
    private final List<BirthItem> items  = GreenDaoUtils.queryAll(dao);
    private final List<BirthItem> search = new ArrayList<>(items.size());
    private BirthdayAdapter birthdayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvAdd = viewBinding.tvAdd;
        searchView = viewBinding.searchView;
        recyclerView = viewBinding.recyclerView;
        if (Global.IS_LIYONG_VERSION) {
            tvAdd.setVisibility(View.INVISIBLE);
        }
        //排序
        Collections.sort(items, new Comparator<BirthItem>() {
            @Override
            public int compare(BirthItem o1, BirthItem o2) {
                return (int) (o1.getCoundDownDay() - o2.getCoundDownDay());
            }
        });
        search.clear();
        search.addAll(items);

        //有一个提交按钮
//        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //点击"搜索按钮"时触发该方法
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //当搜索"内容改变"时触发该方法
                if (TextUtils.isEmpty(newText)) {
                    birthdayAdapter.setList(items);
                } else {
                    search.clear();
                    for (BirthItem item : items) {
                        if (item.name.contains(newText)) {
                            search.add(item);
                        }
                    }
                    birthdayAdapter.setList(search);
                }
                return false;
            }
        });
        //adapter会绑定list的引用
        birthdayAdapter = new BirthdayAdapter(search);
        recyclerView.setAdapter(birthdayAdapter);

        setOnClickListeners(R.id.iv_back, R.id.tv_add);
    }

    @Override
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_add:
                startActivityForResult(new Intent(this, BirthdayDetailActivity.class), 1);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            long id = data.getLongExtra(Global.id, BirthItem.UN_KNOWN_I);
            if (id == BirthItem.UN_KNOWN_I) {
                return;
            }
            //新增
            boolean isInsert = data.getBooleanExtra(Global.isInsert, false);
            //删除
            boolean isDelete = data.getBooleanExtra(Global.isDelete, false);
            CharSequence query = searchView.getQuery();
            BirthItem queryItem = GreenDaoUtils.queryUnique(dao, BirthItemDao.Properties.Id.eq(id));
            //如果是删除
            if (isDelete) {
                for (int i = 0; i < items.size(); i++) {
                    BirthItem item = items.get(i);
                    if (item.id.equals(id)) {
                        items.remove(i);
                        break;
                    }
                }
                List<BirthItem> datas = birthdayAdapter.getData();
                for (int i = 0; i < datas.size(); i++) {
                    BirthItem item = datas.get(i);
                    if (item.id.equals(id)) {
                        birthdayAdapter.removeAt(i);
                        break;
                    }
                }
            } else if (isInsert) {
                //如果是新增
                if (queryItem != null) {
                    items.add(0, queryItem);

                    if (TextUtils.isEmpty(query) || (!TextUtils.isEmpty(query) && queryItem.name.contains(query))) {
                        birthdayAdapter.addData(0, queryItem);
                    }
                }
            } else {
                //如果是修改
                for (int i = 0; i < items.size(); i++) {
                    BirthItem item = items.get(i);
                    if (item.id.equals(id)) {
                        items.set(i, queryItem);
                        break;
                    }
                }
                List<BirthItem> datas = birthdayAdapter.getData();
                for (int i = 0; i < datas.size(); i++) {
                    BirthItem item = datas.get(i);
                    if (item.id.equals(id)) {
                        birthdayAdapter.setData(i, queryItem);
                        break;
                    }
                }
            }
        }
    }
}