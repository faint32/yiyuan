package com.yiyuan.player.entity;

import java.util.List;

/**
 * 右侧菜单请求的数据类
 * @author lenovo
 *
 */
public class RightUI {

	private Description description;
	private List<RightItem> items;
	public List<RightItem> getItems() {
		return items;
	}
	public void setItems(List<RightItem> items) {
		this.items = items;
	}
	public Description getDescription() {
		return description;
	}
	public void setDescription(Description description) {
		this.description = description;
	}
	
}
