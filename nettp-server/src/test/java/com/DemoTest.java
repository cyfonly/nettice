package com;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 测试 Demo
 * @author yunfeng.cheng
 * @create 2016-07-25
 */
public class DemoTest {
	
	public static void main(String[] args) throws Exception{
		sendGetPriType();
		sendGetPriTypeWithNamespace();
		sendPostJsonArrayAndList();
		sendPostJsonArrayAndListWithNamespace();
		sendPostForm();
		sendPostJsonMap();
	}
	
	/**
	 * 测试 GET 方法基本类型参数请求
	 * @throws Exception
	 */
	private static void sendGetPriType() throws Exception{
		String path = "http://127.0.0.1:8080/nettp/primTypeTest.action?";
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
	 * 测试 GET 方法基本类型参数请求（服务端使用@Namespace注解   @Namespace("/nettp/pri/")）
	 * @throws Exception
	 */
	private static void sendGetPriTypeWithNamespace() throws Exception{
		String path = "http://127.0.0.1:8080/nettp/pri/primTypeTestWithNamespace.action?";
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
	 * 测试 POST 方法 Array/List 参数 请求
	 * @throws Exception
	 */
	private static void sendPostJsonArrayAndList() throws Exception{
		String path = "http://127.0.0.1:8080/nettp/sub/arrayListTypeTest.action";
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
	 * 测试 POST 方法 Array/List 参数 请求（服务端使用@Namespace注解   @Namespace("/nettp/array/")）
	 * @throws Exception
	 */
	private static void sendPostJsonArrayAndListWithNamespace() throws Exception{
		String path = "http://127.0.0.1:8080/nettp/array/arrayListTypeTestWithNamespace.action";
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
	 * 测试 POST 方法 表单参数请求
	 * @throws Exception
	 */
	private static void sendPostForm() throws Exception{
		String path = "http://127.0.0.1:8080/nettp/sub/arrayListTypeTest.action";
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
	
	/**
	 * 测试 POST 方法 Map 参数 请求
	 * @throws Exception
	 */
	private static void sendPostJsonMap() throws Exception{
		String path = "http://127.0.0.1:8080/nettp/sub/mapTypeTest.action";
		JSONObject obj = new JSONObject();
		Map<String, String> srcmap = new HashMap<String, String>();
		srcmap.put("proj", "nettp");
		srcmap.put("author", "cyfonly");
		obj.put("srcmap", srcmap);
		String jsonStr = obj.toJSONString();
		byte[] data = jsonStr.getBytes();
        java.net.URL url = new java.net.URL(path);
        java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
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
	
}
