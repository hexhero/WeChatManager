package com.zjapl.weixin.transfer.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpCollectionHelper {

	public static String get(String accessUrl){
		String json = null;
		try {
			URL url = new URL(accessUrl);
			HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();
			//3.设置HttpUrlConnection对象的一些参数，请求方式，连接的超时时间
			openConnection.setRequestMethod("GET");
			openConnection.setConnectTimeout(10*1000);

			int code = openConnection.getResponseCode();
			if(code == 200){
				InputStream inputStream = openConnection.getInputStream();
				//5.获取网络链接的读取流信息，将流转换成字符串。 ByteArrayOutputStream
				byte[] bytes = IOUtils.toByteArray(inputStream);
				String result = new String(bytes,"utf-8");
				json = result;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public static String post(String accessUrl, Map<String, String> data){
		// 创建默认的httpClient实例.    
		CloseableHttpClient httpclient = HttpClients.createDefault();  
		// 创建httppost    
		HttpPost httppost = new HttpPost(accessUrl);
		// 创建参数队列    
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 
		Set<Entry<String,String>> set = data.entrySet();
		Iterator<Entry<String, String>> iterator = set.iterator();
		while(iterator .hasNext()){
			Map.Entry <String, String > me = iterator.next() ;
			String key = me.getKey() ;
			String value = me.getValue() ;
			formparams.add(new BasicNameValuePair(key, value));
		}

		UrlEncodedFormEntity uefEntity;  
		try {  
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);  
			System.out.println("executing request " + httppost.getURI());  
			CloseableHttpResponse response = httpclient.execute(httppost);  
			try {  
				HttpEntity entity = response.getEntity();  
				if (entity != null) {  
					String str = EntityUtils.toString(entity, "UTF-8");
//					byte[] buf = new byte[2048];
//					input.read(buf);
					System.out.println(str);
					return str;

				}  
			} finally {  
				response.close();  
			}  
		} catch (ClientProtocolException e) {  
			e.printStackTrace();  
		} catch (UnsupportedEncodingException e1) {  
			e1.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		} finally {  
			// 关闭连接,释放资源    
			try {  
				httpclient.close();  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		}
		return null;  
	}
}
