package com.zjapl.weixin.transfer.service;

import java.io.File;
import java.text.MessageFormat;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjapl.weixin.transfer.bean.WeChatObjectResult;
import com.zjapl.weixin.transfer.dict.WeChatUrlDict;
import com.zjapl.weixin.transfer.helper.WeChatTokenHelper;
import com.zjapl.weixin.transfer.utils.HttpHelper;

/**
 * @author admin
 *
 */
@Service
public class ResourceService {

	/**
	 * 上传
	 * @param file
	 * @return
	 */
	public WeChatObjectResult<String> uploadImg(File file, String appid) {
		String url = MessageFormat.format(WeChatUrlDict.UPLOAD_IMG_URL, WeChatTokenHelper.obtainAccessToken(appid));
		String imgUrl = null;
		try {
			String json = HttpHelper.upload(url, file);
			JSONObject jsonObject = JSON.parseObject(json);
			imgUrl = jsonObject.getString("url");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new WeChatObjectResult<String>().makeObjectResultEx(imgUrl);
	}

}
