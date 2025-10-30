package org.example.BIO.Socket;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerWait {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务器启动，等待连接");
        serverSocket.accept(); //卡在这里，直到等到连接(?
        System.out.println("有人连上了！");
    }


}
