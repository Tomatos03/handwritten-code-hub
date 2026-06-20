package com.codehub.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端处理器
 *
 * @author Administrator
 */
public class ClientHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 处理服务端响应
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println("收到服务端响应: " + msg);
    }

    /**
     * 连接关闭时触发（服务端关闭连接或网络断开）
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("与服务器的连接已断开");
    }

    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
