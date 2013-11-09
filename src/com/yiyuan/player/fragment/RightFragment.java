package com.yiyuan.player.fragment;

import java.io.InputStream;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.yiyuan.player.R;
import com.yiyuan.player.entity.RightUI;
import com.yiyuan.player.ui.MainActivity;

/**
 * 主界面 右侧内容
 * 
 * @author lianjie
 * 
 */
public class RightFragment extends YiYuanFragment {

	private static final String URL = "http://app.video.baidu.com/adapp_static/clientconfig/json/nav_4.5.1.json/?appname=videoandroid";
	/**
	 * 直播layout
	 */
	private LinearLayout liveLayout;
	 /**
	  *  电视剧layout
	  */
	private LinearLayout tvPlayLayout;
	
	private MainActivity activity;
	private FragmentManager mFragmentManager;
	
	public static RightFragment newInstance() {
		RightFragment fragment = new RightFragment();
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		activity = (MainActivity) getActivity();
		mFragmentManager = getActivity().getSupportFragmentManager();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.right_container, null);
		initView(view);
		makeRequest();
		return view;
	}

	/**
	 * 初始化view
	 */
	private void initView(View view) {
		liveLayout = (LinearLayout) view.findViewById(R.id.right_container_live);
		liveLayout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction transaction = mFragmentManager.beginTransaction();
				LiveFragment fragment = LiveFragment.newInstance();
				transaction.add(R.id.home_content_framelayout, fragment);
				transaction.commit();
				activity.slidingMenu.showContent();
			}
		});

		tvPlayLayout = (LinearLayout) view
				.findViewById(R.id.right_container_tvplay);
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

	/**
	 * 请求数据
	 */
	private void makeRequest() {
		String json = getFromAssets("right.json");
		Gson gson = new Gson();
		RightUI obj = gson.fromJson(json, RightUI.class);
	}
}
