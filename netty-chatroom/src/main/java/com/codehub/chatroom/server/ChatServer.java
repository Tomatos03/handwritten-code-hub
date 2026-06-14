package com.codehub.chatroom.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 聊天室服务端启动类
 *
 * Netty 服务端启动步骤：
 * 1. 创建两个 EventLoopGroup（boss 负责 accept，worker 负责 I/O）
 * 2. 配置 ServerBootstrap
 * 3. 绑定端口，启动监听
 */
public class ChatServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChatServerInitializer());

            ChannelFuture future = bootstrap.bind(PORT).sync();
            System.out.println("聊天室服务端启动，监听端口: " + PORT);

            // 阻塞直到服务端 Channel 关闭
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
