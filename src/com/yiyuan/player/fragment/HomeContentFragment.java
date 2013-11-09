package com.yiyuan.player.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.slidingmenu.lib.SlidingMenu;
import com.yiyuan.player.R;
import com.yiyuan.player.adapter.HomeContentListAdapter;
import com.yiyuan.player.adapter.HomeContentPagerAdapter;
import com.yiyuan.player.entity.ConcretenessContent;
import com.yiyuan.player.entity.Home;
import com.yiyuan.player.entity.Slices;
import com.yiyuan.player.listener.OnErrorListener;
import com.yiyuan.player.ui.MainActivity;

/**
 * 主界面内容
 * @author lianjie
 *
 */
public class HomeContentFragment extends YiYuanFragment implements OnClickListener, OnRefreshListener<ListView> {

	private static final String HOME_PAGE = "http://app.video.baidu.com/adnativeindex/?version=3.8";
	private SlidingMenu slidingMenu = null;
	private PullToRefreshListView pullToRefreshListView = null;
	private ViewPager viewPager = null;
	private List<ImageView> imageViews = null;
	private DisplayImageOptions options = null;
	private int currentItem = 0;
	private ScheduledExecutorService scheduledExecutorService = null;
	private ListView listView = null;
	private List<View> roundImageViews = null;
	private LinearLayout mLinearLayout = null;
	private TextView mTextView = null;
	private FragmentManager mFragmentManager;
	private List<Slices> adapterSlices;
	/**
	 * 加载进度progress
	 */
	private RelativeLayout progressLayout;
	
	public static HomeContentFragment newInstance() {
         HomeContentFragment f = new HomeContentFragment();
         return f;
     }
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			viewPager.setCurrentItem(currentItem);
		}
		
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mFragmentManager = getActivity().getSupportFragmentManager();
		adapterSlices = new ArrayList<Slices>();
		imageViews = new ArrayList<ImageView>();
		roundImageViews = new ArrayList<View>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.online_recommend, null);
		MainActivity activity = (MainActivity) getActivity();
		slidingMenu = activity.slidingMenu;
		Button leftButton = (Button) view.findViewById(R.id.left_button);
		Button rightButton = (Button) view.findViewById(R.id.right_button);
		progressLayout = (RelativeLayout)view.findViewById(R.id.progress_container);
		leftButton.setOnClickListener(this);
		rightButton.setOnClickListener(this);
		pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.home_listview);
		pullToRefreshListView.setOnRefreshListener(this);
		listView = pullToRefreshListView.getRefreshableView();
		makeRequest();
		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		System.out.println("onstart");
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 6, TimeUnit.SECONDS);
		super.onStart();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		System.out.println("onresume");
		super.onResume();
		listView.setOnScrollListener(new PauseOnScrollListener(mImageLoader, true, true));
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		scheduledExecutorService.shutdown();
		super.onStop();
	}
	
	@Override
	public void onPause() {
		mImageLoader.stop();
		mApi.stopAllRequest();
		mImageLoader.clearMemoryCache();
		super.onPause();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		System.out.println("ondestroy");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		System.out.println("ondestroyview");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		System.out.println("ondetach");
		super.onDetach();
	}



	private class ScrollTask implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			currentItem = (currentItem + 1) % imageViews.size();
			handler.obtainMessage().sendToTarget();
		}
		
	}
	
	public void makeRequest() {
		mApi.requestGetData(HOME_PAGE, Home.class, new Response.Listener<Home>() {

			@Override
			public void onResponse(Home home) {
				// TODO Auto-generated method stub
				progressLayout.setVisibility(View.GONE);
				  if(home != null) {
	                	List<Slices> slices = home.getSlices();
	                    for(Slices slice : slices) {
	                    	if(!"index_flash".equals(slice.getTag()) || !"index_flash".equals(slice.getType())) {
	                    		adapterSlices.add(slice);
	                    	}
	                    }
	                    pullToRefreshListView.setAdapter(new HomeContentListAdapter(getActivity(),mFragmentManager, adapterSlices));
	                    initViewPager(slices);
	                }
			}
		}, new OnErrorListener(getActivity()));
    }
	

	private void initViewPager(List<Slices> slices) {
		List<ConcretenessContent> contents = new ArrayList<ConcretenessContent>();
		View headView = View.inflate(getActivity(), R.layout.home_content_head, null);
		viewPager = (ViewPager) headView.findViewById(R.id.home_content_pager);
		mLinearLayout = (LinearLayout) headView.findViewById(R.id.home_content_head_linearlayout);
		mTextView = (TextView) headView.findViewById(R.id.home_content_head_text);
		listView.addHeaderView(headView);
        List<ConcretenessContent> circulations = slices.get(0).getHot();
        for (ConcretenessContent concretenessContent : circulations) {
        	if(!"browser".equals(concretenessContent.getWorks_type())) {
        		contents.add(concretenessContent);
        	}
		}
        
        for (int i = 0; i < contents.size(); i++) {
        	ConcretenessContent concretenessContent = circulations.get(i);
    		ImageView imageView = new ImageView(getActivity());
			imageView.setScaleType(ScaleType.FIT_XY);
			mImageLoader.displayImage(concretenessContent.getImg_url(), imageView, options);
			imageViews.add(imageView);
		
			View mImageView = new View(getActivity());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LayoutParams(10, 10));
			params.leftMargin = 2;
			params.rightMargin = 2;
			if(i == 0){
				mImageView.setBackgroundResource(R.drawable.imager_seleter_focused);
			} else{
				mImageView.setBackgroundResource(R.drawable.image_seleter_normal);
			}
			roundImageViews.add(mImageView);
			mLinearLayout.addView(mImageView, params);
		}
        viewPager.setAdapter(new HomeContentPagerAdapter(imageViews));
        viewPager.setOnPageChangeListener(new HomeContentPagerChangeListener(slices.get(0).getHot()));
        viewPager.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("onclick");
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_button:
			slidingMenu.showMenu();
			break;

		case R.id.right_button:
			slidingMenu.showSecondaryMenu();
			break;
			
		default:
			break;
		}
	}
	
	

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		refreshView.onRefreshComplete();
	}
	
	private class HomeContentPagerChangeListener implements OnPageChangeListener{

		private int oldPosition = 0;
		private List<ConcretenessContent> hot;
		public HomeContentPagerChangeListener(List<ConcretenessContent> hot) {
			// TODO Auto-generated constructor stub
			this.hot = hot;
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			currentItem = position;
			mTextView.setText(hot.get(position).getTitle());
			roundImageViews.get(oldPosition).setBackgroundResource(R.drawable.image_seleter_normal);
			roundImageViews.get(position).setBackgroundResource(R.drawable.imager_seleter_focused);
			oldPosition = position;
		}
		
	}
}
