package com.codehub;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
/**
 *
 *
 *  clientChannel ──register──▶ selector
 *     │                         │
 *     │                         ▼
 *     │                      SelectionKey (key)
 *     │                         │
 *     └──── key.channel() ◀─────┘
 *
 * @author : Administrator
 * @date : 2026/6/9
 */
public class NIOServer {
    private static final int PORT = 3333;
    private static final String FIXED_RESPONSE_CONTENT = "Hello Client!";

    public static void main(String[] args) throws IOException {
        try(
                ServerSocketChannel serverChannel = ServerSocketChannel
                        .open()
                        .bind(new InetSocketAddress(PORT));
                Selector selector = Selector.open();
        ) {
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.printf("开始监听端口:[%s]%n", PORT);

            while (true) {
                // 阻塞当前线程，直到至少有一个通道的事件就绪
                selector.select();

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        handleAccept(key);
                        continue;
                    }

                    if (key.isReadable()) {
                        if (handleRead(key)) {
                            responseClient(key);
                        }
                    }
                }
            }
        }
    }

    private static void responseClient(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        channel.write(ByteBuffer.wrap(FIXED_RESPONSE_CONTENT.getBytes()));
    }

    private static boolean handleRead(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        int readRes = socketChannel.read(buffer);
        if (readRes == -1) {
            key.cancel();
            socketChannel.close();
            return false;
        }
        buffer.flip();
        System.out.println("收到消息：" + new String(buffer.array(), 0, buffer.remaining()));
        buffer.clear();
        return true;
    }

    private static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        // 从监听通道上接收新的客户端连接，返回客户端的数据通道
        SocketChannel clientChannel = serverChannel.accept();
        // 设置为非阻塞模式，Selector 要求所有注册的通道必须是非阻塞的
        // 设置为阻塞模式会导致 Selector 无法正常工作，因为 Selector 需要能够在没有阻塞的情况下检查通道的状态。
        // 阻塞模式下read方法会一直等待数据到来，无法响应其他事件
        clientChannel.configureBlocking(false);
        // 将客户端通道注册到同一个 Selector，监听读事件，并绑定一个 ByteBuffer 供后续复用
        clientChannel.register(key.selector(), SelectionKey.OP_READ,  ByteBuffer.allocate(1024));

        InetSocketAddress localAddress = (InetSocketAddress) serverChannel.getLocalAddress();
        System.out.printf("客户端%s/%s连接到服务器%n", localAddress.getAddress(), localAddress.getHostName());
    }
}
