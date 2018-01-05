package com.zjapl.weixin.transfer.manager;

import com.zjapl.weixin.transfer.bean.AppInfo;
import com.zjapl.weixin.transfer.bean.TransferData;

/**
 * 微信授权用户信息处理
 * @author yangb
 *
 */
public interface AuthExecutor {

	/**
	 * 处理用户信息
	 * @param appinfo
	 * @param transferData
	 * @return
	 */
	public String userInfo(AppInfo appinfo, TransferData transferData);
}
