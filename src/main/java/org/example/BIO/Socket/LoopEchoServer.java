package org.example.BIO.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class LoopEchoServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        Socket client = server.accept();
        System.out.println("客户端已连接");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream())
        );
        PrintWriter out = new PrintWriter(client.getOutputStream());

        String msg;
        while(!(msg = in.readLine()).equals("bye")){
            System.out.println("收到"+msg);
            out.println("Echo:"+msg);
        }

        out.println("Bye!Connection closed");
        client.close();
        server.close();
        System.out.println("连接关闭");
    }
}
