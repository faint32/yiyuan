package com.yiyuan.player.ui;

import org.videolan.libvlc.IVideoPlayer;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.LibVlcException;

import android.app.Activity;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.yiyuan.player.R;
import com.yiyuan.player.vlc.AudioServiceController;
import com.yiyuan.player.vlc.VLCCallbackTask;
import com.yiyuan.player.vlc.VLCUtil;

public class TestActivity extends Activity implements IVideoPlayer {

	protected static final String TAG = "VideoPlayerActivity";
	private SurfaceView mSurface;
	private SurfaceHolder mSurfaceHolder;
	private LibVLC mLibVLC;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_player);
		
		mSurface = (SurfaceView) findViewById(R.id.player_surface);
		mSurfaceHolder = mSurface.getHolder();
		mSurfaceHolder.addCallback(mSurfaceCallback);
		mSurfaceHolder.setFixedSize(320, 220);//显示的分辨率,不设置为视频默认
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		try {
            mLibVLC = VLCUtil.getLibVlcInstance();
        } catch (LibVlcException e) {
            Log.d(TAG, "LibVLC initialisation failed");
            return;
        }
		
		VLCCallbackTask task = new VLCCallbackTask(this){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				AudioServiceController c = AudioServiceController.getInstance();
				c.load("http://app.video.baidu.com/movieintro/?page=1&id=36743", false);
			}
		};
		task.execute();
	}
	
    /**
     * attach and disattach surface to the lib
     */
    private final SurfaceHolder.Callback mSurfaceCallback = new Callback() {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if(format == PixelFormat.RGBX_8888)
                Log.d(TAG, "Pixel format is RGBX_8888");
            else if(format == PixelFormat.RGB_565)
                Log.d(TAG, "Pixel format is RGB_565");
            else if(format == ImageFormat.YV12)
                Log.d(TAG, "Pixel format is YV12");
            else
                Log.d(TAG, "Pixel format is other/unknown");
            mLibVLC.attachSurface(holder.getSurface(), TestActivity.this, width, height);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mLibVLC.detachSurface();
        }
    };
	
//	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		AudioServiceController.getInstance().bindAudioService(this);
//		VLCCallbackTask task = new VLCCallbackTask(this){
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				AudioServiceController c = AudioServiceController.getInstance();
//				c.load("http://app.video.baidu.com/movieintro/?page=1&id=36743", false);
//			}
//		};
//		task.execute();
//	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AudioServiceController.getInstance().unbindAudioService(this);
	}

	@Override
	public void setSurfaceSize(int width, int height, int visible_width,
			int visible_height, int sar_num, int sar_den) {
		// TODO Auto-generated method stub
		
	}
}
