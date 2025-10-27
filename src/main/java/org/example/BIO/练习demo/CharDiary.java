package org.example.BIO.练习demo;

import java.io.FileWriter;
import java.io.IOException;

public class CharDiary {
    public static void main(String[] args) {
        String message = "学了字符流写日记，更简单了";

        try(FileWriter fileWriter = new FileWriter("char_diary.txt")) {
            fileWriter.write(message);
//            System.out.println("写下了"+message);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
