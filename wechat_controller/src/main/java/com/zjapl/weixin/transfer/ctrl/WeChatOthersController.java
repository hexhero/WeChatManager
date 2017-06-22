package com.zjapl.weixin.transfer.ctrl;

import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjapl.weixin.transfer.bean.WeChatResult;
import com.zjapl.weixin.transfer.service.UserService;
import com.zjapl.weixin.transfer.service.UserService.BatchTag;

/**
 * 微信其他服务接口
 * @author yangb
 *
 */
@Controller
@RequestMapping("/public/others")
public class WeChatOthersController {

	private UserService userService = new UserService();
	
	/**
	 * 为用户打标签
	 * 
	 * @return
	 */
	@RequestMapping("/batchTag")
	@ResponseBody
	public WeChatResult batchTag(String openid, Integer tagid, String appid){
		
		BatchTag batch = new BatchTag();
		batch.openid_list = Arrays.asList(openid);
		batch.tagid = tagid;
		WeChatResult result = userService.batchTag(batch, appid);
		return result;
	}

	
}
