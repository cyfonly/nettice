![design](https://github.com/cyfonly/nettice/blob/master/pictures/nettice.png "nettice")  
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/cyfonly/nettice/blob/master/LICENCE)  
基于netty http协议栈的轻量级 MVC 组件
  
# 特性
1. 接收装配请求数据、流程控制和渲染数据
2. `URI` 到方法直接映射，以及命名空间

  
# 功能
1. 对 `HttpRequest` 的流程控制
2. 像普通方法一样处理 `http` 请求
3. 对请求的数据自动装配，支持`基本类型`、`List`、`Array` 和 `Map`
4. 提供 `Render` 方法渲染并写回响应，支持多种 `Content-Type`
5. 支持可配置的命名空间
  
  
# 请求分发整体设计
![design](https://github.com/cyfonly/nettp/blob/master/pictures/design.png "design.png")
  
  
# Action请求处理
![action_process](https://github.com/cyfonly/nettp/blob/master/pictures/action_process.png "action_process.png")  
  
# Usage  
#### 1. Maven  
> 刚上传至Maven中央仓库，预计明早八点（2017-01-10 08:00）可用。  

```
<dependency>
  <groupId>com.github.cyfonly</groupId>
  <artifactId>nettice-core</artifactId>
  <version>1.0.0</version>
</dependency>
```   
#### 2. In your project code
```
.addLast("dispatcher",new ActionDispatcher())
```  

# `URI` 映射和命名空间
使用方法名作为 `URI` 映射关键字，如果项目中存在同样名字的方法会产生冲突，开发者可以使用 `@Namespaces` 注解或者在 `nettice.xml` 配置中添加 `namespaces` 来修改 `URI` 映射，以规避此问题。  

例如 `com.server.action.DemoAction` 提供了 `returnTextUseNamespace()` 方法，`com.server.action.sub.SubDemoAction` 也提供了 `returnTextUseNamespace()` 方法，但两个方法实现不同功能。`nettice` 组件默认使用方法名进行 `URI` 映射，那么上述两个 `returnTextUseNamespace()` 方法会产生冲突，开发者可以使用 `@Namespace` 注解修改 `URI` 映射：  
```
package com.server.action;
public class DemoAction extends BaseAction{
  	@Namespace("/nettp/demo/")
  	public Render returnTextUseNamespace(@Read(key="id") Integer id, @Read(key="proj") String proj){
    		//do something
    		return new Render(RenderType.TEXT, "returnTextUseNamespace in [DemoAction]");
  	}
}
``` 
  
```
package com.server.action.sub;
public class SubDemoAction extends BaseAction{
  	@Namespace("/nettp/subdemo/")
	public Render returnTextUseNamespace(@Read(key="ids") Integer[] ids, @Read(key="names") List<String> names){
		//do something
		return new Render(RenderType.TEXT, "returnTextUseNamespace in [SubDemoAction]");
	}
}
```

也可以在 `nettice.xml` 中设置:
```
<namespaces>
  <namespace name="/nettp/demo/" packages="com.server.action.*"></namespace>
  <namespace name="/nettp/subdemo/" packages="com.server.action.sub"></namespace>
</namespaces>
```

# 接收装配请求数据
使用Read注解可以自动装配请求数组，支持不同的类型（`基本类型`、`List`、`Array`  和 `Map`），可以设置默认值（**目前仅支持基本类型设置 defaultValue**）。  
这个例子演示了从 `HttpRequest` 中获取基本类型的方法，如果没有值会自动设置默认值：
```
public Render primTypeTest(@Read(key="id", defaultValue="1" ) Integer id, @Read(key="proj") String proj, @Read(key="author") String author){
	System.out.println("Receive parameters: id=" + id + ",proj=" + proj + ",author=" + author);
	return new Render(RenderType.TEXT, "Received your primTypeTest request.[from primTypeTest]");
}
```  
这个例子演示了从 `HttpRequest` 中获取 `List/Array` 类型的方法：
```
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
```
这个例子演示了从 `HttpRequest` 中获取 `Map` 类型的方法（**注意，使用 Map 时限定了只能存在一个 Map<String,String> 参数**）：
```
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
```  
  
# 渲染数据
处理方法可以通过返回 `Render` 对象向客户端返回特定格式的数据，一个 `Render` 对象由枚举类型 `RenderType` 和 `data` 两部分组成。  
`nettice` 组件会通过 `RenderType` 来为 `Response` 设置合适的 `Content-Type`，开发者也可以扩展 `Render` 以及相关类来实现更多的类型支持。  
例如这是一个返回 `JSON` 对象的例子，客户端将收到一个 `Json` 对象：
```
public Render postPriMap(){
	JSONObject obj = new JSONObject();
	obj.put("code", 0);
	obj.put("msg", "had received your request.");
	
	return new Render(RenderType.JSON, obj.toJSONString());
}
```  
  
# TODO LIST 
1. java bean支持  
  
  
# License
MIT  


