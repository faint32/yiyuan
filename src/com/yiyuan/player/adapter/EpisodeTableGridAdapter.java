package com.yiyuan.player.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiyuan.player.R;
/**
 * 视频详情里剧集列表适配器
 * @author lianjie
 *
 */
public class EpisodeTableGridAdapter extends BaseAdapter {
	private int size;
	private Context context;
	public EpisodeTableGridAdapter(Context context, int size) {
		this.context = context;
		this.size = size;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = View.inflate(context, R.layout.episode_grid_item, null);
		TextView textView = (TextView) view.findViewById(R.id.episode_several_collect);
		textView.setText("第"+position+"集");
		return view;
	}

}
