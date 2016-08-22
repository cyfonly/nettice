package com.router;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * 结果接口类
 * @author yunfeng.cheng
 * @create 2016-08-01
 */
public interface Return {
	
	public abstract FullHttpResponse process() throws Exception;

}
