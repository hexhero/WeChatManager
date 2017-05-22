package com.zjapl.weixin.transfer.bean;

import com.alibaba.fastjson.JSON;

/**
 * 微信返回结果
 * @author yangb
 *
 */
public class WeChatResult {

	/**
	 * 错误码 0:请求成功 否则失败.
	 */
	private Integer errcode;
	
	/**
	 * 错误信息
	 */
	private String errmsg;

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	
	public static WeChatResult makeResultEx(String result){
		if(result == null){
			return new WeChatResult();
		}
		WeChatResult weiXinResult = JSON.parseObject(result, WeChatResult.class);
		if(weiXinResult.getErrcode() == 0){
		}
		
		return weiXinResult;
	}
	
	public static WeChatResult makeError(){
		WeChatResult ex = new WeChatResult();
		ex.setErrcode(80);
		ex.setErrmsg("系统错误");
		return ex;
	}
}
