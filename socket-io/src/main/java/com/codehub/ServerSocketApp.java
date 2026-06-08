package com.codehub;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 *
 * @author : Tomatos
 * @date : 2026/6/8
 */
public class ServerSocketApp {
    private static final int PORT = 3333;
    private static final String RESPONSE_MESSAGE = "Welcome";

    public static void main(String[] args) throws IOException {
        try (
                ServerSocket serverSocket = new ServerSocket(PORT);
        ) {
            while (true) {
                final Socket socket0 = serverSocket.accept();
                System.out.println("有客户端连接！");
                Thread thread = new Thread(() -> {
                    try (
                            Socket socket = socket0;
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    ) {
                        while (true) {
                            String msg = receiveClientMsg(in);
                            if (msg == null) break; // 客户端断开连接
                            responseClientMsg(out);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                thread.start();
            }
        }
    }

    private static void responseClientMsg(PrintWriter out) {
        out.println(RESPONSE_MESSAGE);
    }

    /**
     * 读取客户端发送的一行消息（以 \n 为消息边界）
     *
     * @return 客户端消息，若客户端断开连接则返回 null
     */
    private static String receiveClientMsg(BufferedReader in) throws IOException {
        String clientMsg = in.readLine();
        if (clientMsg != null) {
            System.out.println("Receive Client Message: " + clientMsg);
        }
        return clientMsg;
    }
}
