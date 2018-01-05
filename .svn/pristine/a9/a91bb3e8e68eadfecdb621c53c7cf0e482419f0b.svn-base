package com.zjapl.weixin.transfer.bean;

/**
 * 微信带参数结果处理接口
 * @author yangb
 *
 */
public class WeChatObjectResult<T> extends WeChatResult {

	private T data;
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public WeChatObjectResult<T> makeObjectResultEx(T data){
		setData(data);
		return this;
	}
	
}
