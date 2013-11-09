package com.yiyuan.player.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yiyuan.player.R;
import com.yiyuan.player.entity.ConcretenessContent;
import com.yiyuan.player.utils.ImageUtils;

public class HomeContentGridAdapter extends BaseAdapter {
	private List<ConcretenessContent> concretenessContents;
	private Context context;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;
	public HomeContentGridAdapter(Context context, List<ConcretenessContent> concretenessContents) {
		this.context = context;
		this.concretenessContents = concretenessContents;
		options = ImageUtils.imageLoader(context, 0);
		imageLoader = ImageLoader.getInstance();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return concretenessContents.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return concretenessContents.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if(concretenessContents.size() > 3) {
			return 0;
		}else {
			return 1;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		int type = getItemViewType(position);
		View view = convertView;
		ViewHolder viewHolder = null;
		if(view == null) {
			viewHolder = new ViewHolder();
			if(type == 0) {
				view = View.inflate(context, R.layout.home_content_grid_item, null);
				viewHolder.imageView = (ImageView) view.findViewById(R.id.home_content_grid_image);
				view.setTag(viewHolder);
			}else {
				view = View.inflate(context, R.layout.home_content_grid_item_other, null);
				viewHolder.imageView = (ImageView) view.findViewById(R.id.home_content_grid_image_other);
				view.setTag(viewHolder);
			}
		}else {
			viewHolder = (ViewHolder) view.getTag();
		}
		imageLoader.displayImage(concretenessContents.get(position).getImg_url(), viewHolder.imageView, options);
		return view;
	}

	private class ViewHolder {
		public ImageView imageView;
	}
}
