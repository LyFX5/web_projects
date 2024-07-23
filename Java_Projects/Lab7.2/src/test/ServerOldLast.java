package test;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class ServerOldLast {
    /*private DatagramSocket ds;
    private HashMap<Integer, Application> applications = new HashMap<>();
    private InetAddress IP;

    public void setUP(String[] str) {
        if(str.length == 0)
            System.err.println("Enter port!");
        else{
            int servport = 0;
            try{
                servport = Integer.parseInt(str[0]);
            }catch (NumberFormatException ne){
                System.out.println("Port is a number!");
                System.exit(0);
            }
            try {
                ds = new DatagramSocket(servport);
                byte[] b = new byte[6000];
                DatagramPacket dp = new DatagramPacket(b,b.length);
                while(true) {
                    ds.receive(dp);
                    int port = dp.getPort();
                    IP = dp.getAddress();
                    b = dp.getData();
                    String in = new String(b, 0, dp.getLength());
                    System.out.println(in);
                    if (in.equals("logout")){
                        applications.remove(port);
                    }
                    if(!applications.containsKey(port)){
                        analizator(in,port);
                    }else {
                        applications.get(port).application(in, port, IP, ds);
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void analizator(String in, int port){
      if(login(in,port)){
          sender("you login successfully",port);
      }else {
          if(register(in,port)){
              sender("you registered successfully",port);
          }else {
              sender("log",port);
          }
      }
    }

    private boolean login(String s,int port){
        String[] str = s.split(",");
        if(str.length != 2){
            return false;
        }
        else{
            String pas = str[1];//passwordHashing(str[1]);
            if(Database.containsUser(str[0],pas)){
                applications.put(port,new Application(str[0]));
                return true;
            }
            else {
                return false;
            }
        }
    }

    private boolean register(String s,int port){
        String[] str = s.split(",");
        if(str.length!=2){
            return false;
        }
        else{
            if(!Database.containsLogin(str[0])) {
                boolean flag = true;
                String message = "";
                String toHash = stringGeneration();
                String password = toHash;//passwordHashing(toHash);
                try {
                    Mail mail = new Mail();
                    mail.mail(toHash,str[1]);
                }catch (MessagingException ex){
                    sender("The address entered is not available or does not exist!",port);
                    flag = false;
                }
                if(flag) {
                    Database.putinUser(str[0],password);
                    sender("Password has been sent to your email.", port);
                    return true;
                }else {
                    return false;
                }
            }
            else{
                sender("Username is already used.",port);
                return false;
            }
        }
    }

    synchronized private void sender(String str,int port){
        try {
            byte[]  nb = str.getBytes();
            DatagramPacket out = new DatagramPacket(nb, nb.length, IP, port);
            ds.send(out);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private String stringGeneration(){
        String alphabet = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
        String digits = "0123456789";
        String PasswordToHash = "";
        String[] strings = new String[2];
        strings[0] = alphabet;
        strings[1] = digits;
        for (int i = 0; i < (int)(Math.random()*17+8); i++) {
            short number = (short) (Math.random() * 2);
            PasswordToHash += (strings[number].charAt((int) (Math.random() * strings[number].length())));
        }
        return PasswordToHash;
    }

    private String passwordHashing(String PasswordToHash){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hashedPassword = md.digest(PasswordToHash.getBytes(StandardCharsets.UTF_8));
        return new String(hashedPassword,0,hashedPassword.length);
    }*/
}
