package com.yiyuan.player.adapter;

import java.util.List;

import com.yiyuan.player.R;
import com.yiyuan.player.entity.CCTV;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class LiveFragmentAdapter extends BaseAdapter {

	private List<CCTV> cctvs;
	private Context context;
	
	public LiveFragmentAdapter(Context context, List<CCTV> cctvs) {
		this.context = context;
		this.cctvs = cctvs;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cctvs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return cctvs.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = View.inflate(context, R.layout.live_grid_item, null);
		return view;
	}

}
