package com.yiyuan.player.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yiyuan.player.R;
import com.yiyuan.player.entity.Videos;
import com.yiyuan.player.utils.ImageUtils;

/**
 * 电影更多适配器
 * @author lianjie
 *
 */
public class MovieMoreAdapter extends BaseAdapter {
	
	private Context context;
	private List<Videos> videos;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;
	private String worksType;
	
	public MovieMoreAdapter(Context context, List<Videos> videos, String worksType) {
		this.context = context;
		this.videos = videos;
		this.worksType = worksType;
		options = ImageUtils.imageLoader(context, 0);
		imageLoader = ImageLoader.getInstance();
	}

	public void addVideoItem(List<Videos> videos) {
		this.videos.clear();
		for(Videos video : videos) {
			this.videos.add(video);
		}
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return videos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return videos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder = null;
		if(view == null) {
			holder = new ViewHolder();
			view = View.inflate(context, R.layout.movie_more_item, null);
			holder.imageView = (ImageView) view.findViewById(R.id.movie_more_item_image);
			holder.textReta = (TextView) view.findViewById(R.id.movie_more_item_rate_text);
			holder.movieName = (TextView) view.findViewById(R.id.movie_more_item_name);
			view.setTag(holder);
		}else {
			holder = (ViewHolder) view.getTag();
		}
		final Videos video = videos.get(position);
		imageLoader.displayImage(video.getImg_url(), holder.imageView, options);
		if("tvplay".equals(worksType)) {
			holder.textReta.setText(video.getUpdate());
		}else {
			holder.textReta.setText(video.getRating()+"分");
		}
		holder.movieName.setText(video.getTitle());
		return view;
	}
	
	private class ViewHolder {
		public ImageView imageView;
		public TextView textReta;
		public TextView movieName;
	}

}
