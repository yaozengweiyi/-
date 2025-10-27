package org.example.BIO.练习demo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class MoodLogger {

    public static void main(String[] args) {
        // 键盘输入
        Scanner scanner = new Scanner(System.in);
        System.out.println("今天心情如何呢？");
        String mood = scanner.nextLine();

        //获取今天日期
        String data = LocalDate.now().toString();

        String record = "["+data+"]"+mood+"\n";

        try(FileOutputStream out = new FileOutputStream("mood.txt",true)) {
            out.write(record.getBytes());
            System.out.println("心情已记录");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
