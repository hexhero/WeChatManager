package com.zjapl.weixin.transfer.manager;

import com.zjapl.weixin.transfer.bean.AppInfo;

/**
 * 事件处理者 工厂
 * @author yangb
 *
 */
public class EventObserverFactory {

	/**
	 * 创建一个子应用
	 * @param appinfo
	 * @return
	 */
	public static EventObserver createEventObserver(AppInfo appinfo){
		return new EventExecutor(appinfo);
	}
}
