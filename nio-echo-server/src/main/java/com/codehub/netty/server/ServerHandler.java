package com.codehub.netty.server;

import com.codehub.EchoConstants;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 服务端处理器
 *
 * @author Administrator
 */
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 处理读取到的客户端消息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println("收到客户端消息: " + msg);
        ctx.writeAndFlush(EchoConstants.FIXED_RESPONSE_CONTENT);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开连接: " + ctx.channel().remoteAddress());
    }

    /**
     *
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
