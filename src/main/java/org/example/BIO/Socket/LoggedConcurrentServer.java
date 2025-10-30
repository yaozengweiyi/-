package org.example.BIO.Socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LoggedConcurrentServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        System.out.println("服务器启动，聊天记录将被记录...");

        while(true){
            Socket client = server.accept();
            System.out.println("服务器连接到:"+client.getInetAddress());

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
        int port = client.getPort();
        //为每个端口提供不同的日志文件
        String logName = "chat_"+port+".log";

        BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream())
        );

        PrintWriter out = new PrintWriter(
                client.getOutputStream(),true
        );

        try(FileWriter writer = new FileWriter(logName,true)){
            String msg;
            while(!(msg = in.readLine()).equals("bye")){
                System.out.println("["+port+"]"+msg);
                out.println("Echo:"+msg);

                writer.write("Client:"+msg+"\n");
                writer.write("Server:Echo:"+msg+"\n");
                writer.flush();
            }

        }
        client.close();
        System.out.println("客户端"+port+"断开，日志保存到"+logName);

    }
}
