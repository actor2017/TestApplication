package com.actor.android5.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.actor.android5.R;
import com.actor.android5.activity.WidgetActivity;
import com.actor.base.BaseFragment;

public class WidgetFragment extends BaseFragment implements View.OnClickListener {
    Button bt1,bt2,bt3,bt4,bt5,bt6;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_widget, container, false);

        bt1 = (Button) view.findViewById(R.id.bt1);
        bt2 = (Button) view.findViewById(R.id.bt2);
        bt3 = (Button) view.findViewById(R.id.bt3);
        bt4 = (Button) view.findViewById(R.id.bt4);
        bt5 = (Button) view.findViewById(R.id.bt5);
        bt6 = (Button) view.findViewById(R.id.bt6);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);

        return view;
    }

    public String getUrl() {
		return "file:///android_asset/widget.html";
	}

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), WidgetActivity.class);
        int id = v.getId();
        if (id == R.id.bt1) {
            intent.putExtra("orientation", WidgetActivity.LIST_V);
        } else if (id == R.id.bt2) {
            intent.putExtra("orientation", WidgetActivity.LIST_H);
        } else if (id == R.id.bt3) {
            intent.putExtra("orientation", WidgetActivity.GRID_V);
        } else if (id == R.id.bt4) {
            intent.putExtra("orientation", WidgetActivity.GRID_H);
        } else if (id == R.id.bt5) {
            intent.putExtra("orientation", WidgetActivity.STAGGERED_GRID_V);
        } else if (id == R.id.bt6) {
            intent.putExtra("orientation", WidgetActivity.STAGGERED_GRID_H);
        }

        getActivity().startActivity(intent);
    }

}
