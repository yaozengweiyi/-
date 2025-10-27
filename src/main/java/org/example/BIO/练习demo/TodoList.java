package org.example.BIO.练习demo;

import java.io.*;
import java.util.Scanner;

public class TodoList {
    public static void main(String[] args) {
        String fileName = "todo.txt";

        showTasks(fileName);

        Scanner scanner = new Scanner(System.in);
        System.out.println("输入新任务(输入quit退出)");

        while (true){
            String task = scanner.nextLine();
            if("quit".equals(task)){
                System.out.println("退出！");
                break;
            }

            //写入任务
            try(FileWriter fileWriter = new FileWriter(fileName,true);
                BufferedWriter writer = new BufferedWriter(fileWriter)
            ) {
                writer.write(task+"\n");
                System.out.println("成功写入任务:"+task);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void showTasks(String fileName) {
        int lineNum = 1;

        try(FileReader fileReader = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fileReader)
        ) {
            String line;
            while((line = reader.readLine())!=null){
                System.out.println(lineNum+"."+line);
                lineNum++;
            }
            if(lineNum == 1){
                System.out.println("暂无任务");
            }
        } catch (FileNotFoundException e) {
            System.out.println("任务文件不存在，将创建新文件");
            System.out.println("暂无任务");
        } catch (IOException e) {
//            File file = new File(fileName);
            throw new RuntimeException(e);
        }
        System.out.println();//空一行

    }

}
