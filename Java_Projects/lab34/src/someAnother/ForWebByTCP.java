package someAnother;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
public class ForWebByTCP {

    private ForWebByTCP(){}

    public static final int AvailablePort = 5000;
    public static final String LocalIP = "127.0.0.1";

    public static void writIntoServer(String IP, int port, String dataS, int dataI) throws IOException {
        Socket chatSocket = new Socket(IP,port);
        DataOutputStream dos = new DataOutputStream(chatSocket.getOutputStream());
        dos.writeUTF(dataS);
        dos.writeInt(dataI);
        dos.close();
        chatSocket.close();
    }

    public static byte[] readFromServer(Socket soc, byte[] data) throws IOException {
        InputStream is = soc.getInputStream();
        is.read(data);
        return data;
    }

    public static void writIntoClient(Socket soc, byte[] data) throws IOException {
        OutputStream os = soc.getOutputStream();
        os.write(data);
    }

    static Socket soc = null;

    public static void readFromClient() throws IOException {
        ServerSocket servSoc = new ServerSocket(AvailablePort);
        soc = servSoc.accept();

    }

    //public static byte[] doStuffWithData(byte[] data){
// System.out.println(data); return null;
// }
}
