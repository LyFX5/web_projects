import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class ClientNew {
    private static boolean passwordFlag = false;
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
                boolean passwordFlag = false;
                new Thread(()->{
                    while (true) {
                        try {
                            String sin;
                            byte[] buf = new byte[1024];
                            ByteBuffer bbuf = ByteBuffer.wrap(buf);
                            channel.read(bbuf);
                            sin = new String(buf);
                            if(sin.equals("Enter your password:")){
                                switcher();
                            }
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
                        String sout;
                        if(passwordFlag){
                            char[] ch = System.console().readPassword();
                            sout = String.valueOf(ch);
                            switcher();
                        }
                        else{
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
    private static void switcher(){
        passwordFlag = !passwordFlag;
    }
}
