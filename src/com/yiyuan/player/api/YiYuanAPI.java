package com.yiyuan.player.api;

import java.util.Map;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.Volley;
import com.yiyuan.player.http.GsonRequest;
import com.yiyuan.player.http.HttpUtils;

public class YiYuanAPI {
	
	private static YiYuanAPI mApi;
	private RequestQueue mQueue;
	public Object object;
	
	private YiYuanAPI(Context context) {
		mQueue = Volley.newRequestQueue(context, new HttpClientStack(HttpUtils.getHttpClient(context)));
		object = new Object();
	}

	public static YiYuanAPI newInstance(Context context) {
		if(mApi == null) {
			mApi = new YiYuanAPI(context);
		}
		return mApi;
	}
	
	public <T> GsonRequest<T> requestGetData(String requestUrl, Class<T> clazz, final Response.Listener<T> succeedListener, final Response.ErrorListener errorListener) {
		GsonRequest<T> mGsonRequest = new GsonRequest<T>(Method.GET, requestUrl, clazz, null, succeedListener, errorListener);
		mGsonRequest.setTag(object);
		mQueue.add(mGsonRequest);
		return mGsonRequest;
	}
	
	public <T> GsonRequest<T> requestPostData(String requestUrl, Class<T> clazz, Map<String, String> headers, final Response.Listener<T> succeedListener, final Response.ErrorListener errorListener) {
		GsonRequest<T> mGsonRequest = new GsonRequest<T>(Method.POST, requestUrl, clazz, headers, succeedListener, errorListener);
		mGsonRequest.setTag(object);
		mQueue.add(mGsonRequest);
		return mGsonRequest;
	}
	
	public void stopAllRequest() {
		mQueue.cancelAll(object);
	}
}
