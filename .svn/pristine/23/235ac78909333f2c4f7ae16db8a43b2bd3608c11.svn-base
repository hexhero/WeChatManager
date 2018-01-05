package com.zjapl.weixin.transfer.utils;

import java.io.UnsupportedEncodingException;

import com.zjapl.weixin.transfer.bean.AppInfo;
import com.zjapl.weixin.transfer.utils.HttpHelper.HttpResult;

public class CommunicationUtil {

	public static final String WEB_AUTH = "/auth";
	
	public static HttpResult postWebAuth(AppInfo appinfo, String data){
		String url = appinfo.getCallbackUrl() + WEB_AUTH;
		try {
			return HttpHelper.post(url, data.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
