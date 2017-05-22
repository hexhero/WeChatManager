package com.zjapl.weixin.pay.service;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjapl.weixin.transfer.bean.BaseOrderInfo;
import com.zjapl.weixin.transfer.bean.QueryOrderResult;
import com.zjapl.weixin.transfer.bean.UnifiedOrderInfo;
import com.zjapl.weixin.transfer.bean.UnifiedOrderResult;
import com.zjapl.weixin.transfer.dict.WeChatPayDict;
import com.zjapl.weixin.transfer.utils.HttpUtils;
import com.zjapl.weixin.transfer.utils.WeiXinUtils;
import com.zjapl.weixin.transfer.utils.WeiXinXmlUtils;

/**
 * 微信支付接口实现
 * @author yangb
 *
 */
@Service
public class PayService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public UnifiedOrderResult unifiedOrder(UnifiedOrderInfo orderInfo) {
		orderInfo.setAppid("wx432d8aebade5a186");
		orderInfo.setMch_id(WeChatPayDict.MCH_ID); //商户ID
		orderInfo.setDevice_info(WeChatPayDict.DEVICE_INFO); //设备号
		orderInfo.setNonce_str(WeiXinUtils.makeNonceStr());//随机字符串 
		orderInfo.setSign_type(WeChatPayDict.SIGN_TYPE); //签名类型
		orderInfo.setFee_type(WeChatPayDict.FEE_TYPE); //货币类型
		
		if(WeChatPayDict.ORDER_EXPIRE_TIME > (60 * 5)){ //最短失效时间间隔必须大于5分钟
			orderInfo.setTime_start(WeiXinUtils.paseDateToSecond(new Date())); //交易起始时间
			Date timeExp = DateUtils.addSeconds(new Date(), WeChatPayDict.ORDER_EXPIRE_TIME); //失效时间
			orderInfo.setTime_expire(WeiXinUtils.paseDateToSecond(timeExp)); //交易失效时间
		}
		orderInfo.setTrade_type(WeChatPayDict.TRADE_TYPE); //交易类型
		
		
		//必填
//		orderInfo.setTotal_fee(1); //商品价格
//		orderInfo.setBody(""); //商品描述
//		orderInfo.setSpbill_create_ip("用户端IP");
//		orderInfo.setNotify_url(""); //支付结果通知的回调地址
//		orderInfo.setOpenid(""); //openid
//		orderInfo.setOut_trade_no("");  //商户订单号
		
		//选填
//		orderInfo.setDetail("");//商品详情
//		orderInfo.setAttach("");//附加数据
//		orderInfo.setGoods_tag(""); //商品标记
//		orderInfo.setProduct_id(""); //商品Id
//		orderInfo.setLimit_pay(""); //指定支付方式 no_credit--可限制用户不能使用信用卡支付
		
		
		//计算签名
		String sign = makeSign(orderInfo);
		orderInfo.setSign(sign);
		
		//转xml结果
		String xmlParam = WeiXinXmlUtils.UnifiedOrderInfo2Xml(orderInfo);
		logger.info(xmlParam);
		
		//提交至微信服务器
		String xml = HttpUtils.methodPost(xmlParam, WeChatPayDict.PAY_UNIFIED_ORDER_URL);
		System.out.println(xml);
		UnifiedOrderResult orderResult = WeiXinXmlUtils.xml2UnifiedOrderResult(xml);
		
		return orderResult;
	}
	
	public SortedMap<Object,Object> makeResultSign(UnifiedOrderResult orderResult){
		//TODO 待测试签名的正确性
		SortedMap<Object,Object> sortedMap = new TreeMap<>();
		sortedMap.put("appId", orderResult.getAppid());
		sortedMap.put("timeStamp", System.currentTimeMillis() / 1000 + "");
		sortedMap.put("nonceStr", WeiXinUtils.makeNonceStr());
		sortedMap.put("package", "prepay_id=" + orderResult.getPrepay_id());
		sortedMap.put("signType", "MD5");
		String paySign = WeiXinUtils.createSign("UTF-8", sortedMap);
		sortedMap.put("paySign", paySign);
		
		return sortedMap;
	}
	
	private int idType_wx = 1; //微信订单号
	private int idType_trade = 0; //商户订单号
	
	public QueryOrderResult queryOrderByTransactionId(String orderId) {
		return queryOrder(orderId, idType_wx);
	}

	public QueryOrderResult queryOrderByOutTradeNo(String orderId) {
		return queryOrder(orderId, idType_trade);
	}
	
	private QueryOrderResult queryOrder(String orderId,int idType){
		BaseOrderInfo orderInfo = new BaseOrderInfo();
//		orderInfo.setAppid(WeChatDictionary.APP_ID);
		orderInfo.setMch_id(WeChatPayDict.MCH_ID); //商户ID
		orderInfo.setNonce_str(WeiXinUtils.makeNonceStr());//随机字符串 
		orderInfo.setSign_type(WeChatPayDict.SIGN_TYPE); //签名类型
		
		if(idType == idType_wx)
			orderInfo.setTransaction_id(orderId);
		else if(idType == idType_trade)
			orderInfo.setOut_trade_no(orderId);
		
		//计算签名
		String sign = makeSign(orderInfo);
		orderInfo.setSign(sign);
		
		//转xml结果
		String xmlParam = WeiXinXmlUtils.UnifiedOrderInfo2Xml(orderInfo);
		logger.info(xmlParam);
		
		//提交至微信服务器
		String xml = HttpUtils.methodPost(xmlParam, WeChatPayDict.PAY_QUERY_ORDER);
		QueryOrderResult queryOrderResult = WeiXinXmlUtils.xml2QueryOrderResult(xml);
		
		return queryOrderResult;
	}
	
	
	/**
	 * 计算签名
	 * @param orderInfo
	 * @return
	 */
	private String makeSign(BaseOrderInfo orderInfo) {
		
		Map<Object,Object> map = WeiXinUtils.transBean2Map(orderInfo);
		
		SortedMap<Object,Object> sortedMap = new TreeMap<>(map);
		
		return WeiXinUtils.createSign("UTF-8", sortedMap);
	}



	

}
