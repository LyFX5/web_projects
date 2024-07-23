package someAnother;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class Cli {

    static byte[] b = new byte[128];
    static DatagramSocket ds;

    public static void main(String[] args){
        try {
            ds = new DatagramSocket(7000);
        }catch (IOException io){io.printStackTrace();}

        Cli app = new Cli();
        new Thread(()->app.sender()).start();
        new Thread(()->app.reciever()).start();
    }

    public void sender() {
        /*try {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                b = scanner.nextLine().getBytes();
                DatagramPacket out = new DatagramPacket(b, b.length, InetAddress.getLocalHost(),5000);
                ds.send(out);}
        }catch (IOException e){
            e.printStackTrace();
        }*/
        try {
            SocketAddress a = new InetSocketAddress("localhost", 5000);
            DatagramChannel channel = DatagramChannel.open();
            channel.connect(a);
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                ByteBuffer bbuff = ByteBuffer.wrap(scanner.nextLine().getBytes());
                channel.write(bbuff);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }


    public void reciever() {
        try {
            DatagramPacket in = new DatagramPacket(b, b.length);
            while (true){
                ds.receive(in);
                byte[] inb = in.getData();
                System.out.println(new String(inb,0,in.getLength()));}

            /*SocketAddress a = new InetSocketAddress("localhost", 7000); // ds
            DatagramChannel channel2 = DatagramChannel.open();
            channel2.connect(a);
            while (true){
                ByteBuffer bbuff = ByteBuffer.wrap(new byte[5000]);
                channel2.read(bbuff);
                byte[] b = bbuff.array();
                String string = new String(b,0,b.length);
                System.out.println(string);
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
