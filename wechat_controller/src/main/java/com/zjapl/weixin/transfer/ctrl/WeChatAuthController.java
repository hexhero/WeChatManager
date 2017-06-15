package com.zjapl.weixin.transfer.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 微信认证接口
 * @author yangb
 *
 */
@Controller
@RequestMapping("/public/auth")
public class WeChatAuthController {

//	@Resource
//	private ISysUserService wxsysuser;
//	
//	/**
//	 * 用户注册页面
//	 * 
//	 * @return
//	 */
//	@RequestMapping("/registPage")
//	public ModelAndView registPage(String openid){
//		ModelAndView mv = new ModelAndView("regist");
//		mv.addObject("openid", openid);
//		return mv;
//	}
//	
//	/**
//	 * 用户注册并登录
//	 * 
//	 * @return
//	 */
//	@RequestMapping("/regist")
//	@ResponseBody
//	public ResultEx regist(String openid, String telphone){
//		return wxsysuser.wxUserRegist(openid,telphone);
//	}
//	
//	/**
//	 * 用户关联页面
//	 * 
//	 * @return
//	 */
//	@RequestMapping("/relationPage")
//	public ModelAndView relationPage(String openid){
//		ModelAndView mv = new ModelAndView("relation");
//		mv.addObject("openid", openid);
//		return mv;
//	}
//	
//	/**
//	 * 用户关联并登录.
//	 * 
//	 * @return
//	 * @throws Exception 
//	 */
//	@RequestMapping("/relation")
//	@ResponseBody
//	public ResultEx relation(String openid, String username, String password) throws Exception{
//		return wxsysuser.relation(openid, username, password);
//	}
//
//	
//	/**
//	 * 简单标识 认证
//	 * 
//	 * @return
//	 */
//	@RequestMapping("/base")
//	public void base(){
//		
//		
//	}
//	
//	/**
//	 * 用户详细信息 认证
//	 * 
//	 * @return
//	 */
//	@RequestMapping("/userinfo")
//	public void userinfo(){
//		
//		
//	}
	
	
}
