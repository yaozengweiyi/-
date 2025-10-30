package org.example.NIO;

import java.nio.ByteBuffer;

public class ByfferStateDemo {
    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(10);
        printState("刚创建",buf);

        buf.put("hi".getBytes());
        printState("写入 'hi' 后", buf);

        //flip() 准备读
        buf.flip();
        printState("flip()之后",buf);

        byte b = buf.get();
        printState("取出一个字节后",buf);
        System.out.println("读取的字节"+(char)b);

    }
    // 打印 Buffer 状态
    private static void printState(String label, ByteBuffer buf) {
        System.out.println(label + " → pos=" + buf.position() +
                ", lim=" + buf.limit() +
                ", cap=" + buf.capacity() +
                ", remaining=" + buf.remaining());
    }



}
