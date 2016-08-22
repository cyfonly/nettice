package com.server.action.subaction;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.router.BaseAction;
import com.router.annotation.Read;
import com.router.ret.Render;
import com.router.ret.RenderType;

/**
 * Array/List 参数类型测试类
 * @author yunfeng.cheng
 * @create 2016-08-22
 */
public class PriArrayTest extends BaseAction{
	
	public Render postPriArrayList(@Read(key="ids") Integer[] ids, @Read(key="names") List<String> names){
		System.out.println("server output ids:");
		for(int i=0; i<ids.length; i++){
			System.out.println(ids[i]);
		}
		
		System.out.println("server output names");
		for(String item : names){
			System.out.println(item);
		}
		
		JSONObject obj = new JSONObject();
		obj.put("code", 0);
		obj.put("msg", "had received your Array/List request.");
		
		return new Render(RenderType.JSON, obj.toJSONString());
	}
	
}
