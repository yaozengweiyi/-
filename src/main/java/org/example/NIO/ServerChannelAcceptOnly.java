package org.example.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerChannelAcceptOnly {
    public static void main(String[] args) throws IOException, InterruptedException {
//        创建门卫
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.bind(new InetSocketAddress(8888));
        //设置为非阻塞模式（关键！)
        serverSocketChannel.configureBlocking(false);

        System.out.println("门卫已就位，监听8888端口");

        for(int i = 0;i<10;i++){
//            非阻塞accept
            SocketChannel clientChannel = serverSocketChannel.accept();

            if(clientChannel != null){
                System.out.println("新客户端连接"+clientChannel.getRemoteAddress());
                clientChannel.close();
            }else{
                System.out.println("第"+(i+1)+"次检查，无人连接");
            }

            Thread.sleep(1000);
        }

        serverSocketChannel.close();
        System.out.println("门卫下班");
    }

}
