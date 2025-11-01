package org.example.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class SelectorAcceptEvent {
    public static void main(String[] args) throws IOException {
//        创造门卫
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(8888));
        //非阻塞
        serverChannel.configureBlocking(false);

//        创建时间通知中心
        Selector selector = Selector.open();

        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("事件监听启动，只关注新连接...");

        while(true){
            int readyChannels = selector.select(); //阻塞在这里
            System.out.println("检测到"+readyChannels+"个事件");

            for(SelectionKey key : selector.selectedKeys()){
                if(key.isAcceptable()){
                    System.out.println("监测到新连接事件");
                    // 实际处理新连接
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    server.accept(); // 接受连接
                }
            }
            selector.selectedKeys().clear(); // 清空已处理事件
        }

    }
}
