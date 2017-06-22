package com.zjapl.weixin.transfer.manager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.zjapl.weixin.transfer.bean.EventInfo;
import com.zjapl.weixin.transfer.utils.StringUtils;

/**
 * 事件/消息 管理中心
 * @author yangb
 *
 */
public class EventManager {

	private EventManager(){};
	
	private static EventManager mEM = new EventManager(); //饿汉式
	
	public static EventManager getInstance(){
		return mEM;
	}
	
	/**
	 * 微信号 , 事件观察者
	 */
	private static ConcurrentMap<String,EventObserver> mObservers = new ConcurrentHashMap<>();
	
	/**
	 * 注册观察者
	 * @param observer
	 */
	public void registObserver(EventObserver observer){
		if(observer != null && !StringUtils.isEmpty(observer.getAppName())){
			mObservers.put(observer.getAppName(), observer);
		}
	}
	
	/**
	 * 取消注册观察者
	 * @param appid
	 */
	public void unregistObserver(String appid){
		if(mObservers.containsKey(appid)){
			mObservers.remove(appid);
		}
	}
	
	public EventInfo notifyHandleEvent(String appname, EventInfo reqInfo){
		if(mObservers.containsKey(appname)){
			EventObserver observer = mObservers.get(appname);
			if(observer != null) return observer.handleEvent(reqInfo);
		}
		return null;
	}
	
	public EventInfo notifyHandleText(String appname, EventInfo reqInfo){
		if(mObservers.containsKey(appname)){
			EventObserver observer = mObservers.get(appname);
			if(observer != null) return observer.handleText(reqInfo);
		}
		return null;
	}
	
	public EventInfo notifyHandleVoice(String appname, EventInfo reqInfo){
		if(mObservers.containsKey(appname)){
			EventObserver observer = mObservers.get(appname);
			if(observer != null) return observer.handleVoice(reqInfo);
		}
		return null;
	}
}
