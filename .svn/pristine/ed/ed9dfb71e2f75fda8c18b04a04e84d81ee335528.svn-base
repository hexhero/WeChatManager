/**
 * 
 */
package com.zjapl.weixin.transfer.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjapl.weixin.transfer.bean.TemplateMessage;
import com.zjapl.weixin.transfer.bean.TemplateMessage.Data;
import com.zjapl.weixin.transfer.bean.WeChatObjectResult;
import com.zjapl.weixin.transfer.helper.WeChatTokenHelper;

/**
 * 发送模板消息工具类
 * @author Jiangdd
 *
 * 2017年12月20日 上午11:36:07 
 */
public class TemplateMessageUtils {
	static Logger log = LoggerFactory.getLogger(TemplateMessageUtils.class);

	public final static String sendUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
	
	/**
	 * 发送模板消息
	 * @param msg
	 * @return
	 */
	public static WeChatObjectResult<Object> sendTemplateMessage(TemplateMessage msg,String appid){
		if(msg!=null&&msg.getData()!=null&&!StringUtils.isEmpty(appid)){
			msg.setData(msg.getData());
			String json = JSONObject.toJSONString(msg);
			String result = HttpUtils.methodPost(json, sendUrl, WeChatTokenHelper.obtainAccessToken(appid));
			JSONObject jb = JSON.parseObject(result);
			log.info("发送模板消息："+json+"/n/n,结果："+jb);
			return new WeChatObjectResult<Object>().makeObjectResultEx(jb);
		}
		return new WeChatObjectResult<>().makeObjectResultEx("");
	}
	
	public static void main(String[] args) {
		//String jsonToken = HttpUtils.methodGet(WeChatUrlDict.ACCESS_TOKEN_URL, appid,"8f3d2b82d3e2ef2f0c933c5826cbb25d");
		//JSONObject jbt = JSON.parseObject(jsonToken);
		TemplateMessage msg = new TemplateMessage();
		msg.setTemplate_id("ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY");
		msg.setTouser("OPENID");
		msg.setUrl("http://weixin.qq.com/download");
		Map<String, Data> map = new HashMap<String, Data>();
		
		map.put("first", msg.new Data("恭喜你购买成功！"));
		map.put("keynote1", msg.new Data("巧克力"));
		map.put("keynote2", msg.new Data("39.8元"));
		map.put("keynote3", msg.new Data("2014年9月22日"));
		
		msg.setData(map);
		String json = JSONObject.toJSONString(msg);
		System.out.println("*********************************"+json);
	}
}
