package com.zjapl.weixin.transfer.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zjapl.weixin.transfer.bean.Tag;
import com.zjapl.weixin.transfer.bean.WeChatObjectResult;
import com.zjapl.weixin.transfer.bean.WeChatResult;
import com.zjapl.weixin.transfer.dict.WeChatUrlDict;
import com.zjapl.weixin.transfer.helper.WeChatTokenHelper;
import com.zjapl.weixin.transfer.utils.HttpUtils;

@Service("transferTagService")
public class TagService{

	/**
	 * 创建一个标签
	 * @param tag
	 * @param appid
	 * @return
	 */
	public WeChatObjectResult<Tag> create(Tag tag, String appid) {
		Tags tags = new Tags();
		tags.setTag(tag);
		String data = JSON.toJSONString(tags);
		tags = HttpUtils.methodPost(Tags.class,data,WeChatUrlDict.CREATE_TAG_URL, WeChatTokenHelper.obtainAccessToken(appid));
		WeChatObjectResult<Tag> objectResultEx = new WeChatObjectResult<Tag>().makeObjectResultEx(tags.tag);
		if(tags.tag == null){
			objectResultEx.setErrcode(tags.getErrcode());
			objectResultEx.setErrmsg(tags.getErrmsg());
		}else{
			objectResultEx.setErrcode(0);
		}
		return objectResultEx;
		
	}
	
	/**
	 * 获取所有标签
	 * @param appid
	 * @return
	 */
	public WeChatObjectResult<List<Tag>> obtain(String appid) {
		String json = HttpUtils.methodGet(WeChatUrlDict.QUERY_TAG_URL, WeChatTokenHelper.obtainAccessToken(appid));
 		Tags tags = JSON.parseObject(json, Tags.class);
		return new WeChatObjectResult<List<Tag>>().makeObjectResultEx(tags.getTags());
	}

	
	public WeChatResult update(Tag tag, String appid) {
		String data = JSON.toJSONString(tag);
		data = "{\"tag\":" + data +"}";
		String result = HttpUtils.methodPost(data, WeChatUrlDict.UPDATE_TAG_URL, WeChatTokenHelper.obtainAccessToken(appid));
		return WeChatResult.makeResultEx(result);
	}

	public WeChatResult delete(Serializable id, String appid) {
		Tag tag = new Tag();
		tag.setId((Integer)id);
		String data = JSON.toJSONString(tag);
		data = "{\"tag\":" + data +"}";
		String result = HttpUtils.methodPost(data, WeChatUrlDict.DELETE_TAG_URL, WeChatTokenHelper.obtainAccessToken(appid));
		return WeChatResult.makeResultEx(result);
	}
	
	public static class Tags extends WeChatResult {
		private List<Tag> tags;
		private Tag tag;
		public List<Tag> getTags() {
			return tags;
		}
		public void setTags(List<Tag> tags) {
			this.tags = tags;
		}
		public Tag getTag() {
			return tag;
		}
		public void setTag(Tag tag) {
			this.tag = tag;
		}
		
		
		
	}


}
