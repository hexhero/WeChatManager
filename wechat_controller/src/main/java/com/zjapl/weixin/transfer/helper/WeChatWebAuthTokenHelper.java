package com.zjapl.weixin.transfer.helper;

import java.text.MessageFormat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjapl.weixin.transfer.bean.AppInfo;
import com.zjapl.weixin.transfer.bean.WebAccessToken;
import com.zjapl.weixin.transfer.dict.WeChatUrlDict;
import com.zjapl.weixin.transfer.utils.HttpCollectionHelper;

/**
 * 微信 webtoken 获取.
 * @author yangb
 *
 */
public class WeChatWebAuthTokenHelper {

	public static WebAccessToken getWebToken(AppInfo appinfo, String code){
		return initWebToken(appinfo, code);
	}

	private static WebAccessToken initWebToken(AppInfo appinfo, String code){
		String url = MessageFormat.format(WeChatUrlDict.WEB_ACCESS_TOKEN_URL, appinfo.getAppid(), appinfo.getSecret(),code);
		String json = HttpCollectionHelper.get(url);
		JSONObject jo = JSON.parseObject(json);
		WebAccessToken webToken = new WebAccessToken();
		webToken.setAccess_token(jo.getString("access_token"));
		webToken.setExpires_in(jo.getString("expires_in"));
		webToken.setOpenid(jo.getString("openid"));
		webToken.setRefresh_token("refresh_token");
		webToken.setScope("scope");
		return webToken;
	}
}
