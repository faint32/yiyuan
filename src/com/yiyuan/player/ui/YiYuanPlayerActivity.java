package com.yiyuan.player.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.slidingmenu.lib.app.SlidingFragmentActivity;
import com.yiyuan.player.YiYuanPlayerApplication;
import com.yiyuan.player.api.YiYuanAPI;
import com.yiyuan.player.http.YiYuanPlayer;

public class YiYuanPlayerActivity extends FragmentActivity {
	protected YiYuanPlayer yiYuanPlayer = null;
	public YiYuanPlayerApplication application;
	public YiYuanAPI mApi;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		yiYuanPlayer = new YiYuanPlayer();
		application = (YiYuanPlayerApplication) getApplication();
		application.activities.add(this);
		mApi = YiYuanAPI.newInstance(this);
	}
}
