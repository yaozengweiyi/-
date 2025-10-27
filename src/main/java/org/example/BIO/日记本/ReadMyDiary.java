package org.example.BIO.日记本;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadMyDiary {
    public static void main(String[] args) {
        try(FileInputStream in = new FileInputStream("my_diary.txt")) {
//            准备一个水桶，最多装1000个字符
            byte[] bucket = new byte[1000];

            int howManyBytes = in.read(bucket);
//            把字符串变为文字
            String content = new String(bucket,0,howManyBytes);
//            throw new FileNotFoundException("我故意的");
            System.out.println("你的日记内容为："+content);
        } catch (FileNotFoundException e) {
            System.out.println("获取日记失败，呜呜呜");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
