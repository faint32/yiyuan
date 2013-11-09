package com.yiyuan.player.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;
import com.yiyuan.player.R;
import com.yiyuan.player.YiYuanPlayerApplication;
import com.yiyuan.player.fragment.HomeContentFragment;
import com.yiyuan.player.fragment.LeftFragment;
import com.yiyuan.player.fragment.RightFragment;

public class MainActivity extends SlidingFragmentActivity {

	private static final String tag = MainActivity.class.getSimpleName();
	
	 protected static final String ACTION_SHOW_PROGRESSBAR = "org.videolan.vlc.gui.ShowProgressBar";
	    protected static final String ACTION_HIDE_PROGRESSBAR = "org.videolan.vlc.gui.HideProgressBar";
	    protected static final String ACTION_SHOW_TEXTINFO = "org.videolan.vlc.gui.ShowTextInfo";
	    public static final String ACTION_SHOW_PLAYER = "org.videolan.vlc.gui.ShowPlayer";

	    private static final String PREF_SHOW_INFO = "show_info";
	    private static final String PREF_FIRST_RUN = "first_run";
	
//	private HomeContentFragment contentFragment = null;
	private HomeContentFragment contentFragment = null;
	public FragmentManager manager = null;
	private LeftFragment leftFragment = null;
	private RightFragment rightFragment = null;
	public SlidingMenu slidingMenu = null;
	private long firstTime = 0;
	private YiYuanPlayerApplication application;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (YiYuanPlayerApplication) getApplication();
		application.activities.add(this);
		manager = getSupportFragmentManager();
		initSlidingMenu(savedInstanceState);
	}
	
	private void initSlidingMenu(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		setBehindContentView(R.layout.test1);
		if(savedInstanceState == null) {
			FragmentTransaction transaction = manager.beginTransaction();
			leftFragment = new LeftFragment();
			transaction.replace(R.id.left_framelayout, leftFragment);
			transaction.commit();
		}else {
			leftFragment = (LeftFragment) manager.findFragmentById(R.id.left_framelayout);
		}
		
		contentFragment = HomeContentFragment.newInstance();
//		contentFragment = TestFragment.newInstance();
		slidingMenu = getSlidingMenu();
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenu.setFadeDegree(0.35f);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		setContentView(R.layout.home_content);
		
		manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.home_content_framelayout, contentFragment);
		transaction.commit();
		
		rightFragment = RightFragment.newInstance();
		getSlidingMenu().setSecondaryMenu(R.layout.test2);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		manager.beginTransaction().replace(R.id.right_framelayout, rightFragment).commit();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		slidingMenu.showMenu();
//		if(keyCode == KeyEvent.KEYCODE_BACK) {
//			if(slidingMenu.isShown()) {
//				slidingMenu.showMenu();
//			}
//			long secondTime = System.currentTimeMillis();
//			if (secondTime - firstTime > 800) {
//				Toast.makeText(this, "在按一次返回键退出", Toast.LENGTH_SHORT).show();
//				firstTime = secondTime;
//				return true;
//			} else {
//				application.onTerminate();
//			}
//		}
		return super.onKeyDown(keyCode, event);
	}
}
