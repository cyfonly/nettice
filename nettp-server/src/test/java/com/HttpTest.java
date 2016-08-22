package com;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 测试类
 * @author yunfeng.cheng
 * @create 2016-07-25
 */
public class HttpTest {
	
	public static void main(String[] args) throws Exception{
//		sendGetPriType();
//		sendPostJsonArray();
		sendPostForm();
	}
	
	/**
	 * 测试 GET 方法 基本类型参数 请求
	 * @throws Exception
	 */
	private static void sendGetPriType() throws Exception{
		String path = "http://127.0.0.1:8080/nettp/returnText.action?";
		String getUrl = path + "id=10001&proj=nettp&author=yunfengCheng";
        java.net.URL url = new java.net.URL(getUrl);
        java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoOutput(true);
        conn.connect();
        if(conn.getResponseCode() == 200){
        	BufferedReader in = new BufferedReader(new InputStreamReader((InputStream) conn.getInputStream(), "UTF-8"));
        	String msg = in.readLine();
        	System.out.println("msg: " + msg);
            in.close();
        }
        conn.disconnect();
	}
	
	/**
	 * 测试 POST 方法 Array/List参数 请求
	 * @throws Exception
	 */
	private static void sendPostJsonArray() throws Exception{
		String path = "http://127.0.0.1:8080/nettp/sub/postPriArrayList.action";
		JSONObject obj = new JSONObject();
		int[] ids = {1,2,3};
		List<String> names = new ArrayList<String>();
		names.add("aaaa");
		names.add("bbbb");
		obj.put("ids", ids);
		obj.put("names", names);
		String jsonStr = obj.toJSONString();
		byte[] data = jsonStr.getBytes();
        java.net.URL url = new java.net.URL(path);
        java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
        OutputStream outStream = conn.getOutputStream();
        outStream.write(data);
        outStream.flush();
        outStream.close();
        if(conn.getResponseCode() == 200){
        	BufferedReader in = new BufferedReader(new InputStreamReader((InputStream) conn.getInputStream(), "UTF-8"));
        	String msg = in.readLine();
        	System.out.println("msg: " + msg);
            in.close();
        }
        conn.disconnect();
	}
	
	/**
	 * 测试 POST 方法 表单参数 请求
	 * @throws Exception
	 */
	private static void sendPostForm() throws Exception{
		String path = "http://127.0.0.1:8080/nettp/sub/postPriArrayList.action";
		String parm = "ids=1&ids=2&names=aaaa&names=bbbb";
		byte[] data = parm.getBytes();
        java.net.URL url = new java.net.URL(path);
        java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
        OutputStream outStream = conn.getOutputStream();
        outStream.write(data);
        outStream.flush();
        outStream.close();
        if(conn.getResponseCode() == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader((InputStream) conn.getInputStream(), "UTF-8"));
            String msg = in.readLine();
        	System.out.println("msg: " + msg);
            in.close();
        }
        conn.disconnect();
	}
	
}
