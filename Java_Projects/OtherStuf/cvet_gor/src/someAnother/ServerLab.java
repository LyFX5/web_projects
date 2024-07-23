package someAnother;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ServerLab {
    public void receiveUDP(){
        try {
            FileWriter fw = new FileWriter("CommandsNEW.txt");
            DatagramSocket ss = new DatagramSocket(ForTest.RESP);
            byte[] buff = new byte[5000];
            DatagramPacket dp = new DatagramPacket(buff,buff.length);
            while (true){
                ss.receive(dp);
                byte[] data = dp.getData();
                String string = new String(data,0,data.length);
                if (string.equals("close")){
                    fw.close();
                    System.exit(1);
                }
                fw.write(string+"\n");
            }
        }catch (IOException io){io.printStackTrace();}
    }
}

        /*public void receive(){
            try {
                PrintStream ps = new PrintStream("Commands");
                ServerSocket ss = new ServerSocket(ForTest.RESP);
                Socket soc = ss.accept();
                while (true) {
                    InputStreamReader isr = new InputStreamReader(soc.getInputStream());
                    BufferedReader bure = new BufferedReader(isr);
                    String string;
                    if ((string = bure.readLine()) != null) {
                        ps.println(string);
                    }
                }
            }catch (IOException io){io.printStackTrace();}
        }*/
