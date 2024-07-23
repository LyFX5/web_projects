package test;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class ClientNew {

    static boolean passwordFlag = false;

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
                Runtime.getRuntime().addShutdownHook(new Thread(){
                    public void run(){
                        try{
                            channel.write(ByteBuffer.wrap("save".getBytes()));
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
                new Thread(()->{ // receiver
                    while (true) {
                        try {
                            String sin;
                            byte[] buf = new byte[1000];
                            ByteBuffer bbuf = ByteBuffer.wrap(buf);
                            channel.read(bbuf);
                            sin = new String(buf);
                            if(sin.contains("log")){ //  pfkjefn
                                swicher();
                            }else {
                                System.out.println(sin);
                            }
                        } catch (Exception exe) {
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.err.println("test.Server is temporarily unavailable or port is not correct!");
                        }
                    }
                }).start();

                new Thread(()->{ // sender
                    while (true) {
                        String sout = "";
                        if(passwordFlag){
                            sout = login();
                            passwordFlag = false;
                        }else {
                            Scanner scanner = new Scanner(System.in);
                            sout = scanner.nextLine();
                        }
                        byte[] b = sout.getBytes();
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

    static void swicher(){
        passwordFlag = true;
    }

    static String login(){
        String first = "";
        String second = "";
        System.out.println("If you want to login, answer 'login'" + "\n" + "If you want to register, answer 'register'.");
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        if (s.equals("login")) {
            System.out.println("Enter your login, please");
            first = sc.nextLine();
            System.out.println("Enter you password, please");
            char[] chars = System.console().readPassword();
            second = String.valueOf(chars);
        }
        if (s.equals("register")) {
            System.out.println("Chose and enter any login, please");
            first = sc.nextLine();
            System.out.println("Enter your email, please");
            second = sc.nextLine();
        }
        return first+","+second;
    }


}
