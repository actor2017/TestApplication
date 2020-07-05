package com.actor.android5.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actor.android5.R;
import com.actor.base.BaseFragment;

/**
 * 侧边栏菜单
 */
public class DrawerFragment extends BaseFragment {

    private OnDrawerItemSelectedListener mListener;


    private ListView mDrawerListView;

    private int mCurrentSelectedPosition = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectItem(mCurrentSelectedPosition);
    }

    public void setOnDrawerItemSelectedListener(OnDrawerItemSelectedListener listener) {
        mListener = listener;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mDrawerListView = (ListView) inflater.inflate(R.layout.fragment_drawer, container, false);

        //设置侧边栏菜单点击条目监听
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);

        return mDrawerListView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //设置侧边栏菜单内容
        ArrayAdapter adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_activated_1);

        //从strings文件中获取菜单内容
        Object[] array = getActivity()
                .getResources()
                .getStringArray(R.array.drawer_items);
        //给适配器设置数据
        adapter.addAll(array);
        mDrawerListView.setDividerHeight(2);


        //给抽屉菜单设置数组适配器
        mDrawerListView.setAdapter(adapter);
    }

    /**
     * 点击菜单条目的回调
     */
    public void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mListener != null) {
            //回调
            mListener.onDrawerItemSelected(position);
        }
    }

    /**
     * 点击菜单条目的监听
     */
    public interface OnDrawerItemSelectedListener {
        void onDrawerItemSelected(int position);
    }
}
