package com.yiyuan.player.fragment;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Response;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.yiyuan.player.R;
import com.yiyuan.player.adapter.MovieMoreAdapter;
import com.yiyuan.player.entity.MovieMore;
import com.yiyuan.player.entity.VideoList;
import com.yiyuan.player.entity.Videos;
import com.yiyuan.player.listener.OnErrorListener;

/**
 * 电影更多界面
 * @author lianjie
 *
 */
public class MovieMoreFragment extends YiYuanFragment {
	private String movieMoreUrl = "";
	private PullToRefreshGridView mPullRefreshGridView;
	private FragmentManager mFragmentManager;
	private String worksType;
	private int end = 18;
	private MovieMoreAdapter adapter;
	/**
	 * 返回按钮
	 */
	private ImageButton backButton;
	/**
	 * 顶部文字
	 */
	private TextView headText;
	private Context context;
	
	public static MovieMoreFragment newInstance(String worksType) {
		MovieMoreFragment fragment = new MovieMoreFragment();
		Bundle bundle = new Bundle();
		bundle.putString("worksType", worksType);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle bundle = getArguments();
		if(bundle != null) {
			worksType = bundle.getString("worksType");
		}
		context = getActivity();
		mFragmentManager = getActivity().getSupportFragmentManager();
		
		/**更多url*/
		movieMoreUrl = "http://app.video.baidu.com/adnative"+worksType+"/?type=&area=&start=&beg=0&end="+end+"&version=3.6.0";
		System.out.println(movieMoreUrl);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.movie_more, null);
		mPullRefreshGridView = (PullToRefreshGridView) view.findViewById(R.id.pull_refresh_grid);
		backButton = (ImageButton) view.findViewById(R.id.btn_back);
		headText = (TextView) view.findViewById(R.id.header_text);
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mFragmentManager.beginTransaction().detach(MovieMoreFragment.this).commit();
			}
		});
		mPullRefreshGridView.setMode(Mode.BOTH);
		mPullRefreshGridView.setOnRefreshListener(listener);
		makeRequest();
		return view;
	}
	
	OnRefreshListener2<GridView> listener = new OnRefreshListener2<GridView>() {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
			end = 18;
			movieMoreUrl = "http://app.video.baidu.com/adnative"+worksType+"/?type=&area=&start=&beg=0&end="+end+"&version=3.6.0";
			mApi.requestGetData(movieMoreUrl, MovieMore.class, new Response.Listener<MovieMore>() {

				@Override
				public void onResponse(MovieMore movieMore) {
					if(movieMore != null && movieMore.getVideo_list() != null) {
						VideoList videoList = movieMore.getVideo_list();
						if(videoList != null) {
							List<Videos> videos = videoList.getVideos();
							if(videos != null) {
								if(adapter != null) {
									adapter.notifyDataSetChanged();
								}
							}
						}
					}
					mPullRefreshGridView.onRefreshComplete();
				}
			}, new OnErrorListener(context));
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
			end += 18;
			movieMoreUrl = "http://app.video.baidu.com/adnative"+worksType+"/?type=&area=&start=&beg=0&end="+end+"&version=3.6.0";
			mApi.requestGetData(movieMoreUrl, MovieMore.class, new Response.Listener<MovieMore>() {

				@Override
				public void onResponse(MovieMore movieMore) {
					if(movieMore != null && movieMore.getVideo_list() != null) {
						VideoList videoList = movieMore.getVideo_list();
						if(videoList != null) {
							List<Videos> videos = videoList.getVideos();
							if(videos != null) {
								adapter.addVideoItem(videos);
								adapter.notifyDataSetChanged();
							}
						}
					}
					mPullRefreshGridView.onRefreshComplete();
				}
			}, new OnErrorListener(context));
		}

	};
	
	/**
	 * 请求数据
	 */
	public void makeRequest() {
		mApi.requestGetData(movieMoreUrl, MovieMore.class, new Response.Listener<MovieMore>() {

			@Override
			public void onResponse(MovieMore movieMore) {
				// TODO Auto-generated method stub
				if(movieMore != null && movieMore.getVideo_list() != null) {
					VideoList videoList = movieMore.getVideo_list();
					if(videoList != null) {
						List<Videos> videos = videoList.getVideos();
						if(videos != null) {
							adapter = new MovieMoreAdapter(getActivity(), videos, worksType);
							mPullRefreshGridView.setAdapter(adapter);
							setGridViewOnClickListener(videos);
						}
					}
				}
			}
		}, new OnErrorListener(context));
	}
	
	private void setGridViewOnClickListener(final List<Videos> videos) {
		mPullRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Videos video = videos.get(position);
				FragmentTransaction transaction = mFragmentManager.beginTransaction();
				String worksId = video.getWorks_id();
				VideoDetailsFragment fragment = VideoDetailsFragment.newInstance(worksId, worksType);
				transaction.add(R.id.home_content_framelayout, fragment);
				transaction.commit();
			}
		});
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mImageLoader.stop();
		mImageLoader.clearMemoryCache();
		mApi.stopAllRequest();
	}
}
