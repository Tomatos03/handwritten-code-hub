package com.codehub.chatroom.client;

import com.codehub.chatroom.common.protocol.MessageDecoder;
import com.codehub.chatroom.common.protocol.MessageEncoder;
import com.codehub.chatroom.client.handler.ClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 客户端 Pipeline 初始化器
 *
 * Pipeline 顺序:
 * 1. LengthFieldBasedFrameDecoder - 解帧
 * 2. MessageDecoder              - 字节 → Message 对象
 * 3. MessageEncoder              - Message 对象 → 字节
 * 4. ClientHandler               - 收发处理
 */
public class ChatClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // TODO: 组装 Pipeline（与服务端类似，但 Handler 不同）
        throw new UnsupportedOperationException("待实现");
    }
}
