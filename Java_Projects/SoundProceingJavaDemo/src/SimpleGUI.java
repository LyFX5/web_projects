import javafx.scene.layout.Border;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SimpleGUI{
   JButton button1;
    MyDrowPanel viget;
    JFrame frame;
    JLabel label;
    JButton button2;
    button1Listener listener1 = new button1Listener();
    button2Listener listener2 = new button2Listener();
    class button1Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.repaint();
        }
    }
    ArrayList<ActionEvent> ev_array = new ArrayList<>();
    class button2Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            ev_array.add(e);
            if(ev_array.size()%2 != 0) {
                label.setText("i've been changed");
            }else{
                label.setText("i'am a label");
            }
        }
    }
    public static void main(String[] args){
        SimpleGUI simp = new SimpleGUI();
        simp.go();

    }
    public void go(){
        frame = new JFrame();
        button1 = new JButton("click me");
        viget = new MyDrowPanel();
        label = new JLabel("i'am a label");
        button2 = new JButton();
        button1.addActionListener(listener1);
        button2.addActionListener(listener2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.SOUTH,button1);
        frame.getContentPane().add(BorderLayout.CENTER,viget);
        frame.getContentPane().add(BorderLayout.EAST,button2);
        frame.getContentPane().add(BorderLayout.WEST,label);
        frame.setSize(300,300);
        frame.setVisible(true);
    }
}
