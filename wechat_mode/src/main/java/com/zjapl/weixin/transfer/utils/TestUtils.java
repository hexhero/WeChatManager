package com.zjapl.weixin.transfer.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.Test;

import com.zjapl.weixin.transfer.dict.WeChatEventDict;

public class TestUtils {

	@Test
	public void haveUrl(){
		String webAuthURL = WeiXinUtils.createWebAuthURL("wx432d8aebade5a186", WeChatEventDict.SCOPE_SNSAPI_BASE, "redirect", "gh_226a7f5a3886");
		System.out.println(webAuthURL);
	}
	
	@Test
	public void t2() throws UnsupportedEncodingException{
		String url = "http://itapl.com/weixin-web-front/";
		String encode = URLEncoder.encode(url, "utf-8");
		System.out.println(encode);
	}
}
