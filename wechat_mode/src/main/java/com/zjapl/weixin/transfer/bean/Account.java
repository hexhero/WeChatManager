package com.zjapl.weixin.transfer.bean;

import java.util.List;

/**
 * 微信账户信息
 * @author yangb
 *
 */
public class Account {

	/**
	 * 关注该公众账号的总用户数
	 */
	private Integer total;
	/**
	 * 拉取的OPENID个数，最大值为10000
	 */
	private Integer count;
	
	/**
	 * 数据
	 */
	private Data data;
	
	/**
	 * 拉取列表的最后一个用户的OPENID
	 */
	private String nextOpenid;
	
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

	public String getNextOpenid() {
		return nextOpenid;
	}
	public void setNextOpenid(String nextOpenid) {
		this.nextOpenid = nextOpenid;
	}
	
	class Data{
		
		/**
		 * OPENID的列表
		 */
		private List<String> openid;
		
		public List<String> getOpenid() {
			return openid;
		}
		public void setOpenid(List<String> openid) {
			this.openid = openid;
		}
	}
	
}
