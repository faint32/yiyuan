package com.yiyuan.player.fragment;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yiyuan.player.api.YiYuanAPI;
import com.yiyuan.player.utils.ImageUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class YiYuanFragment extends Fragment {
	
	public YiYuanAPI mApi;
	public ImageLoader mImageLoader;
	public DisplayImageOptions options;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mApi = YiYuanAPI.newInstance(getActivity());
		mImageLoader = ImageLoader.getInstance();
		options = ImageUtils.imageLoader(getActivity(), 0);
	}
}
