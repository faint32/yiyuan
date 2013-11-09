package com.yiyuan.player.entity;

import java.util.List;

/**
 * 电影更多
 * @author lianjie
 *
 */
public class MovieMore {

	private VideoList video_list;
	private List<CurConds> cur_conds;
	public VideoList getVideo_list() {
		return video_list;
	}
	public void setVideo_list(VideoList video_list) {
		this.video_list = video_list;
	}
	public List<CurConds> getCur_conds() {
		return cur_conds;
	}
	public void setCur_conds(List<CurConds> cur_conds) {
		this.cur_conds = cur_conds;
	}
	
}
