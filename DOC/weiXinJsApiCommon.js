/** 
 * yangb 
 * 2017/5/4 
 */
$.initJsApiConf = function(apiList, ready,error){
		
		if(!(apiList instanceof Array)){
			console.info("param type error");
			return;
		}
		
		var _url = window.location.href;
		$.post(contextPath+"/public/wechat/jsconfig",{
			url:_url	
		},function(data){
			var confData = eval( "(" + data + ")" );
			
			var _appId = confData.appid;
			var _noncestr = confData.noncestr;
			var _signature = confData.signature;
			var _timestamp = confData.timestamp;
			var _jsApiList = apiList;
			
			wx.config({
			    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			    appId: _appId, // 必填，公众号的唯一标识
			    timestamp: _timestamp, // 必填，生成签名的时间戳
			    nonceStr: _noncestr, // 必填，生成签名的随机串
			    signature: _signature,// 必填，签名，见附录1
			    jsApiList: _jsApiList // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
			});
			
			wx.ready(function(){
				ready();
			});
			
			wx.error(function(res){
				error(res);
			});
		});
	}
