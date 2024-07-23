import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){

        if (args.length != 2){
            System.out.println("Enter IP and port");
        }else {
            String IP = args[0];
            int port = 0;
            try{
                port = Integer.parseInt(args[1]);
            }catch (NumberFormatException e){
                System.out.print("Port is a number!");
                System.exit(0);
            }
            try {
                SocketAddress sa = new InetSocketAddress(IP, port);
                DatagramChannel channel = DatagramChannel.open();
                channel.connect(sa);

                new Thread(()->{
                    while (true) {
                        try {
                            String sin;
                            byte[] buf = new byte[1024];
                            ByteBuffer bbuf = ByteBuffer.wrap(buf);
                            channel.read(bbuf);
                            sin = new String(buf);
                            System.out.println(sin);
                        } catch (Exception exe) {
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.err.println("Server is temporarily unavailable or port is not correct!");
                        }
                    }
                }).start();

                new Thread(()->{
                    while (true) {
                        String s = "";
                        Scanner sc = new Scanner(System.in);
                        s = sc.nextLine();
                        if (s.equals("login")) {
                            System.out.println("Enter your login, please");
                            String first = sc.nextLine();
                            System.out.println("Enter you password, please");
                            char[] chars = System.console().readPassword();
                            String second = String.valueOf(chars);
                            s = "login: " + first + "," + second;
                        }
                        if (s.equals("register")) {
                            System.out.println("Chose and enter any login, please");
                            String first = sc.nextLine();
                            System.out.println("Enter your email, please");
                            String second = sc.nextLine();
                            s = "register: " + first + "," + second;
                        }
                        byte[] b = s.getBytes();
                        ByteBuffer bb = ByteBuffer.wrap(b);
                        try {
                            channel.write(bb);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        bb.clear();
                    }
                }).start();
            }catch (IOException e){e.printStackTrace();}
        }
    }
}



