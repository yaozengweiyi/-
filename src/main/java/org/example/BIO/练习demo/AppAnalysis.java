package org.example.BIO.练习demo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AppAnalysis {
    private static final String fileName = "app.log";

    public static void main(String[] args) {
        System.out.println("日志分析报告");
        int countSum = countSum();
        System.out.println("总分析日志数:"+countSum);
        int countInfo = countInfo();
        System.out.println("INFO:"+countInfo);
        int countWarn = countWarn();
        System.out.println("WARN:"+countWarn);
        int countError = countError();
        System.out.println("ERROR:"+countError+"\n");

        System.out.println("错误详情:");
        System.out.println(errorLine());
    }

    private static String  errorLine() {

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String lineNum;
            while((lineNum = reader.readLine())!=null){
                if(lineNum.contains("ERROR")) return lineNum;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "没有错误信息";
    }


    private static int countError() {
        int line = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String lineNum;
            while((lineNum = reader.readLine())!=null){
                if(lineNum.contains("ERROR")) line++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return line;
    }


    private static int countWarn() {
        int line = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String lineNum;
            while((lineNum = reader.readLine())!=null){
                if(lineNum.contains("WARN")) line++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return line;
    }

    private static int countInfo() {
        int line = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String lineNum;
            while((lineNum = reader.readLine())!=null){
                if(lineNum.contains("INFO")) line++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return line;
    }

    private static int countSum() {
        int line = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String lineNum;
            while((lineNum = reader.readLine())!=null){
                line++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return line;

    }


}
