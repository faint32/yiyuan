package com.yiyuan.player.fragment;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yiyuan.player.R;
import com.yiyuan.player.adapter.EpisodeTableGridAdapter;
import com.yiyuan.player.api.YiYuanAPI;
import com.yiyuan.player.entity.PlayerAddress;
import com.yiyuan.player.entity.VideoDetails;
import com.yiyuan.player.listener.OnErrorListener;
import com.yiyuan.player.utils.ImageUtils;
import com.yiyuan.player.utils.Log;
import com.yiyuan.player.vlc.AudioServiceController;
import com.yiyuan.player.vlc.VLCCallbackTask;

/**
 * 视频详情
 * @author lianjie
 *
 */
public class VideoDetailsFragment extends YiYuanFragment {

	private static final String tag = VideoDetailsFragment.class.getSimpleName();
	private String url = "http://app.video.baidu.com/xqinfo/?worktype=adnative";
	private String playUrl = "http://gate.baidu.com/tc?m=8&video_app=1&ajax=1&src=";
	/**
	 * 视频缩略图
	 */
	private ImageView videoImage = null;
	/**
	 * 电影或电视剧名字
	 */
	private TextView titleText = null;
	/**
	 * 视频评分或者电视剧更新到第几集
	 */
	private TextView rateText = null;
//	private TextView videoNameText = null;
	/**
	 * 电影或电视剧主演 
	 */
	private TextView videoActorText = null;
	/**
	 * 电影或电视剧年代
	 */
	private TextView timeText = null;
	/**
	 * 播放layout
	 */
	private LinearLayout playLayout = null;
	/**
	 * 离线下载layout
	 */
	private LinearLayout downloadLayout;
	/**
	 * 播放来源layout
	 */
	private LinearLayout playSourceLayout;
	/**
	 * 播放来源图标
	 */
	private ImageView sourceImage;
	/**
	 * 收藏图标
	 */
	private ImageView collectImage;
	/**
	 * 分享图标
	 */
	private ImageView shareImage;
	/**
	 * 视频简介中的导演
	 */
	private TextView detailDirectText;
	/**
	 * 视频简介中的主演
	 */
	private TextView detailActorText;
	/**
	 * 视频简介中的类型
	 */
	private TextView detailTypeText;
	/**
	 * 视频简介中的视频描述
	 */
	private TextView detailDescriptionText;
	/**
	 * 剧集列表文字，如果是电影则隐藏
	 */
	private TextView episodeTableText;
	/**
	 * 剧集列表
	 */
	private GridView episodeTableGrid;
	/**
	 * 视频简介文字
	 */
	private TextView videoDetailText;
	/**
	 * 视频简介layout
	 */
	private LinearLayout videoDetailLayout;
	private ImageButton backButton;
	private ImageLoader imageLoader = null;
	private Context context;
	private DisplayImageOptions options = null;
	private String worksId;
	private String worksType;
	private FragmentManager mFragmentManager;
	
	public static VideoDetailsFragment newInstance(String worksId, String worksType) {
		VideoDetailsFragment fragment = new VideoDetailsFragment();
		Bundle bundle = new Bundle();
		bundle.putString("worksId", worksId);
		bundle.putString("worksType", worksType);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if(bundle != null) {
			worksId = bundle.getString("worksId");
			worksType = bundle.getString("worksType");
		}
		context = getActivity();
		mFragmentManager = getActivity().getSupportFragmentManager();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(context, R.layout.video_detail_header_layout, null);
		imageLoader = ImageLoader.getInstance();
		options = ImageUtils.imageLoader(context, 0);
		backButton = (ImageButton) view.findViewById(R.id.btn_back);
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFragmentManager.beginTransaction().detach(VideoDetailsFragment.this).commit();
			}
		});
		
		videoImage = (ImageView) view.findViewById(R.id.video_details_image);
		rateText = (TextView) view.findViewById(R.id.video_rate_text);
		
		titleText = (TextView) view.findViewById(R.id.video_details_title);
		playLayout = (LinearLayout) view.findViewById(R.id.video_detail_play);
		downloadLayout = (LinearLayout) view.findViewById(R.id.video_detail_download);
		playSourceLayout = (LinearLayout) view.findViewById(R.id.site_arrow_area);
		
		sourceImage = (ImageView) view.findViewById(R.id.site_icon);
		collectImage = (ImageView) view.findViewById(R.id.video_detail_collect);
		shareImage = (ImageView) view.findViewById(R.id.video_detail_share);
		
		videoActorText = (TextView) view.findViewById(R.id.video_details_actor);
		timeText = (TextView) view.findViewById(R.id.video_details_time);
		
		detailDirectText = (TextView) view.findViewById(R.id.video_detail_direct_text);
		detailActorText = (TextView) view.findViewById(R.id.video_detail_actor_text);
		detailTypeText = (TextView) view.findViewById(R.id.video_detail_type_text);
		detailDescriptionText = (TextView) view.findViewById(R.id.video_detail_description_text);
		episodeTableText = (TextView) view.findViewById(R.id.episode_table_text);
		if("movie".equals(worksType) || "micromovie".equals(worksType)) {
			episodeTableText.setVisibility(View.GONE);
		}
		
		videoDetailLayout = (LinearLayout) view.findViewById(R.id.video_detail_episode_layout);
		episodeTableGrid = (GridView) view.findViewById(R.id.episode_table_grid);
		videoDetailText = (TextView) view.findViewById(R.id.video_detail_text);
		// http://app.video.baidu.com/xqinfo/?worktype=adnativemovie&id=112568&site=
//		String str2 = "http://gate.baidu.com/tc?m=8&video_app=1&ajax=1&src=" + str1;
//
//		http://gate.baidu.com/tc?m=8&video_app=1&ajax=1&src=http://v.pps.tv/play_35H31I.html#from_baidu
		url = url + worksType + "&id=" + worksId + "&site=";
		Log.d(tag, url);
		
		mApi.requestGetData(url, VideoDetails.class, new Response.Listener<VideoDetails>() {

			@Override
			public void onResponse(VideoDetails response) {
				// TODO Auto-generated method stub
				initData(response);
			}
		}, new OnErrorListener(context));
		return view;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		AudioServiceController.getInstance().bindAudioService(context);
		super.onStart();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mImageLoader.stop();
		mImageLoader.clearMemoryCache();
		mApi.stopAllRequest();
	}
	
	private void initData(final VideoDetails videoDetails) {
		// TODO Auto-generated method stub
		if(videoDetails != null) {
			imageLoader.displayImage(videoDetails.getImg_url(), videoImage, options);
			rateText.setText(videoDetails.getRating()+" 分");
			titleText.setText(videoDetails.getTitle());
			List<String> actors = videoDetails.getActor();
			String actor = "";
			for (int i = 0; i < actors.size(); i++) {
				actor += actors.get(i) + " ";
			}
			videoActorText.setText("主演 : " + actor);
			timeText.setText("年代 : " + videoDetails.getPubtime());
			
			String director = "";
			for (int i = 0; i < videoDetails.getDirector().size(); i++) {
				director += videoDetails.getDirector().get(i)+" ";
			}
			detailDirectText.setText("导演 : "+director);
			detailActorText.setText("主演 : " + actor);
			String type = "";
			for (int i = 0; i < videoDetails.getType().size(); i++) {
				type += videoDetails.getType().get(i)+" ";
			}
			detailTypeText.setText("类型 : "+type);
			detailDescriptionText.setText(videoDetails.getIntro());
			if(videoDetails.getSites() != null && videoDetails.getSites().size() > 0) {
				imageLoader.displayImage(videoDetails.getSites().get(0).getSite_logo(), sourceImage, options);
			}
			
			episodeTableText.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					videoDetailLayout.setVisibility(View.GONE);
					episodeTableGrid.setVisibility(View.VISIBLE);
				}
			});
			
			videoDetailText.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					episodeTableGrid.setVisibility(View.GONE);
					videoDetailLayout.setVisibility(View.VISIBLE);
				}
			});
			
			if(videoDetails.getCur_episode() > 0 || videoDetails.getMax_episode() > 0) {
				episodeTableGrid.setAdapter(new EpisodeTableGridAdapter(getActivity(), videoDetails.getCur_episode()));
			}
			
			if(videoDetails.getSites() != null && videoDetails.getSites().size() > 0) {
				if(videoDetails.getSites().get(0) != null) {
					//拼装获取播放地址url
					playUrl = playUrl + videoDetails.getSites().get(0).getSite_url();
				}
			}
		}
		Log.d(tag, playUrl);
		mApi.requestGetData(playUrl, PlayerAddress.class, new Response.Listener<PlayerAddress>() {

			@Override
			public void onResponse(PlayerAddress response) {
				// TODO Auto-generated method stub
				String url = response.getVideo_source_url();
				setPlayerOnClickListener(url);
			}
		}, new OnErrorListener(context));
	}
	
	/**
	 * 播放按钮点击事件，开始播放
	 * @param videoDetails
	 */
	private void setPlayerOnClickListener(final String playerUrl) {
		playLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			      VLCCallbackTask task = new VLCCallbackTask(context) {

			 			@Override
			 			public void run() {
			 				System.out.println(playerUrl);
			 				AudioServiceController c = AudioServiceController.getInstance();
			 				c.load(playerUrl, false);
//			 				c.load("http://data.vod.itc.cn/?new=/209/213/LO8Rik3sTZwj2nDBXV4n46.mp4", false);
			 			}
			         };
			         task.execute();
			}
		});
	}
}
