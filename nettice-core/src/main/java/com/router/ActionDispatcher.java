package com.router;

import org.apache.http.HttpHeaders;

import com.router.config.ActionWrapper;
import com.router.invocation.ActionProxy;
import com.router.utils.HttpRenderUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.ReferenceCountUtil;

/**
 * Action 分发器，收到 request 后不做实际业务处理，而是组装 action 并交给处理。
 * @author yunfeng.cheng
 * @create 2016-08-01
 */
public class ActionDispatcher extends ChannelHandlerAdapter{
	
	private static final String CONNECTION_KEEP_ALIVE = "keep-alive";
    private static final String CONNECTION_CLOSE = "close";
    private static final String ACTION_SUFFIX = ".action";
	
	protected static RouterContext routerContext;
	private HttpRequest request;
	private FullHttpResponse response;
	private Channel channel;
	
	public ActionDispatcher(){
		
	}
	
	public void init(String configFilePath) throws Exception{
		if(configFilePath == null){
			configFilePath = "router.xml";
		}
		routerContext = new RouterContext(configFilePath);
	}
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
		if(msg instanceof HttpRequest){
			channel = ctx.channel();
			request = (HttpRequest) msg;
			
			try{
				String path = getRequestPath();
				ActionWrapper actionWrapper = routerContext.getActionWrapper(path);
				if(actionWrapper == null){
					response = HttpRenderUtil.getNotFoundResponse();
					writeResponse(true);
					return;
				}
				
				DataHolder.setRequest(request);
				ActionProxy proxy = routerContext.getActionProxy(actionWrapper);
				Return result = proxy.execute();
				if(result != null){
					response = result.process();
				}
				writeResponse(false);
			}catch(Exception e){
				response = HttpRenderUtil.getErroResponse();
				writeResponse(true);
			}finally{
				ReferenceCountUtil.release(msg);
			}
		}
	}
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
	
	private String getRequestPath() throws Exception{
		String uri = request.uri();
		int startIndex = uri.indexOf(ACTION_SUFFIX);
		if(startIndex <= 0){
			throw new Exception("request path error");
		}
		return uri.substring(0,startIndex + ACTION_SUFFIX.length());
	}
	
	private void writeResponse(boolean forceClose){
		boolean close = isClose();
		if(!close && !forceClose){
			response.headers().add(HttpHeaders.CONTENT_LENGTH, String.valueOf(response.content().readableBytes()));
		}
		ChannelFuture future = channel.write(response);
		if(close || forceClose){
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}
	
	private boolean isClose(){
		if(request.headers().contains(HttpHeaders.CONNECTION, CONNECTION_CLOSE, true) ||
				(request.protocolVersion().equals(HttpVersion.HTTP_1_0) && 
				!request.headers().contains(HttpHeaders.CONNECTION, CONNECTION_KEEP_ALIVE, true)))
			return true;
		return false;
	}
	
}
