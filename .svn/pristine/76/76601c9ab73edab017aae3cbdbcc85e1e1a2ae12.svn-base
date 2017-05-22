package com.zjapl.weixin.transfer.utils;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

import com.alibaba.fastjson.JSON;
import com.zjapl.weixin.transfer.utils.HttpHelper.HttpResult;

/**
 * Http 工具类
 * @author yangb
 *
 */
public class HttpUtils {
	
	/**
	 * Get请求
	 * @param t
	 * @param url
	 * @param param
	 * @return
	 */
	public static <T> T methodGet(Class<T> t, String url, Object... param){
		if(param != null)
			url = MessageFormat.format(url,param);
		String json = HttpHelper.get(url).getString();
		return JSON.parseObject(json, t);
	}
	
	/**
	 * POST 请求
	 * @param t
	 * @param url
	 * @param param
	 * @return
	 */
	public static <T> T methodPost(Class<T> t, String data, String url, Object... param){
		if(param != null)
			url = MessageFormat.format(url,param);
		try {
			HttpResult post = HttpHelper.post(url, data.getBytes("utf-8"));
			String json = post.getString();
			return JSON.parseObject(json, t);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get不返回对象请求
	 * @param url
	 * @param param
	 * @return
	 */
	public static String methodGet(String url, Object... param){
		if(param != null)
			url = MessageFormat.format(url,param);
		String json = HttpHelper.get(url).getString();
		return json;
	}
	
	/**
	 * Post 不返回对象请求
	 * @param url
	 * @param param
	 * @return
	 */
	public static String methodPost(String data,String url, Object... param){
		if(param != null)
			url = MessageFormat.format(url,param);
		try {
			HttpResult post = HttpHelper.post(url, data.getBytes("utf-8"));
			String json = post.getString();
			return json;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
