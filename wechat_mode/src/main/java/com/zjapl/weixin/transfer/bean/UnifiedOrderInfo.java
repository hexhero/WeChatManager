package com.zjapl.weixin.transfer.bean;

import java.io.Serializable;

/**
 * 统一下单信息
 * 	给微信提交预支付交易单信息
 * @author yangb
 *
 */
public class UnifiedOrderInfo extends BaseOrderInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
	 */
	private String device_info;

	/**
	 * 商品简单描述，该字段请按照规范传递，具体请见参数规定(128)
	 * 必填
	 */
	private String body;
	/**
	 * 	商品详情 单品优惠字段(暂未上线)(6000)
	 */
	private String detail;
	/**
	 * 	附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
	 */
	private String attach;
	/**
	 * 符合ISO 4217标准的三位字母代码，默认人民币：CNY，详细列表请参见货币类型
	 */
	private String fee_type;
	/**
	 * 订单总金额，单位为分，详见支付金额
	 * 必填
	 */
	private Integer total_fee;
	/**
	 * APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
	 * 必填
	 */
	private String spbill_create_ip;
	/**
	 * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
	 */
	private String time_start;
	/**
	 * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则.注意：最短失效时间间隔必须大于5分钟
	 */
	private String time_expire;
	/**
	 * 商品标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠
	 */
	private String goods_tag;
	/**
	 * 通知地址  异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
	 * 必填
	 */
	private String notify_url;
	/**
	 * 交易类型  取值如下：JSAPI，NATIVE，APP等，说明详见参数规定
	 * 必填
	 */
	private String trade_type;
	/**
	 * 商品ID
	 */
	private String product_id;
	/**
	 * 指定支付方式 上传此参数no_credit--可限制用户不能使用信用卡支付
	 */
	private String limit_pay;
	/**
	 * 用户标识 trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识
	 */
	private String openid;
	
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public Integer getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	public String getTime_start() {
		return time_start;
	}
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}
	public String getTime_expire() {
		return time_expire;
	}
	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}
	public String getGoods_tag() {
		return goods_tag;
	}
	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getLimit_pay() {
		return limit_pay;
	}
	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
}
