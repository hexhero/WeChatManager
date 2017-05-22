package com.zjapl.weixin.transfer.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjapl.weixin.transfer.bean.EventInfo;
import com.zjapl.weixin.transfer.bean.ServerAuthInfo;
import com.zjapl.weixin.transfer.dict.WeChatEventDict;
import com.zjapl.weixin.transfer.manager.EventManager;
import com.zjapl.weixin.transfer.utils.WeiXinXmlUtils;

/**
 * 微信回调全局接口
 * @author yangb
 *
 */
@Controller
@RequestMapping("/public")
public class WeChatPublicController {

	private EventManager eventManager = EventManager.getInstance();
	
	/**
	 * 微信统一回调接口
	 * 
	 * @param value
	 * @return
	 */
	@RequestMapping(value="/callback", method=RequestMethod.POST)
	@ResponseBody
	public String globalEvent(@RequestBody String value){
		EventInfo reqInfo = WeiXinXmlUtils.xml2EventInfo(value);
		String appname = reqInfo.getToUserName();
		EventInfo respInfo = null;
		switch(reqInfo.getMsgType()){
		
		case WeChatEventDict.MSG_TYPE_EVENT:
			respInfo = eventManager.notifyHandleEvent(appname, reqInfo);
			break;
			
		case WeChatEventDict.MSG_TYPE_TEXT:
			respInfo = eventManager.notifyHandleText(appname, reqInfo);
			break;
			
		case WeChatEventDict.MSG_TYPE_VOICE:
			respInfo = eventManager.notifyHandleVoice(appname, reqInfo);
			break;
		}
		
		return WeiXinXmlUtils.EventInfo2Xml(respInfo);
	}
	
	/**	
	 * 微信服务器认证回调接口
	 * 
	 * @param server
	 * @return
	 */
	@RequestMapping(value="/callback", method=RequestMethod.GET)
	@ResponseBody
	public String globalEvent(ServerAuthInfo serverInfo){
		return serverInfo.getEchostr();
	}
	
}
