package com.codehub.chatroom.server;

import com.codehub.chatroom.common.protocol.MessageDecoder;
import com.codehub.chatroom.common.protocol.MessageEncoder;
import com.codehub.chatroom.server.handler.AuthHandler;
import com.codehub.chatroom.server.handler.ChatHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 服务端 Pipeline 初始化器
 *
 * Pipeline 顺序（入站方向）:
 * 1. LengthFieldBasedFrameDecoder - 解帧，解决粘包/拆包
 * 2. MessageDecoder              - 字节 → Message 对象
 * 3. AuthHandler                 - 昵称认证（阶段1，通过后自移除）
 * 4. ChatHandler                 - 业务逻辑（阶段2）
 *
 * 出站方向:
 * - MessageEncoder               - Message 对象 → 字节
 */
public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // TODO: 组装 Pipeline
        // 提示:
        // ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(
        //     1024 * 1024,  // maxFrameLength
        //     0,            // lengthFieldOffset
        //     4,            // lengthFieldLength
        //     0,            // lengthAdjustment
        //     4             // initialBytesToStrip
        // ));
        // ch.pipeline().addLast("msgDecoder", new MessageDecoder());
        // ch.pipeline().addLast("msgEncoder", new MessageEncoder());
        // ch.pipeline().addLast("authHandler", new AuthHandler());
        // ch.pipeline().addLast("chatHandler", new ChatHandler());
        throw new UnsupportedOperationException("待实现");
    }
}
