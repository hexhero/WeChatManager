/**
 * 
 */
package com.zjapl.weixin.transfer.bean;

import java.util.Map;

/**
 * 微信模板消息实体
 * @author Jiangdd
 *
 * 2017年12月20日 上午11:32:06 
 */
public class TemplateMessage {
    //	是	接收者openid
	private String touser;
	
	//是	模板ID
	private String template_id;	
	
	//否	模板跳转链接
	private String url;	
	
	//否	跳小程序所需数据，不需跳小程序可不用传该数据
	private Miniprogram miniprogram;	
	
	//是	模板数据
	private Map<String,Data> data;	
	
	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Miniprogram getMiniprogram() {
		return miniprogram;
	}

	public void setMiniprogram(Miniprogram miniprogram) {
		this.miniprogram = miniprogram;
	}

	public Map<String, Data> getData() {
		return data;
	}

	public void setData(Map<String, Data> data) {
		this.data = data;
	}

	public class Miniprogram{
		//是	所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系）
		private String appid;	
		//是	所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar）
		private String pagepath;	
		
		public String getAppid() {
			return appid;
		}

		public void setAppid(String appid) {
			this.appid = appid;
		}

		public String getPagepath() {
			return pagepath;
		}

		public void setPagepath(String pagepath) {
			this.pagepath = pagepath;
		}
	}
	
	public class Data{
		
		public Data(String value,String color){
			this.value = value;
			this.color = color;
		}
		
		public Data(String value){
			this.value = value;
		}
		
		//否	模板内容字体颜色，不填默认为黑色
		private String color;
		private String value;
		
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
}
