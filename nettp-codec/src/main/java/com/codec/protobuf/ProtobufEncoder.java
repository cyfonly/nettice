package com.codec.protobuf;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.HttpResponse;

public class ProtobufEncoder extends MessageToMessageEncoder<HttpResponse>{

	@Override
	protected void encode(ChannelHandlerContext ctx, HttpResponse msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
