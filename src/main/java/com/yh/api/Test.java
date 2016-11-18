package com.yh.api;

import com.yh.utils.HttpClientUtil;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import java.util.TreeMap;

/**
 * Created by FQ.CHINA on 2016/11/18.
 */
public class Test {

    public static String testLogin()throws Exception{
        TreeMap<String, String> apiparamsMap = new TreeMap<String, String>();
        apiparamsMap.put("username","t1");
        apiparamsMap.put("userpass","20161");
        UrlEncodedFormEntity formEntity = HttpClientUtil.getPostFormEntity(apiparamsMap);
        String res = HttpClientUtil.doHttpPost("http://localhost:8080/yh/api/login",formEntity);
        return res;
    }

    public static void main(String[] args)throws Exception{
        String res = testLogin();
        System.out.println(res);
    }
}
