package com.zjapl.weixin.transfer.bean;

/**
 * 订单的信息
 * 给微信提交的订单信息
 * @author yangb
 *
 */
public class BaseOrderInfo {

	/**
	 * 微信支付分配的公众账号ID（企业号corpid即为此appId）
	 * 必填
	 */
	private String appid;
	
	/**
	 * 微信支付分配的商户号
	 * 必填
	 */
	private String mch_id;
	
	/**
	 * 随机字符串，长度要求在32位以内。推荐随机数生成算法
	 * 必填
	 */
	private String nonce_str;
	
	/**
	 * 通过签名算法计算得出的签名值，详见签名生成算法
	 * 必填
	 */
	private String sign;
	
	/**
	 * 签名类型，默认为MD5，支持HMAC-SHA256和MD5。
	 */
	private String sign_type;

	/**
	 * 微信的订单号，建议优先使用
	 */
	private String transaction_id;
	
	/**
	 * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。详见商户订单号
	 * 必填
	 */
	private String out_trade_no;
	
	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	
}
