package com.codehub.netty.server;


import com.codehub.EchoConstants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new MultiThreadIoEventLoopGroup(1, NioIoHandler.newFactory());
        EventLoopGroup workerGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel ch) {
                            ch.pipeline()
                              .addLast(new StringEncoder())
                              .addLast(new StringDecoder())
                              .addLast(new ServerHandler());
                            System.out.println("客户端连入服务器, 客户端地址: " + ch.remoteAddress());
                        }
                    });
            System.out.printf("开始监听端口:[%s]%n", EchoConstants.PORT);
            serverBootstrap.bind(EchoConstants.PORT)
                           .sync()
                           .channel()
                           .closeFuture()
                           .sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
