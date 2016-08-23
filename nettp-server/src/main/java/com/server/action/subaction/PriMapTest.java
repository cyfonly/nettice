package com.server.action.subaction;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.router.BaseAction;
import com.router.annotation.Read;
import com.router.ret.Render;
import com.router.ret.RenderType;

/**
 * Map 参数类型测试类
 * @author yunfeng.cheng
 * @create 2016-08-22
 */
public class PriMapTest extends BaseAction{

	public Render postPriMap(@Read(key="srcmap") Map<String,String> srcmap){
		System.out.println("server output srcmap:");
		for(String key : srcmap.keySet()){
			System.out.println(srcmap.get(key));
		}
		
		JSONObject obj = new JSONObject();
		obj.put("code", 0);
		obj.put("msg", "had received your Map request.");
		
		return new Render(RenderType.JSON, obj.toJSONString());
	}
	
}
