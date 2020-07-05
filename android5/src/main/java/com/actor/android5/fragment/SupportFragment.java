package com.actor.android5.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actor.android5.R;
import com.actor.base.BaseFragment;

/**
 * 兼容性
 */
public class SupportFragment extends BaseFragment {

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_support, container, false);
	}

	public String getUrl() {
		return "file:///android_asset/support.html";
	}
}
