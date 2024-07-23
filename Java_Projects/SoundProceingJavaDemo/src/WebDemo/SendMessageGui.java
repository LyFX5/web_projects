package WebDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class SendMessageGui {

    public static void main(String[] args){
        new SendMessageGui().setUpGui();
    }

    JButton sendButton;
    JTextField outText;
    JTextArea inText;
    BufferedReader buRe;
    PrintWriter printWriter;
    Socket socket;

    public void setUpGui(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel sendField = new JPanel();
        JPanel getField = new JPanel();
        JPanel background = new JPanel();
        BorderLayout layout0 = new BorderLayout();
        BorderLayout layout1 = new BorderLayout();
        BorderLayout layout2 = new BorderLayout();
        background.setLayout(layout0);
        sendField.setLayout(layout1);
        getField.setLayout(layout2);
        background.add(getField,BorderLayout.CENTER);
        background.add(sendField,BorderLayout.NORTH);
        sendButton = new JButton("Send");
        ListenerForButton lfb = new ListenerForButton();
        sendButton.addActionListener(lfb);
        outText = new JTextField(20);
        inText = new JTextArea(15,50);
        inText.setLineWrap(true);
        inText.setWrapStyleWord(true);
        JScrollPane scroller = new JScrollPane(inText);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sendField.add(sendButton,BorderLayout.EAST);
        sendField.add(outText,BorderLayout.CENTER);
        getField.add(scroller,BorderLayout.CENTER);

        startNetWorking();

        Thread incomingReader = new Thread(new IncomingReading());
        incomingReader.start();

        frame.getContentPane().add(background,BorderLayout.CENTER);
        frame.setSize(500,500);
        frame.setVisible(true);
    }

    class ListenerForButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                printWriter.println(outText.getText());
                printWriter.flush();
            }catch (Exception ex){System.out.println("при отправке что то пошло не так");ex.printStackTrace();}
            outText.setText("");
            outText.requestFocus();
        }
    }

    public void startNetWorking(){
        try {
            socket = new Socket("127.0.0.1",5000);
            printWriter = new PrintWriter(socket.getOutputStream());
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            buRe = new BufferedReader(isr);
            System.out.println("networking established");
        }catch (IOException ioex){ioex.printStackTrace();}
    }

    public class IncomingReading implements Runnable{
        @Override
        public void run() {
            String message;
            try {
                while ((message = buRe.readLine()) != null){
                    System.out.println("read" + message);
                    inText.append(message + "\n");
                }
            }catch (IOException ioex){ioex.printStackTrace();}
        }
    }
}

