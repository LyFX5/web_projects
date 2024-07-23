package someAnother;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client  {

    public static void sendToServerAndGetMassageByTCP() {
        System.out.println("CHAT");
        Scanner scanner = new Scanner(System.in);
        String str = "";
            try {
                Socket socket = new Socket("127.0.0.1", 2000);
                while (true) {
                    if (scanner.hasNext()){
                        str = scanner.nextLine();
                    }
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF(str);
                    if (str.equals("end")){
                        System.exit(1);
                    }
                    System.out.println(dis.readUTF());
                }
            } catch (IOException io) {
                System.out.println("проблема у клиента");
            }

    }

    public static void sendToServerAndGetMessageByUDP(){
        String str = "";
        int i = 0;
        try {
            DatagramSocket soc = new DatagramSocket();
            while (i < 1){
                str = "првет";
                byte[] outby = str.getBytes();
                DatagramPacket out = new DatagramPacket(outby,outby.length,InetAddress.getLocalHost(),5000);
                soc.send(out);
                byte[] buff = new byte[1024];
                DatagramPacket in = new DatagramPacket(buff,buff.length);
                soc.receive(in);
                byte[] inby = in.getData();
                str = new String(inby,0,in.getLength());
                if (str.equals("exit")){
                    System.exit(1);
                }
                System.out.println("клиент получил" + str);
                i++;
            }
        }catch (IOException io){System.out.println("IOException");}
    }
}
