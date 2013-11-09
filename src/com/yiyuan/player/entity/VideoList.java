package com.yiyuan.player.entity;

import java.util.List;
/**
 * 
 * @author lianjie
 *电影更多里实际内容
 */
public class VideoList {

	private String video_num;
	private String beg;
	private String end;
	private List<Videos> videos;
	public String getVideo_num() {
		return video_num;
	}
	public void setVideo_num(String video_num) {
		this.video_num = video_num;
	}
	public String getBeg() {
		return beg;
	}
	public void setBeg(String beg) {
		this.beg = beg;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public List<Videos> getVideos() {
		return videos;
	}
	public void setVideos(List<Videos> videos) {
		this.videos = videos;
	}
	
}
