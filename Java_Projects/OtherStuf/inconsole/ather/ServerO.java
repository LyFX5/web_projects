import javax.mail.MessagingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;

public class ServerO {
    private DatagramSocket ds;
    private HashMap<Integer,Application> applications = new HashMap<>();
    private InetAddress IP;
    public void setUP(String[] args) {
        if(args.length == 0)
            System.err.println("Enter port!");
        else{
            try {
                ds = new DatagramSocket(Integer.parseInt(args[0]));
                byte[] b = new byte[1024];
                DatagramPacket dp = new DatagramPacket(b,b.length);
                String timeLogin = "";
                boolean passwordFlag = false;
                while(true) {
                    ds.receive(dp);
                    int port = dp.getPort();
                    IP = dp.getAddress();
                    b = dp.getData();
                    String in = new String(b, 0, dp.getLength());
                    System.out.println(in);
                    if(!applications.containsKey(port)){
                        if((in.split(" ")[0].equals("login:")) && in.split(" ").length == 2){
                            passwordFlag = true;
                            timeLogin = in.split(" ")[1];
                            sender("Enter your password:",port);
                            continue;
                        }
                        if(passwordFlag) {
                            in = "login: "+timeLogin+","+in;
                            analizator(in, port);
                            passwordFlag = false;
                            continue;
                        }
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
        String[] str = in.split(" ");
        switch (str[0]){
            case("login:"):
                login(str[1],port);
                break;
            case ("register:"):
                register(str[1],port);
                break;
            default:
                sender("Login or register in format:"+"\n"+
                        "login: username,password"+"\n"+
                        "register: username,emailadress",port);
        }
    }
    private void login(String s,int port){
        String[] str = s.split(",");
        if(str.length != 2){
            analizator("",port);
        }
        else{
            String pas = str[1];//passwordHashing(str[1]);
            if(Database.containsUser(str[0],pas)){
                applications.put(port,new Application(str[0]));
            }
            else sender("Check your login and password or register.",port);
        }
    }

    private void register(String s,int port){
        String[] str = s.split(",");
        if(str.length!=2){
            analizator("",port);
        }
        else{
            if(!Database.containsLogin(str[0])) {
                String message = "";
                String toHash = stringGeneration();
                String salt = passwordHashing(saltGeneration());
                String password = toHash;//passwordHashing(toHash);
                try {
                    Mail mail = new Mail();
                    mail.mail(toHash,str[1]);
                }catch (MessagingException ex){
                    sender("The address entered is not available or does not exist!",port);
                }
                Database.putinUser(str[0],password);
                sender("Password has been sent to your email.",port);
            }
            else{
                sender("Username is already used.",port);
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
    }

    private String saltGeneration(){
        SecureRandom random = new SecureRandom();
        byte[] salts = new byte[16];
        random.nextBytes(salts);
        return new String(salts, 0, salts.length);
    }
}
