package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerT {
    ServerSocket ss;
    Socket socket;
    String s;
    public static void main(String[] args){
        new ServerT().setUp();
    }
    public void setUp(){
        try {
            ss = new ServerSocket(6666);
            socket = ss.accept();
        }catch (Exception e){
            e.printStackTrace();
        }
        new Thread(()->{
            while ((s = reciver()) != null){
                System.out.println(s);
                sender("test.ServerT: "+s);
            }
        }).start();
    }
    public void sender(String s){
        try {
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            os.writeUTF(s);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String reciver(){
        String sin = null;
        try{
            DataInputStream is = new DataInputStream(socket.getInputStream());
            sin = is.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }
        return sin;
    }



}
