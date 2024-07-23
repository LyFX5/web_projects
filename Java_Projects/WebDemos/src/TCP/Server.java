package TCP;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args){
        try {
            int port = 6666;
            ServerSocket mySoc = new ServerSocket(port);
            Scanner scanner = new Scanner(System.in);
                String out;
                while (true){
                    Socket soc = mySoc.accept();
                    out = scanner.nextLine();
                    BufferedWriter buwr = null;
                    try {
                        buwr = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));
                        buwr.write(out);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    BufferedReader bure = new BufferedReader(new InputStreamReader(soc.getInputStream()));
                    System.out.println(bure.readLine());
                    buwr.close();
                }
        }catch (IOException io){
            io.printStackTrace();
        }
    }
}
