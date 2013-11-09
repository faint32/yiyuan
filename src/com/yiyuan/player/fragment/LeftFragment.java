package com.yiyuan.player.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.yiyuan.player.R;
import com.yiyuan.player.vlc.audio.AudioBrowserFragment;

/**
 * 主界面 左侧界面
 * @author lianjie
 *
 */
public class LeftFragment extends Fragment {
	
	private FragmentManager mFragmentManager;

	public static LeftFragment newInstance() {
		LeftFragment mLeftFragment = new LeftFragment();
		return mLeftFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mFragmentManager = getActivity().getSupportFragmentManager();
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.left_container, container, false);
		
		ImageButton localButton = (ImageButton) view.findViewById(R.id.left_container_local);
		localButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
				mTransaction.add(R.id.home_content_framelayout, new AudioBrowserFragment());
				mTransaction.commit();
			}
		});
		
		ImageButton onLineButton = (ImageButton) view.findViewById(R.id.left_container_online);
		onLineButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		ImageButton settingButton = (ImageButton) view.findViewById(R.id.left_container_setting);
		settingButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		return view;
	}
}
