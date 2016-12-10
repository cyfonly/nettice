package com.codec.protobuf;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.HttpRequest;

public class ProtobufDecoder extends MessageToMessageDecoder<HttpRequest> {

	@Override
	protected void decode(ChannelHandlerContext ctx, HttpRequest msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
