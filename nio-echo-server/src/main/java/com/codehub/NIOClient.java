package com.codehub;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NIOClient {

    public static void main(String[] args) throws IOException {
        try (
                Scanner scanner = new Scanner(System.in);
                SocketChannel channel = SocketChannel.open()
        ) {
            boolean isConnected = channel.connect(new InetSocketAddress(EchoConstants.LOCAL_ADDRESS, EchoConstants.PORT));
            if (!isConnected) {
                System.out.println("连接服务器失败");
                System.exit(-1);
            }

            ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
            while (true) {
                String content = scanner.nextLine();
                if (shouldExit(content)) {
                    break;
                }
                channel.write(ByteBuffer.wrap(content.getBytes()));

                // 读取数据填充到buffer中，返回读取到的字节数
                int byteNum = channel.read(receiveBuffer);
                if (connectClose(byteNum)) {
                    break;
                }
                String responseContent = getResponseContent(receiveBuffer);
                System.out.println(responseContent);
            }
        }
    }

    private static String getResponseContent(ByteBuffer receiveBuffer) {
        receiveBuffer.flip();
        String content = new String(receiveBuffer.array(), 0, receiveBuffer.remaining());
        receiveBuffer.clear();
        return content;
    }

    private static boolean connectClose(int byteNum) {
        return byteNum == -1;
    }

    private static boolean shouldExit(String content) {
        return content.equalsIgnoreCase(EchoConstants.EXIT_FLAG);
    }
}
