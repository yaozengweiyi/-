package org.example.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SocketChannelReadOnce {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(8080));
        serverChannel.configureBlocking(false);
        System.out.println("服务器启动，等待连接...");

        //等待客户端连接
        SocketChannel client = null;
        while(client == null){
            client  = serverChannel.accept();
            Thread.sleep(100);
        }
        System.out.println("客户端已经连接:"+client.getRemoteAddress());

        //从客户端读取数据
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = client.read(buffer); //读数据到buffer

        if(bytesRead>0){
            buffer.flip();
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            System.out.println("收到数据"+new String(data).trim());
        }
        client.close();
        serverChannel.close();
        System.out.println("连接关闭");
    }







}
