# nettp
实现【命名空间】和【请求分发】功能的 netty http 服务端
  
  
# 结构说明
![structure_descri](https://github.com/cyfonly/nettp/blob/master/nettp-server/src/main/resources/pictures/structure_descri.png "structure_descri.png")  
  
  
# 消息路由整体设计  
![design](https://github.com/cyfonly/nettp/blob/master/nettp-server/src/main/resources/pictures/design.png "design.png")
  
  
# Action请求处理
![action_process](https://github.com/cyfonly/nettp/blob/master/nettp-server/src/main/resources/pictures/action_process.png "action_process.png")  

  
# 特性
1. 接收装配请求数据、流程控制和渲染数据
2. URI 到方法直接映射，以及命名空间

  
# 功能
1. 对 httpRequest 的流程控制
2. 像普通方法一样处理 http 请求
3. 对请求的数据自动装配，支持基本类型、数组和 Map
4. 提供 Render 方法渲染并写回响应，支持多种 content-type
5. 支持可配置的命名空间
  
  
