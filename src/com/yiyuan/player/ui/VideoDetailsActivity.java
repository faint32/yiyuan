package com.yiyuan.player.ui;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yiyuan.player.R;
import com.yiyuan.player.api.YiYuanAPI;
import com.yiyuan.player.entity.VideoDetails;
import com.yiyuan.player.listener.OnErrorListener;
import com.yiyuan.player.utils.ImageUtils;
import com.yiyuan.player.vlc.AudioServiceController;
import com.yiyuan.player.vlc.VLCCallbackTask;

public class VideoDetailsActivity extends YiYuanPlayerActivity {

	private String url = "http://app.video.baidu.com/xqinfo/?worktype=adnative";
	private ImageView videoImage = null;
	private TextView titleText = null;
	private TextView videoNameText = null;
	private TextView videoActorText = null;
	private TextView timeText = null;
	private TextView introText = null;
	private ImageLoader imageLoader = null;
	private Context context = VideoDetailsActivity.this;
	private DisplayImageOptions options = null;
	private Button playerButton = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_details_activity);
		imageLoader = ImageLoader.getInstance();
		options = ImageUtils.imageLoader(context, 0);
		videoImage = (ImageView) findViewById(R.id.video_details_image);
		titleText = (TextView) findViewById(R.id.video_details_title);
		videoNameText = (TextView) findViewById(R.id.video_details_videoname);
		videoActorText = (TextView) findViewById(R.id.video_details_actor);
		timeText = (TextView) findViewById(R.id.video_details_pubtime);
		introText = (TextView) findViewById(R.id.video_details_intro);
		playerButton = (Button) findViewById(R.id.vido_details_videoPlayButton);

		Intent intent = getIntent();
		String worksId = intent.getStringExtra("worksId");
		String worksType = intent.getStringExtra("worksType");
		// http://app.video.baidu.com/xqinfo/?worktype=adnativemovie&id=112568&site=
		url = url + worksType + "&id=" + worksId + "&site=";
		
		mApi.requestGetData(url, VideoDetails.class, new Response.Listener<VideoDetails>() {

			@Override
			public void onResponse(VideoDetails response) {
				// TODO Auto-generated method stub
				initData(response);
			}
		}, new OnErrorListener(context));
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		AudioServiceController.getInstance().bindAudioService(this);
	}
	
	private void initData(final VideoDetails videoDetails) {
		// TODO Auto-generated method stub
		imageLoader.displayImage(videoDetails.getImg_url(), videoImage, options);
		titleText.setText(videoDetails.getTitle());
		videoNameText.setText(videoDetails.getTrunk());
		List<String> actors = videoDetails.getActor();
		String actor = "";
		for (int i = 0; i < actors.size(); i++) {
			actor += actors.get(i) + " ";
		}
		videoActorText.setText("主演 : " + actor);
		timeText.setText("年代 : " + videoDetails.getPubtime());
		introText.setText(videoDetails.getIntro());
		playerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(context, VideoPlayerActivity.class);
////				intent.putExtra("playerurl", videoDetails.getSites().get(0).getSite_url());
//				startActivity(intent);
			      VLCCallbackTask task = new VLCCallbackTask(context) {

			 			@Override
			 			public void run() {
			 				// TODO Auto-generated method stub
			 				AudioServiceController c = AudioServiceController.getInstance();
//			 				c.load("http://data.vod.itc.cn/?new=/209/213/LO8Rik3sTZwj2nDBXV4n46.mp4", false);
//			 				c.load("rtmp://wow4.cnr.cn:80/live/zgzs", false);
			 				c.load(videoDetails.getSites().get(0).getSite_url(), false);
			 			}
			         };
			         task.execute();
			}
		});
	}
}
