# WeChatManager
## 微信多公众号管理平台, 支付等功能.
## 框架图:
![Image frameworkImg](https://raw.githubusercontent.com/cn-Gongfu/WeChatManager/master/DOC/%E5%BE%AE%E4%BF%A1%E5%A4%9A%E5%85%AC%E4%BC%97%E5%8F%B7%E7%AE%A1%E7%90%86%E6%A1%86%E6%9E%B6%E5%9B%BE.png)

## 引入项目的时候的操作初始化
```
@Controller
public class WeiXinInit {

	@Autowired
	private ParticipantsController ctrl;
	
	@Autowired
	private MessageExecutor message;
	
	@PostConstruct
	public void init(){
		AppInfo appInfo = new AppInfo();
		appInfo.setAppid("wxe88656209c6cf6fb");
		appInfo.setAppname("云尚名宿");
		appInfo.setSecret("ef0960c8b097ef06cf02e2d9d5762a71"); //密匙
		
		WeChatGlobalInit.initApp(Arrays.asList(appInfo));
		WeChatGlobalInit.registAuthExecutor(ctrl);  //初始化认证信息处理器
		WeChatGlobalInit.registEventExecutor(message); //初始化消息处理器
	}
}

@Controller
@RequestMapping("/participants")
public class ParticipantsController implements AuthExecutor{

	@Override
	public String userInfo(AppInfo appinfo, TransferData transferData) {
		System.out.println(appinfo);
		System.out.println(transferData);
		return "1234";
	}
}


@Controller
public class MessageExecutor extends  EventAbstract{

	@Override
	public String getAppName() {
		return "云尚名宿";
	}

	@Override
	public EventInfo handleText(EventInfo reqInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventInfo handleVoice(EventInfo reqInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventInfo handleImage(EventInfo reqInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventInfo handleVideo(EventInfo reqInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventInfo handleShortVideo(EventInfo reqInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventInfo handleLocation(EventInfo reqInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventInfo handleLink(EventInfo reqInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventInfo subscribe(String openid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unsubscribe(String openid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EventInfo scan(String openid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventInfo custom(EventInfo event) {
		// TODO Auto-generated method stub
		return null;
	}

}


```
