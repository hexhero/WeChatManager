package com.zjapl.weixin.transfer.manager;

import com.zjapl.weixin.transfer.bean.EventInfo;
import com.zjapl.weixin.transfer.dict.WeChatEventDict;

/**
 * 事件消息的抽象类
 * @author yangb
 *
 */
public abstract class EventAbstract implements EventObserver {

	private EventInfo event;

	@Override
	public EventInfo handleEvent(EventInfo reqInfo) {
		this.event = reqInfo;
		EventInfo respInfo = null;
		String openid = reqInfo.getFromUserName();
		switch (reqInfo.getEvent()) {
		case WeChatEventDict.EVENT_SUBSCRIBE:
			respInfo = subscribe(openid);
			break;
			
		case WeChatEventDict.EVENT_UNSUBSCRIBE:
			unsubscribe(openid);
			break;
			
		case WeChatEventDict.EVENT_SCAN:
			respInfo = scan(openid);
			break;
			
		default:
			respInfo = custom(reqInfo);
			break;
		}
		
		return respInfo;
	}
	
	/**
	 * 用户关注
	 * @param requestInfo
	 */
	public abstract EventInfo subscribe(String openid);

	/**
	 * 用户取消关注
	 * @param requestInfo
	 */
	public abstract void unsubscribe(String openid);

	/**
	 * 已关注用户扫描公众号二维码
	 * @param requestInfo
	 */
	public abstract EventInfo scan(String openid);

	/**
	 * 自定义事件.
	 * @param event
	 */
	public abstract EventInfo custom(EventInfo event);
	
	/**
	 * 获取事件信息
	 * @return
	 */
	public EventInfo getEventInfo(){
		return this.event;
	}
}
