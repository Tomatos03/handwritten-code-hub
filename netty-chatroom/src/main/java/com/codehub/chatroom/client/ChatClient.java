package com.codehub.chatroom.client;

import com.codehub.chatroom.common.message.ChatMessage;
import com.codehub.chatroom.common.message.CommandMessage;
import com.codehub.chatroom.common.message.PrivateMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * 聊天室客户端启动类
 *
 * 两个线程职责：
 * - 主线程：读取用户输入（stdin），解析后发送到服务端
 * - EventLoop 线程：接收服务端消息，在 ClientHandler 中打印到终端
 *
 * 用户输入格式：
 * - "普通消息"    → ChatMessage（广播）
 * - "@Tom 你好"  → PrivateMessage（私聊）
 * - "/name 新名" → CommandMessage（改名）
 * - "/quit"      → 退出
 */
public class ChatClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientInitializer());

            Channel channel = bootstrap.connect(HOST, PORT).sync().channel();
            System.out.println("已连接到聊天室服务器");

            // 主线程：读取用户输入
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    continue;
                }

                // TODO: 解析输入并发送
                // 提示:
                // if (input.startsWith("/quit")) {
                //     channel.close();
                //     break;
                // } else if (input.startsWith("/name ")) {
                //     String newName = input.substring(6).trim();
                //     channel.writeAndFlush(new CommandMessage("?", "name", newName));
                // } else if (input.startsWith("@")) {
                //     int spaceIdx = input.indexOf(' ');
                //     String target = input.substring(1, spaceIdx);
                //     String content = input.substring(spaceIdx + 1);
                //     channel.writeAndFlush(new PrivateMessage("?", target, content));
                // } else {
                //     channel.writeAndFlush(new ChatMessage("?", input));
                // }
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
