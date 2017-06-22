package com.zjapl.weixin.transfer.ctrl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjapl.weixin.transfer.bean.AppInfo;
import com.zjapl.weixin.transfer.bean.TransferData;
import com.zjapl.weixin.transfer.bean.WebAccessToken;
import com.zjapl.weixin.transfer.dict.WeChatEventDict;
import com.zjapl.weixin.transfer.dict.WeChatUrlDict;
import com.zjapl.weixin.transfer.global.WeChatContent;
import com.zjapl.weixin.transfer.helper.WeChatJsTicketHelper;
import com.zjapl.weixin.transfer.helper.WeChatWebAuthTokenHelper;
import com.zjapl.weixin.transfer.utils.HttpCollectionHelper;
import com.zjapl.weixin.transfer.utils.StringUtils;
import com.zjapl.weixin.transfer.utils.WeiXinSignUtils;

/**
 * 微信网页接口
 * @author yangb
 *
 */
@Controller
@RequestMapping("/public/web")
public class WeChatWebController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 微信页面回调. 只获取用户的openid(此链接跳转不需要任何授权) 回调速度快,不用拉取用户信息
	 * @param code
	 * @param state
	 * @return
	 */
	@RequestMapping("/openid")
	@ResponseBody
	public String openidAuth(String code, String state,HttpServletResponse resp,HttpServletRequest req){
		return webAuth(code, state, WeChatEventDict.SCOPE_SNSAPI_BASE,resp,req);
	}
	
	/**
	 * 微信页面回调. 需要获取用户的详情(非公众号粉丝跳转此链接需要经过授权页面) 
	 * @param code
	 * @param state
	 * @return
	 */
	@RequestMapping("/userinfo")
	@ResponseBody
	public String userinfoAuth(String code, String state, HttpServletResponse resp, HttpServletRequest req){
		return webAuth(code, state, WeChatEventDict.SCOPE_SNSAPI_USERINFO,resp,req);
	}
	
	/**
	 * 计算JsApi签名信息
	 * @param url
	 * @param appid
	 * @return
	 */
	@RequestMapping("/jsconfig")
	@ResponseBody
	public Map<Object, Object> jsConfig(String url,String appid){
		Map<Object, Object> config = WeiXinSignUtils.sign(WeChatJsTicketHelper.obtainTicket(appid), url);
		config.put("appid", appid);
		return config;
	}
	
	/**
	 * 微信页面授权重定向处理
	 * @param code
	 * @param state
	 * @param scope
	 * @return
	 */
	private String webAuth(String code, String state, String scope, HttpServletResponse resp, HttpServletRequest req) {
		String[] info = resolveState(state);
		if(info != null){
			String childState = info[0];
			String appname = info[1];
			AppInfo appinfo = WeChatContent.obtainAppInfo(appname);//获取AppInfo
			WebAccessToken webToken = WeChatWebAuthTokenHelper.getWebToken(appinfo, code);//根据code获取用户的openid
			if(webToken != null && webToken.getOpenid() != null){
				String openid = webToken.getOpenid();
				//封装请求子系统数据
				TransferData transferData = new TransferData();
				transferData.setState(childState); //子系统的state
				transferData.setOpenid(openid);  //用户的openid
				transferData.setScope(webToken.getScope());//scope
				
				switch (scope) {
				case WeChatEventDict.SCOPE_SNSAPI_BASE:
					break;
					
				case WeChatEventDict.SCOPE_SNSAPI_USERINFO:
					//获取用户详细信息
					String userinfo = obtainUserInfo(webToken, openid);//获取用户详情
					transferData.setData(userinfo);//将用户详情加入到参数中
					break;
				default:
					break;
				}
				
				//重定向到子系统
				try {
					comunication(appinfo,transferData,resp,req);
				} catch (IOException e) {
					e.printStackTrace();
					return "<h2>页面请求失败</h2>";
				}
			}
		}
		return null;
	}


	/**
	 * 重定向到子系统
	 * @param appinfo
	 * @param transferData
	 * @return
	 * @throws IOException 
	 */
	private void comunication(AppInfo appinfo, TransferData transferData,HttpServletResponse resp, HttpServletRequest req) throws IOException {
		String url = appinfo.getCallbackUrl()+WeChatEventDict.CALLBACK_WEB_AUTH + transferData.parseParam("utf-8");
		logger.info("重定向地址", url);
		resp.sendRedirect(url); //重定向到子系统
	}
	
/*	//不连接子系统, 直接返回openid
	private String comunication(AppInfo appinfo, TransferData transferData) {
		String resultStr = "<script language='javascript'> window.location = 'http://itapl.com/test-react-douban/index.html?openid=" + transferData.getOpenid() + "'; </script>";
		return resultStr;
	}
*/	
	
	/**
	 * 获取用户详情
	 * @return
	 */
	private String obtainUserInfo(WebAccessToken webToken,String openid){
		String accessUrl = MessageFormat.format(WeChatUrlDict.WEB_PULL_USERINFO_URL,webToken.getAccess_token(),openid);
		String json = HttpCollectionHelper.get(accessUrl);
		return json;
	}
	
	/**
	 * 解析state
	 * @param state
	 * @return
	 */
	private String[] resolveState(String state){
		if(!StringUtils.isEmpty(state)){
			String[] info = state.split("\\*");
			if(info.length == 2){
				return info;
			}
		}
		return null;
	}
	
//	@Autowired
//	private IWeiXinTokenService tokenService;
//	
//	@Resource
//	private IUserService wxUserService;
//	
//	@Resource
//	private SysUserService sysUserService;
//
//	/**
//	 * 微信页面入口.(不跳转注册页面)
//	 * @param code
//	 * @param state
//	 * @param session
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/page2")
//	public ModelAndView page2(String code, String state, HttpSession session) throws Exception{
//		AccessToken	token = tokenService.obtainWebAccessToken(code);
//		if(token == null || token.getOpenId() == null){
//			ModelAndView mv = new ModelAndView("error"); //跳转到登录页面.
//			return mv;
//		}
//		
//		// 查询微信用户.
//		// 获取微信用户信息.
//		QueryUserVo userVo = new QueryUserVo();
//		userVo.setOpenId(token.getOpenId());
//		UserVo wxUser = wxUserService.queryUserByOne(userVo).getData();
//		//如果该用户不存在,创建之
//		if(wxUser == null){
//			wxUser = tokenService.obtainUserInfo(token.getOpenId(),token.getAccessToken());
//			wxUserService.addOrEditWeiXinUser(wxUser);
//		}else if(wxUser.getNickName() == null){
//			//如果信息不完善,完善之
//			UserVo newWxUser = tokenService.obtainUserInfo(token.getOpenId(),token.getAccessToken());
//			BeanUtils.copyPropertiesIgnoreNullValue(newWxUser, wxUser);
//			wxUserService.addOrEditWeiXinUser(wxUser);
//		}
//		
//		ModelAndView mv = new ModelAndView(state);
//		session.setAttribute("wxUser", wxUser);// 将微信用户保存在 session 中
//		
//		if(wxUser != null && wxUser.getUserid() != null){
//			String message = null;
//			//用户是系统用户,直接登录.
//			//解析密码
//			String password = wxUser.getPassword();
//			password = AESUtils.aesDecrypt(password); 
//			
//			Long sysUserId =  wxUser.getUserid(); //获取系统用户Id
//			SysUser sysUser = sysUserService.querySysUser(sysUserId).getData();//获取系统用户
//			
//			if(password == null){
//				password = "888888";
//			}
//			
//			message = WeiXinUtils.shiroLogin(sysUser.getUsername(), "888888"); //用户登录,返回登录结果,如果失败,返回失败信息,成功则为null
//			
//			if(message == null){
//				//登录成功
//				mv.addObject("openid", wxUser.getOpenId());
//				mv.addObject("message","ok");
//				mv.addObject("sysUser", sysUser);
//				
//			}else{
//				//登录失败
//				mv.addObject("openid", wxUser.getOpenId());
//				mv.addObject("message",message);
//				mv.addObject("state", state);
//				return mv;
//			}
//			
//		}
//		//跳转的页面可以使用 微信带回来的参数指定响应的页面.
//		return mv;
//	}
//	
//	/**
//	 * 微信页面入口.
//	 * @param code
//	 * @param state
//	 * @param session
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/page")
//	public ModelAndView page(String code, String state, HttpSession session) throws Exception{
//		AccessToken	token = tokenService.obtainWebAccessToken(code);
//		if(token == null){
//			ModelAndView mv = new ModelAndView("error"); //跳转到登录页面.
//			return mv;
//		}
//		
//		// 查询微信用户.
//		// 获取微信用户信息.
//		QueryUserVo userVo = new QueryUserVo();
//		userVo.setOpenId(token.getOpenId());
//		UserVo wxUser = wxUserService.queryUserByOne(userVo).getData();
//		//如果该用户不存在,创建之
//		if(wxUser == null){
//			wxUser = tokenService.obtainUserInfo(token.getOpenId(),token.getAccessToken());
//			wxUserService.addOrEditWeiXinUser(wxUser);
//		}else if(wxUser.getNickName() == null){
//			//如果信息不完善,完善之
//			UserVo newWxUser = tokenService.obtainUserInfo(token.getOpenId(),token.getAccessToken());
//			BeanUtils.copyPropertiesIgnoreNullValue(newWxUser, wxUser);
//			wxUserService.addOrEditWeiXinUser(wxUser);
//		}
//		
//		if(wxUser == null || wxUser.getUserid() == null){
//			//用户没有系统账户, 不能登录
//			//用户还不是正式用户
////			ModelAndView mv = new ModelAndView("index");
//			ModelAndView mv = new ModelAndView("regist"); //跳转到登录页面.
//			mv.addObject("openid", token.getOpenId());
//			mv.addObject("state", state);
//			return mv;
//			
//		}else{
//			String message = null;
//			//用户是系统用户,直接登录.
//			//解析密码
//			String password = wxUser.getPassword();
//			password = AESUtils.aesDecrypt(password); 
//			
//			Long sysUserId =  wxUser.getUserid(); //获取系统用户Id
//			SysUser sysUser = sysUserService.querySysUser(sysUserId).getData();//获取系统用户
//			
//			if(password == null){
//				password = "888888";
//			}
//			
//			message = WeiXinUtils.shiroLogin(sysUser.getUsername(), "888888"); //用户登录,返回登录结果,如果失败,返回失败信息,成功则为null
//			
//			if(message == null){
//				//登录成功
//				// 跳转到登录成功页面
//				//跳转的页面可以使用 微信带回来的参数指定响应的页面.
//				ModelAndView mv = new ModelAndView(state);
//				session.setAttribute("wxUser", wxUser);// 将微信用户保存在 session 中
//				mv.addObject("openid", wxUser.getOpenId());
////				mv.addObject("wxUser", wxUser);
//				mv.addObject("sysUser", sysUser);
//				return mv;
//				
//			}else{
//				//登录失败
//				// 跳转到注册页面
//				ModelAndView mv = new ModelAndView("regist");
//				mv.addObject("openid", wxUser.getOpenId());
//				mv.addObject("message",message);
//				mv.addObject("state", state);
//				return mv;
//			}
//		}
//		
//	}
//	
//	/**
//	 * 微信 JsApi Config参数
//	 * 
//	 * @return
//	 */
//	@RequestMapping("/jsApiConf")
//	@ResponseBody
//	public Map<Object, Object> jsApiConfig(String url, HttpServletRequest request){
//		if(TextUtils.isEmpty(url))
//			url =request.getRequestURL().toString();
//		
//		Map<Object, Object> jsConfig =tokenService.jsApiConfig(url);
//		return jsConfig;
//	}
//	
//	
//	/**
//	 * 测试页面一
//	 * 
//	 * @return
//	 */
//	@RequestMapping("/test1")
//	public ModelAndView t1(String code, String state, HttpSession session){
//		
//		AccessToken token = (AccessToken) session.getAttribute("WebToken");
//		if(token == null){
//			token = tokenService.obtainWebAccessToken(code);
//			session.setAttribute("WebToken", token);
//		}
//		
//		ModelAndView mv = new ModelAndView("test1");
//		mv.addObject("token", token);
//		return mv;
//	}
//	
//	@RequestMapping("/test2")
//	public ModelAndView t2(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session){
//		
//		AccessToken token = (AccessToken) session.getAttribute("WebToken");
//		if(token == null){
//			token = tokenService.obtainWebAccessToken(code);
//			session.setAttribute("WebToken", token);
//		}
//		
//		UserVo userinfo = tokenService.obtainUserInfo(token.getOpenId(),token.getAccessToken());
//		
//		ModelAndView mv = new ModelAndView("test2");
//		mv.addObject("token", token);
//		mv.addObject("userinfo", userinfo);
//		return mv;
//	}
//	
}
