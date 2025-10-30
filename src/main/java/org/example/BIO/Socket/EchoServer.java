package org.example.BIO.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) throws IOException {
        //监听
        ServerSocket server = new ServerSocket(8888);
        //返回一个socket
        Socket client = server.accept();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream())
        );
        PrintWriter out = new PrintWriter(client.getOutputStream(),true);

        String msg = in.readLine();
        out.println("Echo:"+msg);

        client.close();
        server.close();
    }









}
