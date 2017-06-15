package com.zjapl.weixin.transfer.global;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;

import com.zjapl.weixin.transfer.bean.AppInfo;
import com.zjapl.weixin.transfer.manager.EventManager;
import com.zjapl.weixin.transfer.manager.EventObserver;

/**
 * 全局初始化
 * @author yangb
 *
 */
@Controller
public class WeChatGlobalInit {
	
	public EventManager eventManager = EventManager.getInstance();
	
	public static WeChatContent content;

	/**
	 * 添加公众号信息
	 * @param appinfo 公众号
	 * @param observer 事件处理者
	 */
	public static void addApp(AppInfo appinfo, EventObserver observer){
		content.addAppInfo(appinfo);
		EventManager.getInstance().registObserver(observer); // 注册事件
	}
	
	/**
	 * 移除公众号信息
	 * @param appinfo 公众号
	 */
	public static void removeApp(AppInfo appinfo){
		content.removeAppInfo(appinfo);
		EventManager.getInstance().unregistObserver(appinfo.getAppid()); // 取消注册
	}
	
	/**
	 * 初始化应用.
	 */
	public static void initApp(ArrayList<AppInfo> apps){
		content = new WeChatContent(); 
		content.init(apps);//初始化Content
		System.out.println("系统初始化完成!");
	}
	
	
	/**
	 * 获取公众号账号信息
	 * @return
	 */
	/*
	@PostConstruct
	public void testInit(){
		
		ArrayList<AppInfo> apps = new ArrayList<AppInfo>();//weChatPublicService.searchAppList();
		
		//测试账号 jdd
		AppInfo app1 = new AppInfo();
		app1.setAppname("gh_4e7631df0476");
		app1.setAppid("wxd5c513ec2d48b455");
		app1.setSecret("85890aac1876e96f5643701d44f72386");
		app1.setCallbackUrl("http://127.0.0.1:8080/office-web/public/wechat");
		//测试账号 yangb
		AppInfo app2 = new AppInfo();
		app2.setAppname("gh_9c332f4b0835");
		app2.setAppid("wx4f4707d0ed5a9332");//wx4f4707d0ed5a9332
		app2.setSecret("cbc18905da2e102db1c17035b09ddb3e");
		app2.setCallbackUrl("http://192.168.10.46:8181/market-web/public/wechat");
		//测试账号 ysms_cn
		AppInfo app3 = new AppInfo();
		app3.setAppname("ysms_cn");
		app3.setAppid("wxe88656209c6cf6fb");
		app3.setSecret("ef0960c8b097ef06cf02e2d9d5762a71");
		app3.setCallbackUrl("http://192.168.10.46:8181/market-web/public/wechat");
		
		apps.add(app1);
		apps.add(app2);
		apps.add(app3);
		
		initApp(apps);
	}
	*/
	/**
	 * 注册子应用
	 * @param appInfo
	 */
/*	private void registApp(AppInfo appInfo){
		//注册子应用.
		EventObserver eventObserver = EventObserverFactory.createEventObserver(appInfo);
		eventManager.registObserver(eventObserver); 
		System.out.println("注册应用 " + appInfo.getAppname() + " 完成");
	}*/
	
}
