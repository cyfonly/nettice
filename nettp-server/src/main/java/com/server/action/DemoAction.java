package com.server.action;

import com.router.BaseAction;
import com.router.annotation.Namespace;
import com.router.annotation.Read;
import com.router.ret.Render;
import com.router.ret.RenderType;

/**
 * 基本类型参数 测试 demo
 * @author yunfeng.cheng
 * @create 2016-08-22
 */
public class DemoAction extends BaseAction{
	
	//测试基本参数类型
	public Render primTypeTest(@Read(key="id", defaultValue="1" ) Integer id, @Read(key="proj") String proj, @Read(key="author") String author){
		System.out.println("Receive parameters: id=" + id + ",proj=" + proj + ",author=" + author);
		return new Render(RenderType.TEXT, "Received your primTypeTest request.[from primTypeTest]");
	}
	
	//使用 @Namespace 注解
	@Namespace("/nettp/pri/")
	public Render primTypeTestWithNamespace(@Read(key="id") Integer id, @Read(key="proj") String proj, @Read(key="author") String author){
		System.out.println("Receive parameters: id=" + id + ",proj=" + proj + ",author=" + author);
		return new Render(RenderType.TEXT, "Received your primTypeTestWithNamespace request.[from primTypeTestWithNamespace]");
	}
	
	
	
}
