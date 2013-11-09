package com.yiyuan.player.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

public class HomeContentPagerAdapter extends PagerAdapter {

	private List<ImageView> imageViews = null;
	
	public HomeContentPagerAdapter(List<ImageView> imageViews) {
		this.imageViews = imageViews;
	}
	
	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager)container).removeView((View)object);
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		((ViewPager)container).addView(imageViews.get(position));
		return imageViews.get(position);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}
	
}
