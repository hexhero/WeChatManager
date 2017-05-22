package com.zjapl.weixin.transfer.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;

import com.zjapl.weixin.transfer.dict.WeChatEventDict;
import com.zjapl.weixin.transfer.dict.WeChatPayDict;
import com.zjapl.weixin.transfer.dict.WeChatUrlDict;

/**
 * 工具类
 * @author yangb
 *
 */
public class WeiXinUtils {

	
	/**
	 * 组装微信Web授权的Url
	 * @param appid
	 * @param scope  例:WeChatEventDict.SCOPE_SNSAPI_BASE
	 * @param state 用户自定义参数, 会在回调接口中传递回去
	 * @param appname: app名称
	 * @return
	 */
	public static String createWebAuthURL(String appid, String scope, String state, String appname){
		String authType = null;
		switch (scope) {
		case WeChatEventDict.SCOPE_SNSAPI_BASE:
			authType = "openid";
			break;
		case WeChatEventDict.SCOPE_SNSAPI_USERINFO:
			authType = "userinfo";
			break;
		default:
			authType = "userinfo";
			break;
		}
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri={1}public%2fweb%2f{2}&response_type=code&scope={3}&state={4}*{5}#wechat_redirect";
		return MessageFormat.format(url,appid,UrlEncode(WeChatUrlDict.SERVER_URL),authType,scope,state,appname);
		
	}
	
	/**
	 * url编码
	 * @param url
	 * @return
	 */
	public static String UrlEncode(String url){
		try {
			return URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取客户端真实IP
	 * @param request
	 * @return
	 */
	public static String getRemoteHost(javax.servlet.http.HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
	
	/**
	 * 将日期转为秒
	 * @return
	 */
	public static String paseDateToSecond(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(date);
	}
	
	/**
	 * 随机字符串，长度要求在32位以内。
	 * @return
	 */
	public static String makeNonceStr(){
		String uuid = UUID.randomUUID().toString();//用来生成数据库的主键id非常不错。。
		return uuid.replace("-", "").toUpperCase();
	}
	
    /**
     * Map转bean
     * @param map
     * @param obj
     */
    public static void transMap2Bean(Map<Object, Object> map, Object obj) {  
        if (map == null || obj == null) {  
            return;  
        }  
        try {  
            BeanUtils.populate(obj, map);  
        } catch (Exception e) {  
            System.out.println("transMap2Bean2 Error " + e);  
        }  
    } 
	
    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map  
    /**
     * Bean 转 Map
     * @param obj
     * @return
     */
    public static Map<Object, Object> transBean2Map(Object obj) {  
  
        if(obj == null){  
            return null;  
        }          
        Map<Object, Object> map = new HashMap<Object, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
  
                    map.put(key, value);  
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
  
        return map;  
  
    }  
    
    /** 
     * 微信支付签名算法sign 
     * @param characterEncoding 
     * @param parameters 
     * @return 
     */  
    @SuppressWarnings({ "rawtypes" })  
    public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters){  
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v)   
                    && !"sign".equals(k) && !"key".equals(k)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }  
        sb.append("key=" + WeChatPayDict.KEY);  
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();  
        return sign;  
    }  
    
    /** 
     * 微信JsAPI签名算法
     * @param characterEncoding 
     * @param parameters 
     * @return 
     */  
    @SuppressWarnings({ "rawtypes" })  
    public static String createJsAPISign(String characterEncoding,SortedMap<Object,Object> parameters){  
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v)   
                    && !"sign".equals(k) && !"key".equals(k)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }
        String str = sb.toString();
        str = str.substring(0,str.length() - 1);
        String sign = MD5Util.MD5Encode(str, characterEncoding).toUpperCase();
        return sign;  
    }  

	/**
	 * 在Shiro中登录.
	 * @param message
	 * @param username
	 * @param password
	 * @return
	 */
/*	public static String shiroLogin( String username, String password) {
		UsernamePasswordToken shiroToken = new UsernamePasswordToken(username, password);
		String message = null;
		try {
			SecurityUtils.getSubject().login(shiroToken);
		} catch (AuthenticationException e) {
			message  = ExceptionResolver.getFailureMessage(e);
		}
		return message;
	}*/

}
