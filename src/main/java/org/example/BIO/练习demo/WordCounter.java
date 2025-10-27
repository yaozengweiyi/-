package org.example.BIO.练习demo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class WordCounter {
    public static void main(String[] args) {
        int totalChars = 0;

        try(FileReader fileReader = new FileReader("char_diary.txt");
            BufferedReader reader = new BufferedReader(fileReader)
        ) {
            String line;
            while((line = reader.readLine())!=null){
                totalChars += line.length();
            }
            System.out.println("总字数为："+totalChars);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
