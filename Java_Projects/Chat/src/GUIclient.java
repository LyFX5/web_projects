import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class GUIclient {

    JFrame frame;
    JFrame logFrame;
    JFrame regFrame;
    JFrame appFrame;
    JButton button1; // login
    JButton button2; // registration
    DatagramChannel channel;

    public static void main(String[] args){
        GUIclient client = new GUIclient();
        client.setUP();
        client.web();
     }

    public void setUP(){
        frame = new JFrame();
        frame.setTitle("Welcome to our chat application!");
        button1 = new JButton();
        button2 = new JButton();
        JPanel panel = new JPanel();
        panel.add(button1);
        panel.add(button2);

        button1.setText("Login");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        button2.setText("Registration");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER,panel);
        frame.setBounds(500,200,400,400);
        frame.setVisible(true);
    }

    public void login(){
        frame.dispose();
        logFrame = new JFrame("Login");
        logFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TextField username = new TextField();
        TextField password = new TextField();
        JButton login = new JButton("Login");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String first = username.getText();
                String second = password.getText();
                String s = "login: " + first + "," + second;
                sender(s);
            }
        });
        JButton onmain = new JButton("On main page");
        onmain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logFrame.dispose();
                setUP();
            }
        });
        JPanel butPanel = new JPanel();
        butPanel.add(login);
        butPanel.add(onmain);

        Box buttonBox = new Box(BoxLayout.Y_AXIS);
        JPanel background = new JPanel(new BorderLayout());
        background.setBorder(BorderFactory.createEmptyBorder(100,60,180,60));
        background.add(buttonBox);
        logFrame.getContentPane().add(BorderLayout.CENTER,background);

        buttonBox.add(username);
        buttonBox.add(password);
        buttonBox.add(butPanel);
        logFrame.setBounds(500,200,400,400);
        logFrame.setVisible(true);
    }
    public void register(){

    }

    public void goToApp(){
        logFrame.dispose();
        appFrame = new JFrame("Chat");
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //todo
        appFrame.setBounds(500,200,600,600);
        appFrame.setVisible(true);
    }

    public void web(){
        try {
            SocketAddress sa = new InetSocketAddress("localhost", 6666);
            channel = DatagramChannel.open();
            channel.connect(sa);

            new Thread(()->{
                while (true) {
                    try {
                        String sin;
                        byte[] buf = new byte[1024];
                        ByteBuffer bbuf = ByteBuffer.wrap(buf);
                        channel.read(bbuf);
                        sin = new String(buf);
                        if (sin.contains("you are successfully login")){
                            goToApp();
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

        }catch (IOException e){e.printStackTrace();}
    }

    public void sender(String massage){
        byte[] b = massage.getBytes();
        ByteBuffer bb = ByteBuffer.wrap(b);
        try {
            channel.write(bb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bb.clear();
    }
}
