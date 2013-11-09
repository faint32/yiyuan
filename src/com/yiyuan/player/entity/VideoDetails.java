package com.yiyuan.player.entity;

import java.util.List;

public class VideoDetails {

	private String id;
	private String title;
	private String trunk;
	private String img_url;
	private String intro;
	private String rating;
	private String duration;
	private String pubtime;
	private int cur_episode;
	private int max_episode;
	private List<String> director;
	private List<String> actor;
	private List<String> area;
	private List<String> type;
	private String site_type;
	private String play_filter;
	private String bdhd;
	private List<Sites> sites;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTrunk() {
		return trunk;
	}
	public void setTrunk(String trunk) {
		this.trunk = trunk;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getPubtime() {
		return pubtime;
	}
	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}
	public int getCur_episode() {
		return cur_episode;
	}
	public void setCur_episode(int cur_episode) {
		this.cur_episode = cur_episode;
	}
	public int getMax_episode() {
		return max_episode;
	}
	public void setMax_episode(int max_episode) {
		this.max_episode = max_episode;
	}
	public List<String> getDirector() {
		return director;
	}
	public void setDirector(List<String> director) {
		this.director = director;
	}
	public List<String> getActor() {
		return actor;
	}
	public void setActor(List<String> actor) {
		this.actor = actor;
	}
	public List<String> getArea() {
		return area;
	}
	public void setArea(List<String> area) {
		this.area = area;
	}
	public List<String> getType() {
		return type;
	}
	public void setType(List<String> type) {
		this.type = type;
	}
	public String getSite_type() {
		return site_type;
	}
	public void setSite_type(String site_type) {
		this.site_type = site_type;
	}
	public String getPlay_filter() {
		return play_filter;
	}
	public void setPlay_filter(String play_filter) {
		this.play_filter = play_filter;
	}
	public String getBdhd() {
		return bdhd;
	}
	public void setBdhd(String bdhd) {
		this.bdhd = bdhd;
	}
	public List<Sites> getSites() {
		return sites;
	}
	public void setSites(List<Sites> sites) {
		this.sites = sites;
	}
	
}
