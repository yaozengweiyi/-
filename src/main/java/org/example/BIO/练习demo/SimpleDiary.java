package org.example.BIO.练习demo;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class SimpleDiary {
    private static final String fileName = "diary.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("\n简易笔记本");
            System.out.println("1.写日记");
            System.out.println("2.查看所有日记");
            System.out.println("3.搜索日记");
            System.out.println("4.退出");
            System.out.println("请选择:");
            String choice = scanner.nextLine();

            switch (choice){
                case "1":
                    writeDiary(scanner);
                    break;
                case "2":
                    showDiary();
                    break;
                case "3":
                    searchDiary(scanner);
                    break;
                case "4":
                    System.out.println("再见");
                    return;
                default:
                    System.out.println("参数输入错误");
            }

        }

    }

    private static void searchDiary(Scanner scanner) {
        System.out.println("请输入关键词");
        String word = scanner.nextLine().trim();

        if(word.isEmpty()){
            System.out.println("关键词不为空");
            return;
        }

        System.out.println("\n----结果为----");
        boolean find = false;

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = reader.readLine())!=null){
                if(line.contains(word)){
                    System.out.println(line);
                    find = true;
                }
            }
        } catch (IOException e) {
            System.out.println("读取日记失败");
        }
        if(find == false){
            System.out.println("没有查询到相应的日记");
        }

    }

    private static void showDiary() {
        System.out.println("\n----所有日记----");
        try(FileReader reader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(reader)
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("暂无日记");
        }

    }

    private static void writeDiary(Scanner scanner) {
        String content = scanner.nextLine();
        String entry = "[" + LocalDate.now() + "] " + content + "\n";
        try(FileWriter writer = new FileWriter(fileName,true)) {
            writer.write(entry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
