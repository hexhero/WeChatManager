package com.zjapl.weixin.transfer.bean;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * JsApiTicket
 * @author yangb
 *
 */
public class JsApiTicket extends WeChatResult{

	private String ticket;
	
	private Integer expires_in;

	/**
	 * 过期时间
	 */
	private Date outTime;
	
	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Integer getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
		//计算过期时间
		if(expires_in == null){
			expires_in = 0;
		}
		setOutTime(DateUtils.addSeconds(new Date(), expires_in));
	}
	
}
