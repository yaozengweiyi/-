package org.example.BIO.日记本;

import java.io.*;
import java.util.Scanner;

public class RemeberMe {

    public static void main(String[] args) {
        String fileName = "name.txt";
        File file = new File(fileName);

        if(file.exists()){

            try(FileInputStream in = new FileInputStream(fileName)){
                byte[] buf = new byte[100];
                int len = in.read(buf);
                String name = new String(buf,0,len).trim();
                System.out.println("欢迎回来!"+name);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            Scanner scanner = new Scanner(System.in);
            System.out.println("第一次见面，你叫什么啊？");
            String name = scanner.nextLine();

            try(FileOutputStream out = new FileOutputStream(fileName)){
                out.write(name.getBytes());
                System.out.println("我记住你了!"+name);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }




    }





}
