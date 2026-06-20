package com.codehub.netty.client;

import com.codehub.EchoConstants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline()
                      .addLast(new StringEncoder())
                      .addLast(new StringDecoder())
                      .addLast(new ClientHandler());
                }
            });

            Channel channel = bootstrap
                    .connect(
                            EchoConstants.LOCAL_ADDRESS, EchoConstants.PORT
                    )
                    .sync()
                    .channel();

            // 监听连接关闭事件
            channel.closeFuture().addListener(future -> {
                System.out.println("连接已关闭，客户端退出");
                System.exit(0);
            });

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String content = scanner.nextLine();
                if (content.equalsIgnoreCase(EchoConstants.EXIT_FLAG)) {
                    break;
                }
                channel.writeAndFlush(content);
            }

            channel.close()
                   .sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
