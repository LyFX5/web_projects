package UDPchannel;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Server {

    DatagramSocket ds;
    InetAddress IPclient;
    int portClient;
    int portMy = 8089;

    public static void main(String[] args){
        Server server = new Server();
        server.go();
        server.reader();
    }

    public void go(){
        new Thread(()->{
            recieverS();
        }).start();
    }

    public void recieverS(){
        byte[] bytes = new byte[1024];
        try{
            ds = new DatagramSocket(portMy);
            DatagramPacket dp = new DatagramPacket(bytes,bytes.length);
            String inCome;
            while (true){
                ds.receive(dp);
                inCome = new String(dp.getData(),0,dp.getLength());
                System.out.println(inCome);
                IPclient = dp.getAddress();
                portClient = dp.getPort();
                try {
                    Thread.sleep(100);
                }catch (InterruptedException in){
                    in.printStackTrace();
                }
            }
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    public void reader(){
        Scanner scanner = new Scanner(System.in);
        String out;
        while (true) {
            if (scanner.hasNext()) {
                out = scanner.nextLine();
                sender(out);
            }
        }
    }

    public void sender(String str){
        try {
            byte[]  nb = str.getBytes();
            DatagramPacket out = new DatagramPacket(nb, nb.length, IPclient, portClient);
            ds.send(out);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
