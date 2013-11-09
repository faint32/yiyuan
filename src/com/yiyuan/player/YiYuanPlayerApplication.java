package com.yiyuan.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yiyuan.player.utils.Log;
import com.yiyuan.player.vlc.audio.AudioUtil;
import com.yiyuan.player.vlc.MediaDatabase;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;

public class YiYuanPlayerApplication extends Application {

	private final static String TAG = YiYuanPlayerApplication.class.getSimpleName();
	private static YiYuanPlayerApplication instance;
	public final static String SLEEP_INTENT = "org.videolan.vlc.SleepIntent";
	public List<Activity> activities;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		  // Are we using advanced debugging - locale?
		if(activities == null) {
			activities = new ArrayList<Activity>();
		}
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String p = pref.getString("set_locale", "");
        if (p != null && !p.equals("")) {
            Locale locale;
            // workaround due to region code
            if(p.equals("zh-TW")) {
                locale = Locale.TRADITIONAL_CHINESE;
            } else if(p.startsWith("zh")) {
                locale = Locale.CHINA;
            } else if(p.equals("pt-BR")) {
                locale = new Locale("pt", "BR");
            } else if(p.equals("bn-IN") || p.startsWith("bn")) {
                locale = new Locale("bn", "IN");
            } else {
                /**
                 * Avoid a crash of
                 * java.lang.AssertionError: couldn't initialize LocaleData for locale
                 * if the user enters nonsensical region codes.
                 */
                if(p.contains("-"))
                    p = p.substring(0, p.indexOf('-'));
                locale = new Locale(p);
            }
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }

        instance = this;

        // Initialize the database soon enough to avoid any race condition and crash
        MediaDatabase.getInstance(this);
        // Prepare cache folder constants
        AudioUtil.prepareCacheFolder(this);
		super.onCreate();
	}
	
	/**
     * Called when the overall system is running low on memory
     */
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		Log.e(TAG, "System is running low on memory");
		ImageLoader.getInstance().clearMemoryCache();
	}
	
	 /**
     * @return the main context of the Application
     */
	public static Context getAppContext() {
		return instance;
	}
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		for(Activity activity : activities) {
			activity.finish();
		}
		ImageLoader.getInstance().clearMemoryCache();
		System.exit(0);
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	

    /**
     * @return the main resources from the Application
     */
    public static Resources getAppResources()
    {
        if(instance == null) return null;
        return instance.getResources();
    }
}
