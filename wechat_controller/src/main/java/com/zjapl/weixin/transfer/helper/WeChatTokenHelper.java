package com.zjapl.weixin.transfer.helper;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.http.util.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjapl.weixin.transfer.bean.AccessToken;
import com.zjapl.weixin.transfer.dict.WeChatUrlDict;
import com.zjapl.weixin.transfer.global.WeChatContent;
import com.zjapl.weixin.transfer.utils.HttpUtils;

/**
 * 微信令牌管理
 * @author yangb
 *
 */
public class WeChatTokenHelper {
	
	/**
	 *  AccessToken
	 */
	private static ConcurrentMap<String, AccessToken> tokens = new ConcurrentHashMap<>();
	
	private WeChatTokenHelper(){}
	
	/**
	 * 获取AccessToken str
	 * @param appid
	 * @return
	 */
	public static String obtainAccessToken(String appid){
		AccessToken token = tokens.get(appid);
		if(token == null || token.getOutTime().getTime() < new Date().getTime()){
			token = createAccessToke(appid);
			tokens.put(appid, token);
		}
		return token.getAccessToken();
	}
	
	/**
	 * 创建AccessToken
	 * @param appid
	 * @return
	 */
	private static AccessToken createAccessToke(String appid){
		String secret = WeChatContent.obtainSecret(appid);
		if(TextUtils.isEmpty(secret)){
			throw new IllegalArgumentException("没有找到 Appid: " + appid + " 的密匙信息");
		}
		String json = HttpUtils.methodGet(WeChatUrlDict.ACCESS_TOKEN_URL, appid,secret);
		AccessToken token = new AccessToken();
		JSONObject jb = JSON.parseObject(json);
		token.setAccessToken(jb.getString("access_token"));
		token.setExpiresIn(jb.getString("expires_in"));
		return token;
	}
}







