package com.codehub;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 *
 * @author : Tomatos
 * @date : 2026/6/8
 */
public class ClientSocketApp {
    private static final int PORT = 3333;

    public static void main(String[] args) throws IOException {
        try (
                Socket socket = new Socket(InetAddress.getLocalHost(), PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in);
        ) {
            while (true) {
                getUserInputAndSendToServer(scanner, out);
                receiveServerResponse(in);
            }
        }
    }

    /**
     * 读取服务端返回的一行响应（以 \n 为消息边界）
     */
    private static void receiveServerResponse(BufferedReader in) throws IOException {
        String received = in.readLine();
        if (received != null) {
            System.out.printf("Received from server: %s\n", received);
        }
    }

    private static void getUserInputAndSendToServer(Scanner scanner, PrintWriter out) {
        String content = scanner.nextLine();
        out.println(content);
    }
}
