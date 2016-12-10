package com.server.action.sub;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.router.BaseAction;
import com.router.annotation.Namespace;
import com.router.annotation.Read;
import com.router.ret.Render;
import com.router.ret.RenderType;

/**
 * Map/Array/List 类型参数测试 demo
 * @author yunfeng.cheng
 * @create 2016-08-22
 */
public class SubDemoAction extends BaseAction{
	
	//测试 Map 类型参数
	public Render mapTypeTest(@Read(key="srcmap") Map<String,String> srcmap){
		System.out.println("server output srcmap:");
		for(String key : srcmap.keySet()){
			System.out.println(key + "=" + srcmap.get(key));
		}
		
		JSONObject obj = new JSONObject();
		obj.put("code", 0);
		obj.put("msg", "Received your Map request.[from mapTypeTest]");
		
		return new Render(RenderType.JSON, obj.toJSONString());
	}
	
	//测试 Array/List 类型参数
	public Render arrayListTypeTest(@Read(key="ids") Integer[] ids, @Read(key="names") List<String> names){
		System.out.println("server output ids:");
		for(int i=0; i<ids.length; i++){
			System.out.println(ids[i]);
		}
		
		System.out.println("server output names：");
		for(String item : names){
			System.out.println(item);
		}
		
		JSONObject obj = new JSONObject();
		obj.put("code", 0);
		obj.put("msg", "Received your Array/List request.[from arrayListTypeTest()]");
		
		return new Render(RenderType.JSON, obj.toJSONString());
	}
	
	//使用 @Namespace 注解
	@Namespace("/nettp/array/")
	public Render arrayListTypeTestWithNamespace(@Read(key="ids") Integer[] ids, @Read(key="names") List<String> names){
		System.out.println("server output ids:");
		for(int i=0; i<ids.length; i++){
			System.out.println(ids[i]);
		}
		
		System.out.println("server output names：");
		for(String item : names){
			System.out.println(item);
		}
		
		JSONObject obj = new JSONObject();
		obj.put("code", 0);
		obj.put("msg", "Received your Array/List request.[from arrayListTypeTestWithNamespace()]");
		
		return new Render(RenderType.JSON, obj.toJSONString());
	}

}
