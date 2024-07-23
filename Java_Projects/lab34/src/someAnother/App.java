package someAnother;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class App {

    static DatagramSocket ds;
    static DatagramPacket in;

    public static void main(String[] args){

        App app = new App();

        try{
            ds = new DatagramSocket(5000);
        }catch (SocketException e){
            e.printStackTrace();
        }
        new Thread(()->app.reciever()).start();
    }

    synchronized public static void sender(String str){
        try {
            byte[]  nb = str.getBytes();
            DatagramPacket out = new DatagramPacket(nb, nb.length, InetAddress.getLocalHost(), 7000); // 7000
            ds.send(out);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void reciever() {
        byte[] b = new byte[128];
        try {
            in = new DatagramPacket(b, b.length);
            while (true) {
                ds.receive(in);
                byte[] bin = in.getData();
                    handler(new String(bin, 0, in.getLength()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handler(String string){
        String arg = "";
        String[] command = string.split(" ");

        if (command.length == 2) {
            if (command[0].equals("remove") || command[0].equals("add") || command[0].equals("add_if_max")) {
                arg = command[1];
            }
        }
        switch (command[0]) {
            case ("import"):
                //todo
                break;
            case ("remove_last"):
                new Thread().start();
                break;
            case ("remove"):
                new Thread(() -> sender("всё норм ща удалим")).start();
                break;
            case ("add"):
                new Thread(() -> sender("всё норм ща добавим")).start();
                break;
            case ("add_if_max"):
                new Thread().start();
                break;
            case ("show"):
                new Thread().start();
                break;
            case ("info"):
                new Thread().start();
                break;
            case ("load"):
                new Thread().start();
                break;
            case ("exit"):
                new Thread(() -> System.exit(1)).start();
                break;
            case ("save"):
                //todo
                break;
            default:
                new Thread(() -> sender("The command entered is incorrect!\n" + "Example of valid command:\n" +
                        "remove_last\n" + "remove {element}\n" + "add {element}\n" + "show\n" + "info\n" + "load\n" +
                        "add_if_max {element}\n" + "exit\n")).start();
        }
    }
}
