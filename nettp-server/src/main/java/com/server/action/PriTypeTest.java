package com.server.action;

import com.router.BaseAction;
import com.router.annotation.Namespace;
import com.router.annotation.Read;
import com.router.ret.Render;
import com.router.ret.RenderType;

/**
 * 基本类型参数类型测试类
 * @author yunfeng.cheng
 * @create 2016-08-22
 */
public class PriTypeTest extends BaseAction{
	
	public Render returnText(@Read(key="id") Integer id, @Read(key="proj") String proj, @Read(key="author") String author){
		System.out.println("recv params: id=" + id + ",proj=" + proj + ",author=" + author);
		return new Render(RenderType.TEXT, "had received your priType request.");
	}
	
	@Namespace("/nettp/pri/")
	public Render returnTextUseNamespace(@Read(key="id") Integer id, @Read(key="proj") String proj, @Read(key="author") String author){
		//do something
		return new Render(RenderType.TEXT, "returnTextUseNamespace in [PriTypeTest]");
	}
	
}
