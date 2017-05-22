package com.zjapl.weixin.transfer.service;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zjapl.weixin.transfer.bean.Account;
import com.zjapl.weixin.transfer.bean.User;
import com.zjapl.weixin.transfer.bean.WeChatObjectResult;
import com.zjapl.weixin.transfer.bean.WeChatResult;
import com.zjapl.weixin.transfer.dict.WeChatUrlDict;
import com.zjapl.weixin.transfer.helper.WeChatTokenHelper;
import com.zjapl.weixin.transfer.utils.HttpHelper;
import com.zjapl.weixin.transfer.utils.HttpHelper.HttpResult;
import com.zjapl.weixin.transfer.utils.HttpUtils;

/**
 * 微信用户接口实现
 * @author yangb
 *
 */
@Service("transferUserService")
public class UserService{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public WeChatObjectResult<Account> obtainAccount(String nextOpenid, String appid) {
		nextOpenid = nextOpenid == null ? "" : nextOpenid;
		String url = MessageFormat.format(WeChatUrlDict.QUERY_ALL_USER_URL, WeChatTokenHelper.obtainAccessToken(appid),nextOpenid);
		HttpResult httpResult = HttpHelper.get(url);
		String result = httpResult.getString();
		logger.info(result);
		Account account = JSON.parseObject(result, Account.class);
		return new WeChatObjectResult<Account>().makeObjectResultEx(account);
	}

	public WeChatObjectResult<User> obtainUserInfo(String openid, String appid) {
		String url = MessageFormat.format(WeChatUrlDict.QUERY_USERINFO_URL, WeChatTokenHelper.obtainAccessToken(appid),openid);
		HttpResult httpResult = HttpHelper.get(url);
		String result = httpResult.getString();
		logger.info(result);
		User user = JSON.parseObject(result, User.class);
		return new WeChatObjectResult<User>().makeObjectResultEx(user);
	}

	public WeChatResult batchTag(BatchTag batch, String appid) {
		String data = JSON.toJSONString(batch);
		String result = HttpUtils.methodPost(data, WeChatUrlDict.BATCH_TAG_URL, WeChatTokenHelper.obtainAccessToken(appid));
		return WeChatResult.makeResultEx(result);
	}

	public WeChatObjectResult<List<Integer>> obtainTagsByUser(String openid, String appid) {
		String data = "{\"openid\":\" " + openid + " \"}";
		TagIdList tagIdList = HttpUtils.methodPost(TagIdList.class, data, WeChatUrlDict.QUERY_TAG_BY_USER_URL, WeChatTokenHelper.obtainAccessToken(appid));
		return new WeChatObjectResult<List<Integer>>().makeObjectResultEx(tagIdList.tagid_list);
	}

	public WeChatResult unBatchTag(BatchTag batch, String appid) {
		String data = JSON.toJSONString(batch);
		String result = HttpUtils.methodPost(data, WeChatUrlDict.UN_BATCH_TAG_URL, WeChatTokenHelper.obtainAccessToken(appid));
		return WeChatResult.makeResultEx(result);
	}

	public WeChatObjectResult<Account> obtainUserByTag(Serializable tagId, String nextOpenid,String appid) {
		String url = WeChatUrlDict.QUERY_USER_BY_TAG;
		Account account = HttpUtils.methodGet(Account.class, url,  WeChatTokenHelper.obtainAccessToken(appid));
		return new WeChatObjectResult<Account>().makeObjectResultEx(account);
	}
	
	class TagIdList{
		public List<Integer> tagid_list;
	}

	public static class BatchTag{
		/**
		 * 用户openid集合
		 */
		public List<String> openid_list;
		/**
		 * 标签Id
		 */
		public Integer tagid;
	}
}
