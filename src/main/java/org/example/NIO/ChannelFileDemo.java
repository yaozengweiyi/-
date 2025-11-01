package org.example.NIO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ChannelFileDemo {
    public static void main(String[] args) {
        String fileName = "nio_hello.txt";
//      写数据
        try(FileChannel writeChannel = FileChannel.open(
                Paths.get(fileName),
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE
        ) ){
            ByteBuffer buffer = ByteBuffer.wrap("Hello NIO!".getBytes());

            writeChannel.write(buffer);
            System.out.println("写入文件");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        读数据
        try(FileChannel readChannel = FileChannel.open(
                Paths.get(fileName),
                StandardOpenOption.READ
        )){
//            准备空箱子
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            readChannel.read(buffer);

            buffer.flip();

            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            System.out.println("读出内容"+new String(data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
