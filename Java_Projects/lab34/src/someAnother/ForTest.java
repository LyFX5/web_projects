package someAnother;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.Callable;

public final class ForTest {

    public static final int RESP = 2793;
    public static final int SCP = 2790;

    public static void main(String[] args) {

       /* Thread serv = new Thread(new ServerLab());
        serv.start();
        System.out.println("Write");
        String str;
        BufferedReader bure = null;

        ClientLab cl = new ClientLab();
        cl.connect();

        try {
            bure = new BufferedReader(new InputStreamReader(System.in));
            while (!(str = bure.readLine()).equals("close")) {
                cl.send(str);
            }
        }catch (IOException io){io.printStackTrace();}*/
    }
}
