import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

public class ServerOLD {
    private DatagramSocket ds;
    private HashMap<Integer,Application> applications = new HashMap<>();
    private InetAddress IP;//На всякий случай

    public void setUP(String[] args) {
        if(args.length == 0)
            System.err.println("Enter port!");
        else{
            try {
                ds = new DatagramSocket(Integer.parseInt(args[0]));
                byte[] b = new byte[1024];
                DatagramPacket dp = new DatagramPacket(b,b.length);
                while(true) {
                    ds.receive(dp);
                    int port = dp.getPort();
                    IP = dp.getAddress();
                    b = dp.getData();
                    String in = new String(b, 0, dp.getLength());
                    System.out.println(in);
                    if(!applications.containsKey(port)){
                        analizator(in,port);
                    }else {
                        applications.get(port).application(in, port, IP, ds); // это было в треде но кажется он не нужен
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
            if(Database.containsUser(str[0],passwordGeneration(str[1]))){
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
                //Mail mail = new Mail();
                String password = passwordGeneration(str[1]);
                //mail.mail(password,str[1]);
                sender(password,port);//todo
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
    private String passwordGeneration(String password){
        return "postgres";
    }
}
