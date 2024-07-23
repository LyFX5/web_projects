package UDPpacket;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    static InetAddress ServerIP;
    static final int ServerPort = 6666;
    static final int MyPort = 5555;

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        try {

            ServerIP = InetAddress.getLocalHost();

            DatagramSocket ds = new DatagramSocket(MyPort);
            new Thread(()->{
                String out;
                while (true) {
                    out = scanner.nextLine();
                    byte[] bytes = out.getBytes();
                    try {
                        ds.send(new DatagramPacket(bytes, bytes.length,ServerIP,ServerPort));
                    }catch (IOException io){
                        io.printStackTrace();
                    }
                }
            }).start();

            new Thread(()->{
                String inCome;
                byte[] bytes = new byte[1024];
                DatagramPacket dp = new DatagramPacket(bytes,bytes.length);
                while (true){
                    try {
                        ds.receive(dp);
                    }catch (IOException io){
                        io.printStackTrace();
                    }
                    inCome = new String(bytes);
                    System.out.println(inCome);
                }
            }).start();

        }catch (IOException ioex){
            ioex.printStackTrace();
        }
    }
}

