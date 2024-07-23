package someAnother;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class ClientLab {

    public void sendSASHA() { // вот

        String str = "";
        try {
            SocketAddress a = new InetSocketAddress("localhost", 7000);
            DatagramChannel channel = DatagramChannel.open();
            channel.connect(a);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                if (scanner.hasNext()) {
                    str = scanner.nextLine();
                }
                String[] strings = str.split(" ");
                if ((strings.length == 2) && strings[0].equals("import")) {
                    ByteBuffer bbuf = ByteBuffer.wrap("import".getBytes());
                    channel.write(bbuf);
                    boolean flag = true;
                    while (flag) {
                        channel.read(bbuf);
                        byte[] by = bbuf.array();
                        String inCome = new String(by,0,by.length);
                        if (inCome.equals("ready")){
                            try {
                                BufferedReader bure = new BufferedReader(new InputStreamReader(new FileInputStream(strings[1])));
                                String string;
                                ByteBuffer bbuf2;
                                while (!(string = bure.readLine()).equals("")) {
                                    bbuf2 = ByteBuffer.wrap(string.getBytes());
                                    channel.write(bbuf2);
                                }
                            } catch (FileNotFoundException fex) {
                                System.out.println("File not founded");
                            }
                            flag = false;
                        }
                    }
                } else {

                    byte[] b = str.getBytes();
                    ByteBuffer bb = ByteBuffer.wrap(b);
                    channel.write(bb);
                    bb.clear();
                /*byte[] buf = new byte[1024];
                ByteBuffer bbuf = ByteBuffer.wrap(buf);
                channel.read(bbuf);
                str = new String(buf);
                System.out.println("Клиент получил:" + str);*/
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    //PrintStream ps;

    /*public void connect(){
        try {
           Socket soc = new Socket("127.0.0.1",ForTest.RESP);
           ps = new PrintStream(soc.getOutputStream());
        }catch (IOException io){ io.printStackTrace(); }
    }
    public void send(String string){
        ps.println(string);
    }*/

