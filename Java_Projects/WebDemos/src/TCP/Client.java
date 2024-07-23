package TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    static final String ServerIP = "127.0.0.1";
    static final int ServerPort = 6666;

    public static void main(String[] args){
        try {
            Socket soc = new Socket(ServerIP,ServerPort);
            Scanner scanner = new Scanner(System.in);
                String out;
                while (true) {
                    out = scanner.nextLine();
                    try {
                        BufferedWriter buwr = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));
                        buwr.write(out);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    BufferedReader bure = new BufferedReader(new InputStreamReader(soc.getInputStream()));
                    System.out.println(bure.readLine());
                }
        }catch (IOException io){
            io.printStackTrace();
        }

    }
}
