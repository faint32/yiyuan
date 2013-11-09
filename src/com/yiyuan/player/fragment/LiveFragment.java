package com.yiyuan.player.fragment;

import java.io.InputStream;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.yiyuan.player.R;
import com.yiyuan.player.adapter.LiveFragmentAdapter;
import com.yiyuan.player.entity.ListCCTV;

/**
 * 直播fragment
 * @author lenovo
 *
 */
public class LiveFragment extends YiYuanFragment {

	public static LiveFragment newInstance() {
		LiveFragment fragment = new LiveFragment();
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.live_container, null);
		makeRequest(view);
		return view;
		
	}
	
	private void makeRequest(View view) {
		String json = getFromAssets("cctv.json");
		Gson gson = new Gson();
		ListCCTV cctvs = gson.fromJson(json, ListCCTV.class);
		PullToRefreshGridView mGridView = (PullToRefreshGridView) view.findViewById(R.id.live_pull_refresh_grid);
		mGridView.setAdapter(new LiveFragmentAdapter(getActivity(),cctvs.getCctvs()));
	}

	// 从assets 文件夹中获取文件并读取数据
	public String getFromAssets(String fileName) {
		String result = "";
		try {
			InputStream in = getResources().getAssets().open(fileName);
			int lenght = in.available();
			byte[] buffer = new byte[lenght];
			in.read(buffer);
			result = new String(buffer, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
