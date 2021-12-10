package com.enjoyor.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

public class HTTPUtil {
	/**
	 * 向目标url发送post请求
	 * 
	 * @param url
	 * @param params
	 * @return boolean
	 */
	public static boolean post(String url,Map<String, String> params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		//HttpGet httpGet=new HttpGet(url);
		// 参数处理
		if (params != null && !params.isEmpty()) {
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			
			Iterator<Entry<String, String>> it = params.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			
			httpPost.setEntity(new UrlEncodedFormEntity(list, Consts.UTF_8));
		}
		// 执行请求
		try {
			CloseableHttpResponse response = httpclient.execute(httpPost);
			httpPost.releaseConnection();
			httpclient.close();
			if(response.getStatusLine().getStatusCode()==200){
				System.out.println("200..");
				return true;
			}else if(response.getStatusLine().getStatusCode()==500){
				System.out.println("500");
				return false;
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
/*	// 测试
	public static void main(String[] args) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("clientUrl", "httputil");
		post("http://sheefee.com:8080/", params);
	}*/
}