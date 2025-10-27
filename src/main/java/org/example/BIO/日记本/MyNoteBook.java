package org.example.BIO.日记本;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class MyNoteBook {
    public static void main(String[] args){
        String word = "我不是一个小学生";
        String site = new Scanner(System.in).nextLine();
//        打开水管，准备流过去
        try(FileOutputStream out = new FileOutputStream(site)){
            byte[] data = word.getBytes();
            out.write(data);
            System.out.println("日记写好了，就在"+site);
        } catch (FileNotFoundException e) {
            System.out.println("写日记失败了");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}
