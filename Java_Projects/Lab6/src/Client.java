import java.io.*;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class Client{
    public static void main(String[] args) {
        if (args.length == 0)
            System.err.println("Enter port!");
        else {
            System.out.println("Lab6");
            int server_port = Integer.parseInt(args[0]);
            String str = "";
            try {
                SocketAddress a = new InetSocketAddress("localhost", server_port);
                DatagramChannel channel = DatagramChannel.open();
                channel.connect(a);
                Scanner scanner = new Scanner(System.in);
                Runtime.getRuntime().addShutdownHook(new Thread(){
                    public void run(){
                        try{
                            channel.write(ByteBuffer.wrap("exit".getBytes()));
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
                new Thread(() -> {
                    while (true) {
                        try {
                            String sin;
                            byte[] buf = new byte[1024];
                            ByteBuffer bbuf = ByteBuffer.wrap(buf);
                            channel.read(bbuf);
                            sin = new String(buf);
                            System.out.println(sin);
                        } catch (IOException exe) {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.err.println("Server is temporarily unavailable or port is not current!");
                        }
                    }
                }).start();

                while (true) {
                    if (scanner.hasNext()) {
                        str = scanner.nextLine();
                    }
                    String[] strings = str.split(" ");
                    if (strings[0].equals("import")) {
                        if (strings.length == 2) {
                            File file = new File(strings[1]);
                            if(!file.exists())
                                System.err.println("File does not exist!");
                            else if(!file.canRead())
                                System.err.println("File cannot read! Please grant read permission or choose another file.");
                            else {
                                String is = str;
                                new Thread(() -> {
                                    try {
                                        ByteBuffer bbuf = ByteBuffer.wrap(is.getBytes());
                                        channel.write(bbuf);
                                        String os;
                                        Scanner sc = new Scanner(new FileInputStream(file));
                                        while (sc.hasNext()) {
                                            os = sc.nextLine();
                                            ByteBuffer bbuf2 = ByteBuffer.wrap(os.getBytes());
                                            channel.write(bbuf2);
                                        }
                                        os = "stop";
                                        ByteBuffer bbuf2 = ByteBuffer.wrap(os.getBytes());
                                        channel.write(bbuf2);
                                    } catch (FileNotFoundException fex) {
                                        System.out.println("File not founded");
                                        ByteBuffer bbuf2 = ByteBuffer.wrap("Exception".getBytes());
                                        try {
                                            channel.write(bbuf2);
                                        } catch (IOException exxe) {
                                            System.err.println("Problem with sending!");
                                        }
                                    } catch (IOException exe) {
                                        System.err.println("Problem with reading!");
                                    }
                                }).start();
                            }
                        } else System.err.println("Enter correct file name!");
                    } else {
                        String sout = str;
                        new Thread(() -> {
                            byte[] b = sout.getBytes();
                            ByteBuffer bb = ByteBuffer.wrap(b);
                            try {
                                channel.write(bb);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            bb.clear();
                        }).start();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
