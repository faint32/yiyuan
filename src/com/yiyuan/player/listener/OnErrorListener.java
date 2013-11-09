package com.yiyuan.player.listener;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class OnErrorListener implements Response.ErrorListener {

	private Context context;
	public OnErrorListener(Context context) {
		this.context = context;
	}
		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub
			if(error instanceof TimeoutError) {
				Toast.makeText(context, "网络超时", Toast.LENGTH_SHORT).show();
			}else if (error instanceof NetworkError) {
				Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show();
			} else if (error instanceof NoConnectionError) {
				Toast.makeText(context, "无网络连接", Toast.LENGTH_SHORT).show();
			} else if (error instanceof ParseError) {
				Toast.makeText(context, "解析数据错误", Toast.LENGTH_SHORT).show();
				System.out.println(error);
			} else if (error instanceof AuthFailureError) {
				Toast.makeText(context, "AuthFailure错误", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
			}
		}
		
	}