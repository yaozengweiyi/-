package org.example.BIO.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8888);

        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );

        out.println("Hi Server!");
        System.out.println("收到回复:"+in.readLine());

        socket.close();
    }






}
