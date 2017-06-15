package com.zjapl.weixin.pay.ctrl;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zjapl.weixin.pay.service.PayService;
import com.zjapl.weixin.transfer.bean.AppInfo;
import com.zjapl.weixin.transfer.bean.QueryOrderResult;
import com.zjapl.weixin.transfer.bean.UnifiedOrderInfo;
import com.zjapl.weixin.transfer.bean.UnifiedOrderResult;
import com.zjapl.weixin.transfer.bean.WebAccessToken;
import com.zjapl.weixin.transfer.dict.WeChatPayDict;
import com.zjapl.weixin.transfer.helper.WeChatWebAuthTokenHelper;
import com.zjapl.weixin.transfer.utils.WeiXinUtils;
import com.zjapl.weixin.transfer.utils.WeiXinXmlUtils;

/**
 * 微信认证接口
 * @author yangb
 *
 */
@Controller
@RequestMapping("/public/pay")
public class WeChatPayController {

	@Autowired
	private PayService payService;


	/**
	 * 请求支付 (通过微信授权转入, 此接口提供给微信服务器回调)
	 * @param code
	 * @param state 金额*商品名称*单号, &nbsp; 限制长度128字节.
	 * @param request
	 * @return
	 */
	@RequestMapping("/authPay")
	public ModelAndView submitOrder(String code, String state, HttpServletRequest request){
		AppInfo appInfo = new AppInfo();
		appInfo.setAppid(WeChatPayDict.APPID);
		appInfo.setSecret(WeChatPayDict.SECRET);
		appInfo.setAppname(WeChatPayDict.APPNAME);

		WebAccessToken webToken = WeChatWebAuthTokenHelper.getWebToken(appInfo, code);//根据code获取用户的openid

		Map<String, String> map = parseOrderState(state);

		if(map == null){
			ModelAndView mv = new ModelAndView("error");
			mv.addObject("message", "state参数错误, 参数格式如下:<br/> state=金额*商品名称*单号 <br/> 金额单位: 分");
			return mv;
		}

		SortedMap<Object, Object> payparam = prepare( webToken.getOpenid(), request, map);
		ModelAndView mav = new ModelAndView("pay");
		mav.addObject("pay", payparam);
		return mav;
	}

	/**
	 * 解析state 格式: 金额*商品名称*单号, 限制长度128字节.
	 * @param state
	 */
	private Map<String, String> parseOrderState(String state) {
		if(state != null){
			String[] stateArray = state.split("\\*");
			if(stateArray.length == 3){
				HashMap<String, String> map = new HashMap<>();
				if(!stateArray[0].matches("^\\+?[1-9][0-9]*$")){
					return null;
				}
				map.put("fee", stateArray[0]);
				map.put("name", stateArray[1]);
				map.put("order", stateArray[2]);
				return map;
			}
		}
		return null;
	}

	/**
	 * 解析订单参数
	 * @param openid
	 * @param request
	 * @param map 订单参数
	 * @return
	 */
	private SortedMap<Object, Object> prepare(String openid, HttpServletRequest request, Map<String, String> map) {
		//设置订单信息.
		UnifiedOrderInfo orderInfo = new UnifiedOrderInfo();
		orderInfo.setTotal_fee(Integer.parseInt(map.get("fee"))); //订单金额
		orderInfo.setBody(map.get("name")); //订单商品名称
		orderInfo.setOut_trade_no(map.get("order"));//订单号

		SortedMap<Object, Object> sign = unifiedOrder(orderInfo, openid, request);
		//		mav.addObject("order_num", map.get("order")); //返回订单号 
		return sign;
	}

	/**
	 * 提交预订单
	 * 
	 * @return 支付参数
	 */
	public SortedMap<Object, Object> unifiedOrder(UnifiedOrderInfo orderInfo, String openid, HttpServletRequest request){
		//获取客户端真实IP
		String ip = WeiXinUtils.getRemoteHost(request);
		orderInfo.setSpbill_create_ip(ip.split(",")[0]);

		//设置用户openid
		orderInfo.setOpenid(openid);

		//回调地址
		//		String scheme = request.getScheme();
		//		String serverName = request.getServerName();
		String url = "http://itapl.com/naturalMate-web/public/pay/pay_notify";
		orderInfo.setNotify_url(url);

		//获取统一下单结果
		UnifiedOrderResult orderResult = payService.unifiedOrder(orderInfo);

		//准备发起支付数据
		SortedMap<Object, Object> sign = payService.makeResultSign(orderResult);
		return sign;
	}

	/**
	 * 接收支付通知结果
	 * @param value
	 * @return
	 */
	@RequestMapping("/pay_notify")
	@ResponseBody
	public String pay_notify(@RequestBody String value){
		QueryOrderResult orderResult = WeiXinXmlUtils.xml2QueryOrderResult(value);  //支付结果
		return "<xml>  <return_code><![CDATA[SUCCESS]]></return_code>  <return_msg><![CDATA[OK]]></return_msg></xml>";
	}

/*	@RequestMapping("/paypage")
	public ModelAndView paypage(){
		UnifiedOrderResult orderResult  = new UnifiedOrderResult();
		orderResult.setAppid("wx432d8aebade5a186");
		orderResult.setPrepay_id("wx20170517110433aa1cad82dd0360896592");

		SortedMap<Object, Object> sign = payService.makeResultSign(orderResult);

		ModelAndView mv = new ModelAndView("pay");
		mv.addObject("sign", sign);

		return mv;
	}*/

}
