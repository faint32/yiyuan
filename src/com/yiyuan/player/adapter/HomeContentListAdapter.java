package com.yiyuan.player.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.yiyuan.player.R;
import com.yiyuan.player.entity.ConcretenessContent;
import com.yiyuan.player.entity.Slices;
import com.yiyuan.player.fragment.MovieMoreFragment;
import com.yiyuan.player.fragment.VideoDetailsFragment;

public class HomeContentListAdapter extends BaseAdapter {
	
	private List<Slices> slices;
	private Context context;
	private FragmentManager manager;
	
	public HomeContentListAdapter(Context context, FragmentManager manager, List<Slices> slices) {
		this.context = context;
		this.manager = manager;
		this.slices = slices;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return slices.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return slices.get(position);
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
		Slices slice = slices.get(position);
		List<ConcretenessContent> concretenessContents = slice.getHot();
		if(concretenessContents.size() > 3) {
			return 0;
		}else {
			return 1;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		View view = convertView;
		ViewHolder mHolder = null;
		if(view == null) {
			mHolder = new ViewHolder();
			if(type == 0) {
				view = View.inflate(context, R.layout.home_content_grid, null);
				mHolder.mGridView = (GridView) view.findViewById(R.id.home_content_grid);
				mHolder.imageMore = (ImageButton) view.findViewById(R.id.label_more);
				mHolder.labelText = (TextView) view.findViewById(R.id.label_text);
				view.setTag(mHolder);
			}else {
				view = View.inflate(context, R.layout.home_content_grid_other, null);
				mHolder.mGridView = (GridView) view.findViewById(R.id.home_content_grid_other);
				mHolder.imageMore = (ImageButton) view.findViewById(R.id.label_more);
				mHolder.labelText = (TextView) view.findViewById(R.id.label_text);
				view.setTag(mHolder);
			}
		}else {
			mHolder = (ViewHolder) view.getTag();
		}
		Slices slice = slices.get(position);
		List<ConcretenessContent> concretenessContents = slice.getHot();
		mHolder.labelText.setText(slice.getName());
		mHolder.mGridView.setAdapter(new HomeContentGridAdapter(context, concretenessContents));
		setGridViewItemClickListener(mHolder.mGridView, concretenessContents);
		
		imageMoreOnClickListener(mHolder.imageMore, slices.get(position).getTag());
		return view;
	}
	
	private class ViewHolder {
		public GridView mGridView;
		public ImageButton imageMore;
		public TextView labelText;
	}

	private void setGridViewItemClickListener(GridView gridView, final List<ConcretenessContent> concretenessContents) {
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ConcretenessContent content = concretenessContents.get(position);
				FragmentTransaction transaction = manager.beginTransaction();
				VideoDetailsFragment fragment = VideoDetailsFragment.newInstance(content.getWorks_id(), content.getWorks_type());
				transaction.add(R.id.home_content_framelayout, fragment);
				transaction.commit();
			}
		});
	}

	private void imageMoreOnClickListener(ImageButton imageMore, final String tag) {
		imageMore.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentTransaction transaction = manager.beginTransaction();
				MovieMoreFragment fragment = MovieMoreFragment.newInstance(tag);
				transaction.add(R.id.home_content_framelayout, fragment);
				transaction.commit();
			}
		});
	}

}
