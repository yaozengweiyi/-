package org.example.BIO.Socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSayHello {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        System.out.println("等待连接");

        Socket client = server.accept();
        System.out.println("客户端已经连接");

        //输出流
        PrintWriter out = new PrintWriter(client.getOutputStream(),true);
        out.println("Hello from Java Server");

        client.close();
        server.close();

    }



}
