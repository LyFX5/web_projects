package someAnother;

import java.io.*;
import java.net.*;

public class Server implements Runnable  {
     public static void getFromClientChangeAndSendByTCP(){
         String str ;
        try {
            ServerSocket ss = new ServerSocket(2000);
            Socket socket = ss.accept();
            while (true) {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                str =  dis.readUTF();
                if (str.equals("end")){
                    System.exit(1);
                }
                System.out.println("it was on server");
                dos.writeUTF(dis.readUTF() + " " + "server");
            }
            /*dis.close();
            dos.close();
            socket.close();
            ss.close();*/
        }catch (IOException io){ System.out.println("проблема на сервере");}
    }

    //===================================================UDP
    public void getFromClientChangeAndSendByUDP() {
         String str = "";
     try {
         DatagramSocket ss = new DatagramSocket(5000);
         byte[] buff = new byte[6000];
         DatagramPacket in = new DatagramPacket(buff,buff.length);
         while (true){
             ss.receive(in);
             byte[] inby = in.getData();
             str = new String(inby, 0,in.getLength());
             if(str.equals("exit")){
                 System.exit(1);
             }
             System.out.println("сервер получил" + str);
             DatagramPacket out = new DatagramPacket(str.getBytes(),str.getBytes().length,in.getAddress(),in.getPort());
             ss.send(out);
         }
     }catch (IOException io){System.out.println("проблема на сервере");}
    }
    //===========================================================================UDP

    @Override
    public void run() {
        getFromClientChangeAndSendByTCP();
        //getFromClientChangeAndSendByUDP();
    }
}
