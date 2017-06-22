package com.zjapl.weixin.transfer.manager;

import com.zjapl.weixin.transfer.bean.EventInfo;

/**
 * 事件/消息 处理接口
 * @author yangb
 *
 */
public interface EventObserver {
	
	/**
	 * 当前公众号账号
	 * @return
	 */
	String getAppName();
	
	/**
	 * 事件消息处理
	 * @param reqInfo
	 * @return
	 */
	EventInfo handleEvent(EventInfo reqInfo);
	
	/**
	 * 文本消息处理
	 * @param reqInfo
	 * @return
	 */
	EventInfo handleText(EventInfo reqInfo);
	
	/**
	 * 语音消息处理
	 * @param reqInfo
	 * @return
	 */
	EventInfo handleVoice(EventInfo reqInfo);
	
	/**
	 * 图片消息处理
	 * @param reqInfo
	 * @return
	 */
	EventInfo handleImage(EventInfo reqInfo);
	
	/**
	 * 视频消息处理
	 * @param reqInfo
	 * @return
	 */
	EventInfo handleVideo(EventInfo reqInfo);
	
	/**
	 * 短视频消息处理
	 * @param reqInfo
	 * @return
	 */
	EventInfo handleShortVideo(EventInfo reqInfo);
	
	/**
	 * 地理位置消息处理
	 * @param reqInfo
	 * @return
	 */
	EventInfo handleLocation(EventInfo reqInfo);
	
	/**
	 * 超链接消息处理
	 * @param reqInfo
	 * @return
	 */
	EventInfo handleLink(EventInfo reqInfo);
}
