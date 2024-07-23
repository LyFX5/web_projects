package UDPchannel;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class Client {

    static String ServerIP = "localhost";
    static int ServerPort = 6666;

    public static void main(String[] args){
        SocketAddress sa = new InetSocketAddress(ServerIP,ServerPort);
        Scanner scanner = new Scanner(System.in);
        try {
            DatagramChannel channel = DatagramChannel.open();
            channel.connect(sa);
            new Thread(()->{
                String out;
                while (true) {
                    out = scanner.nextLine();
                    ByteBuffer bb = ByteBuffer.wrap(out.getBytes());
                    try {
                        channel.write(bb);
                    }catch (IOException io){
                        io.printStackTrace();
                    }
                    System.out.println("sended : " + out);
                }
            }).start();

            new Thread(()->{
                String inCome;
                while (true){
                    byte[] bytes = new byte[1024];
                    ByteBuffer bb = ByteBuffer.wrap(bytes);
                    try {
                        channel.read(bb);
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
