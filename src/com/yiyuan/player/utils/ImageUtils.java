package com.yiyuan.player.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.yiyuan.player.R;

public class ImageUtils {
	
	public static boolean isInit = false;
	
	public static int getDefaultMemoryCacheSize(Context context) {
		int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
		int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
		return heightPixels * widthPixels * 8;
	}

	public static DisplayImageOptions imageLoader(Context context, int rounded) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.default_image)
		.showImageForEmptyUri(R.drawable.default_image)
		.showImageOnFail(R.drawable.default_image)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.displayer(new RoundedBitmapDisplayer(rounded))
		.displayer(new FadeInBitmapDisplayer(300))
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		initImageLoader(context);
		return options;
	}
	
	
	private static void initImageLoader(Context context) {
		if(isInit) {
			return;
		}
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(getDefaultMemoryCacheSize(context)))
				.discCacheSize(1000000000)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs()
				.build();
		ImageLoader.getInstance().init(config);
	}
}
