package org.example.BIO.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ConcurrentServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        System.out.println("服务器启动，支持并发连接...");

        while(true){
            Socket client = server.accept();
            System.out.println("新客户端:"+client.getInetAddress());

            new Thread(()->{
                try{
                    handleClient(client);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

    }

    private static void handleClient(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream())
        );
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        String msg;
        while (!(msg = in.readLine()).equals("bye")) {
            System.out.println("[" + client.getPort() + "] " + msg);
            out.println("Echo: " + msg);
        }

        client.close();
        System.out.println("👋 客户端 " + client.getPort() + " 断开");

    }


}
