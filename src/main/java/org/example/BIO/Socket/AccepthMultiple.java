package org.example.BIO.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;

public class AccepthMultiple {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        System.out.println("服务器启动，可以服务多个客户端...");

        while(true){
            Socket client = server.accept();
            System.out.println("新客户端连接"+client.getInetAddress());

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream())
            );
            PrintWriter out = new PrintWriter(client.getOutputStream(),true);

            String msg;
            while(!(msg = in.readLine()).equals("bye")){
                System.out.println("["+ client.getPort()+"]收到"+msg);
                out.println("Echo:"+msg);
            }
            client.close();
            System.out.println("客户端断开");

        }





    }
}
