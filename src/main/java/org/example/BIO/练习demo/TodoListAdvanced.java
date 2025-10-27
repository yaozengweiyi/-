package org.example.BIO.练习demo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TodoListAdvanced {
    public static final String fileName = "todo.txt";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while(true){
            showMenu();
            String choice = scanner.nextLine();
            switch (choice){
                case "1":
                    addTask(scanner);
                    break;
                case "2":
                    deleteTask(scanner);
                    break;
                case "3":
                    System.out.println("再见");
                    return;
                default:
                    System.out.println("无效选择，请重试");
            }

        }

    }

    private static void deleteTask(Scanner scanner) {
        List<String> tasks = readAllTasks();
        if(tasks.isEmpty()){
            System.out.println("没有任务删除");
            return;
        }
        System.out.println("输入删除的序号");
        int index = Integer.parseInt(scanner.nextLine())-1;

        if(index>=0 && index<tasks.size()){
            tasks.remove(index);

            try(FileWriter fileWriter = new FileWriter(fileName)){

                for(String task:tasks){
                    fileWriter.write(task+"\n");
                }
                System.out.println("任务已删除");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

    private static void addTask(Scanner scanner) {
        System.out.println("输入新任务:");
        String task = scanner.nextLine();
        try(FileWriter fileWriter = new FileWriter(fileName,true)) {
            fileWriter.write(task);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    private static void showMenu() {

        System.out.println("\n === 待办事项清单 === ");
        showTask();
        System.out.println("1. 添加任务");
        System.out.println("2. 删除任务(输入序号)");
        System.out.println("3. 退出任务");
        System.out.println("请输入");
    }

    private static void showTask() {

        List<String> tasks = readAllTasks();
        if(tasks.isEmpty()){
            System.out.println("暂无任务");
        }else{
            for (int i = 0;i<tasks.size();i++){
                System.out.println((i+1)+"."+tasks.get(i));
            }
        }

    }

    private static List<String> readAllTasks() {
        List<String> tasks = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while((line = reader.readLine() )!= null){
                tasks.add(line);
            }
        } catch (IOException e) {

        }
        return tasks;

    }


}
