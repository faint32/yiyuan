package com.yiyuan.player.entity;

import java.util.List;

public class CCTV {
	private int channel_id;
	private String channel_name;
	private String icon_url;
	private String url;
	private List<String> second_url;
	private String types;
	private String path;
	public int getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
	}
	public String getChannel_name() {
		return channel_name;
	}
	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}
	public String getIcon_url() {
		return icon_url;
	}
	public void setIcon_url(String icon_url) {
		this.icon_url = icon_url;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<String> getSecond_url() {
		return second_url;
	}
	public void setSecond_url(List<String> second_url) {
		this.second_url = second_url;
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
