package org.example.BIO.练习demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class MoodReviewer {
    public static void main(String[] args) {
        String fileName = "mood.txt";
        int happydays = 0;

        try(FileInputStream in = new FileInputStream(fileName)){
            Scanner fileScanner = new Scanner(in);

            while(fileScanner.hasNext()){
                String line = fileScanner.nextLine();
                if(line.contains("开心")){
                    happydays++;
                }
            }
            System.out.println("有"+happydays+"天开心");



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
