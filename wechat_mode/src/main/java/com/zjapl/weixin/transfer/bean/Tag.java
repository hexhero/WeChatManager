package com.zjapl.weixin.transfer.bean;

/**
 * 微信标签
 * @author yangb
 *
 */
public class Tag extends WeChatResult{
	//标签id
	private Integer id; 
	//标签名称
	private String name;
	//标签下面的粉丝数量
	private Integer count;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
