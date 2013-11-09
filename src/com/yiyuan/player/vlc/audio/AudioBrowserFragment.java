package com.yiyuan.player.vlc.audio;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.yiyuan.player.R;
import com.yiyuan.player.adapter.AudioListAdapter;
import com.yiyuan.player.fragment.YiYuanFragment;
import com.yiyuan.player.vlc.AudioServiceController;
import com.yiyuan.player.vlc.Media;
import com.yiyuan.player.vlc.MediaLibrary;
import com.yiyuan.player.vlc.WeakHandler;

public class AudioBrowserFragment extends YiYuanFragment {

	/**
	 * 音频控制器
	 */
	private AudioServiceController mAudioController;

	private MediaLibrary mMediaLibrary;

	private AudioListAdapter mSongsAdapter;

	public final static int SORT_BY_TITLE = 0;
	public final static int SORT_BY_LENGTH = 1;
	private boolean mSortReverse = false;
	private int mSortBy = SORT_BY_TITLE;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mAudioController = AudioServiceController.getInstance();
		mMediaLibrary = MediaLibrary.getInstance(getActivity());

		mSongsAdapter = new AudioListAdapter(getActivity());
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.audio_browser, container, false);
		ListView mSongsList = (ListView) view.findViewById(R.id.songs_list);
		mSongsList.setAdapter(mSongsAdapter);

		mSongsList.setOnItemClickListener(songListener);
		return view;
	}
	
	@Override
	public void onResume() {
		mMediaLibrary.addUpdateHandler(mHandler);
	};
	
	@Override
	public void onDestroy() {
		mSongsAdapter.clear();
	};

	OnItemClickListener songListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			mAudioController.load(mSongsAdapter.getLocations(), position);
			AudioPlayerFragment.start(getActivity());
		}
	};

	/**
	 * Handle changes on the list
	 */
	private Handler mHandler = new AudioBrowserHandler(this);

	private static class AudioBrowserHandler extends
			WeakHandler<AudioBrowserFragment> {
		public AudioBrowserHandler(AudioBrowserFragment owner) {
			super(owner);
		}

		@Override
		public void handleMessage(Message msg) {
			AudioBrowserFragment fragment = getOwner();
			if (fragment == null)
				return;

			switch (msg.what) {
			case MediaLibrary.MEDIA_ITEMS_UPDATED:
				fragment.updateLists();
				break;
			}
		}
	};

	private void updateLists() {
		List<Media> audioList = MediaLibrary.getInstance(getActivity())
				.getAudioItems();
		mSongsAdapter.clear();

		switch (mSortBy) {
		case SORT_BY_LENGTH:
			Collections.sort(audioList, byLength);
			break;
		case SORT_BY_TITLE:
		default:
			Collections.sort(audioList, byName);
			break;
		}
		if (mSortReverse) {
			Collections.reverse(audioList);
		}
		for (int i = 0; i < audioList.size(); i++) {
			mSongsAdapter.add(audioList.get(i));
		}
		mSongsAdapter.notifyDataSetChanged();
	}
	
	 private final Comparator<Media> byLength = new Comparator<Media>() {
	        @Override
	        public int compare(Media m1, Media m2) {
	            if(m1.getLength() > m2.getLength()) return -1;
	            if(m1.getLength() < m2.getLength()) return 1;
	            else return 0;
	        };
	};
	
	private final Comparator<Media> byName = new Comparator<Media>() {
        @Override
        public int compare(Media m1, Media m2) {
            return String.CASE_INSENSITIVE_ORDER.compare(m1.getTitle(), m2.getTitle());
        };
    };
    
    public void sortBy(int sortby) {
        if(mSortBy == sortby) {
            mSortReverse = !mSortReverse;
        } else {
            mSortBy = sortby;
            mSortReverse = false;
        }
        updateLists();
    }
}