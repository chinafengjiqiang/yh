package com.yh.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	private static final  Log log = LogFactory.getLog(HttpClientUtil.class);
	
	public static HttpClient getClient() {
		HttpClient client = HttpClients.createDefault();// 获取HttpClient对象
		return client;
	}

	private static void releaseConnection(HttpRequestBase request) {
		if (request != null) {
			request.releaseConnection();
		}
	}

	private static void showResponse(HttpResponse response) throws ParseException,
			IOException {
		log.debug("requset result:");
		log.debug(response.getStatusLine().toString());// 响应状态
		log.debug("-----------------------------------");

		Header[] heard = response.getAllHeaders();// 响应头
		log.debug("response heard:");
		for (int i = 0; i < heard.length; i++) {
			log.debug(heard[i]);
		}
		log.debug("-----------------------------------");
		HttpEntity entity = response.getEntity();// 响应实体/内容
		log.debug("response content length:" + entity.getContentLength());
		log.debug("response content:");
	
		log.debug(EntityUtils.toString(entity));
		
	}

	public static String doHttpGet(String uri)throws Exception {// get方法提交
		HttpGet getMethod = null;
		getMethod = new HttpGet(uri);// 获取HttpGet对象，使用该对象提交get请求
		Object[] response= exctueRequest(getMethod);
		String resString = "";
		if(response!=null){
			resString = (String)response[2];
		}
	    return resString;
	}
	
	public static String doHttpPost(String uri,HttpEntity entity)throws Exception {// post方法提交
		    HttpPost postMethod = null;
			postMethod = new HttpPost(uri);
			postMethod.setEntity(entity);//设置请求实体，例如表单数据
			Object[] response = exctueRequest(postMethod); // 执行请求，获取HttpResponse对象
			String resPostString = "";
			if(response!=null){
				resPostString = (String)response[2];
			}
		    return resPostString;

	}

	public static Object[] exctueRequest(HttpRequestBase request){
	  HttpResponse response=null;
	  Object[] resObjs = null;
	  try {
		response=getClient().execute(request);//执行请求，获取HttpResponse对象
		//showResponse(response);
		int statuscode = response.getStatusLine().getStatusCode();//处理重定向
		if((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) || (statuscode == HttpStatus.SC_MOVED_PERMANENTLY)
				|| (statuscode == HttpStatus.SC_SEE_OTHER) || (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)){
			
			Header redirectLocation=response.getFirstHeader("Location");
			String newuri=redirectLocation.getValue();
		    if((newuri!=null)||(!"".equals(newuri))){
		      request.setURI(new URI(newuri));
		      response=getClient().execute(request);
		     // showResponse(response);
		   }else {
			   log.debug("Invalid redirect");
		   }
	  
		}
		resObjs = new Object[3];
	    resObjs[0] = response.getStatusLine().toString();
	    resObjs[1] = response.getAllHeaders();// 响应头
	    HttpEntity entity = response.getEntity();// 响应实体/内容
	    resObjs[2] = EntityUtils.toString(entity);
	} catch (Exception e) {
		e.printStackTrace();
	} finally{
		releaseConnection(request);//释放连接
	}
	  return resObjs;
  }

   public static UrlEncodedFormEntity getPostFormEntity(TreeMap<String,String> paramsMap){
	    List<NameValuePair> formparams = new ArrayList<NameValuePair>();// 设置表格参数
	    if(paramsMap!=null){
	       Iterator<Entry<String,String>> iter = paramsMap.entrySet().iterator();
	       while(iter.hasNext()){
	    	   Entry<String,String> entry = iter.next();
	    	   String key = entry.getKey();
	    	   String value = entry.getValue();
	    	   formparams.add(new BasicNameValuePair(key, value));
	       }
	    }
		UrlEncodedFormEntity uefEntity = null;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");//获取实体对象
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return uefEntity;
   }


	public static String doJsonHttpPost(String uri,String jsonData)throws Exception {// post方法提交
		HttpPost postMethod = null;
		postMethod = new HttpPost(uri);
		StringEntity s = new StringEntity(jsonData);
		s.setContentEncoding("UTF-8");
		s.setContentType("application/json");//发送json数据需要设置contentType
		postMethod.setEntity(s);
		Object[] response = exctueRequest(postMethod); // 执行请求，获取HttpResponse对象
		String resPostString = "";
		if(response!=null){
			resPostString = (String)response[2];
		}
		return resPostString;

	}
	
   
	public static void main(String[] args)throws Exception {
		
		String message = "测试短息发送OK";
		message = java.net.URLEncoder.encode(message,"utf-8");
		/*String url = "http://192.168.18.4/sms/send?sn=iactive.sms&pass=EF66656A10B2FEF381B825A76590740B&mphones=13520405178&message="+message;
		String res = doHttpGet(url);
		System.out.println(res);*/
		
		
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();// 设置表格参数
		formparams.add(new BasicNameValuePair("sn", "iactive.sms"));
		formparams.add(new BasicNameValuePair("pass", "EF66656A10B2FEF381B825A76590740B"));
		formparams.add(new BasicNameValuePair("mphones", "13520405178"));
		formparams.add(new BasicNameValuePair("message", "message"));
		
		UrlEncodedFormEntity uefEntity = null;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");//获取实体对象
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String res = doHttpPost("http://192.168.18.4/sms/send", uefEntity);
		System.out.println(res);
	}
}
