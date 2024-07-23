package WebDemo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class CommunicateWithServer {
    public static void main(String[] args){
       CommunicateWithServer cws = new CommunicateWithServer();
       cws.setUpGui();
       cws.startNetWorking();
    }

    BufferedReader buRe;
    InputStreamReader isr;
    Socket socket;
    boolean flag = false;

    public void startNetWorking(){
        String message;
        try {
            socket = new Socket("77.234.214.82",2222);
            isr = new InputStreamReader(socket.getInputStream());
            buRe = new BufferedReader(isr);
            while ((message = buRe.readLine()) != null){
                System.out.println("hello");
                if(flag){
                    System.out.println("flag = true");
                    break;
                }
            }
            buRe.close();
            System.out.println("конец общения");
        }catch (IOException ioex){}
    }
    public void setUpGui(){
        JFrame frame = new JFrame();
        JButton button = new JButton("stop");
        button.addActionListener(new buttonListener());
        frame.add(button);
        frame.setSize(50,100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public class buttonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            flag = true;
        }
    }
}
