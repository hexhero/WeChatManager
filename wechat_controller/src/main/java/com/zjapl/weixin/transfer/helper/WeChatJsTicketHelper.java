package com.zjapl.weixin.transfer.helper;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.zjapl.weixin.transfer.bean.JsApiTicket;
import com.zjapl.weixin.transfer.dict.WeChatUrlDict;
import com.zjapl.weixin.transfer.utils.HttpUtils;

/**
 * 微信JS-API令牌管理
 * JsApiTicketHelper
 * @author yangb
 *
 */
public class WeChatJsTicketHelper {
	/**
	 *  JsApiTicket
	 */
	private static ConcurrentMap<String, JsApiTicket> tickets = new ConcurrentHashMap<>();
	
	/**
	 * 获取JsApiTicket str
	 * @param appid
	 * @return
	 */
	public static String obtainTicket(String appid){
		JsApiTicket ticket = tickets.get(appid);
		if(ticket == null || ticket.getOutTime().getTime() < new Date().getTime()){
			ticket = createAccessToke(appid);
			tickets.put(appid, ticket);
		}
		return ticket.getTicket();
	}
	
	/**
	 * 创建AccessToken
	 * @param appid
	 * @return
	 */
	private static JsApiTicket createAccessToke(String appid){
		JsApiTicket ticket = HttpUtils.methodGet(JsApiTicket.class, WeChatUrlDict.JS_API_TICKET_URL, WeChatTokenHelper.obtainAccessToken(appid));
		return ticket;
	}
}
