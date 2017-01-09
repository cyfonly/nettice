package com.cyfonly.nettice.examples.action;

import com.cyfonly.nettice.core.BaseAction;
import com.cyfonly.nettice.core.annotation.Namespace;
import com.cyfonly.nettice.core.annotation.Read;
import com.cyfonly.nettice.core.ret.Render;
import com.cyfonly.nettice.core.ret.RenderType;

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
