package org.example.BIO.练习demo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CharDiaryRead {
    public static void main(String[] args) {
        String fileName = "char_diary.txt";
        int lineNumber = 1;
        try(FileReader fileReader = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fileReader)
        ){
            String line;
            while((line = reader.readLine())!=null){
                System.out.println(lineNumber +"  "+line);

                lineNumber++;
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
