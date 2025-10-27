package org.example.BIO.日记本;

import java.util.Scanner;

public class ChatWithMe {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("我是聊天小助手，请输入你的名字");

        String name = scanner.nextLine();

        System.out.println("你的名字是"+name);

    }


}
