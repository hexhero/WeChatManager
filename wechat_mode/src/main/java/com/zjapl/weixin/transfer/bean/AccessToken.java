package com.zjapl.weixin.transfer.bean;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * 服务器凭证 APP AccessToken
 * @author yangb
 *
 */
public class AccessToken {

	/**
	 * 获取到的凭证
	 */
	private String accessToken;
	/**
	 * 凭证有效时间, 单位: 秒 s
	 */
	private String expiresIn;
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
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
		//计算过期时间
		if(expiresIn == null){
			expiresIn = "10";
		}
		setOutTime(DateUtils.addSeconds(new Date(), Integer.parseInt(expiresIn)));
	}
	
	
}
