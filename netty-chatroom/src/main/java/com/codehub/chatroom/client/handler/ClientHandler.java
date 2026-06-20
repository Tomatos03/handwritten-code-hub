package com.codehub.chatroom.client.handler;

import com.codehub.chatroom.common.message.Message;
import com.codehub.chatroom.common.message.SystemMessage;
import com.codehub.chatroom.common.message.ChatMessage;
import com.codehub.chatroom.common.message.PrivateMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端消息处理 Handler
 *
 * 职责：接收服务端推送的消息，格式化后打印到终端
 */
public class ClientHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        // TODO: 根据消息类型格式化输出
        // 提示:
        // switch (msg) {
        //     case SystemMessage sys  → 打印系统消息
        //     case ChatMessage chat   → 打印广播消息
        //     case PrivateMessage pm  → 打印私聊消息
        //     case CommandMessage cmd → { /* 客户端一般不收到 CommandMessage */ }
        // }
        throw new UnsupportedOperationException("待实现");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // TODO: 打印 "与服务器断开连接"，退出程序
        throw new UnsupportedOperationException("待实现");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
