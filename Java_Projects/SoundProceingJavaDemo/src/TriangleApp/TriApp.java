package TriangleApp;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TriApp {
    public static void main(String[] args){
       TriApp tp = new TriApp();
       //tp.set_up_GUI();

        tp.draw();
    }

    JButton button1 = new JButton("points");
    JButton button2 = new JButton("start");
    JButton button3 = new JButton("stop");
    myPoint myPoint = new myPoint();

    public void set_up_GUI(){
        JFrame frame = new JFrame();
        JPanel background = new JPanel();
        frame.add(background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        JPanel paper = new JPanel();
        JPanel buttonPanel = new JPanel();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(BorderLayout.SOUTH,button3);
        panel.add(BorderLayout.NORTH,button1);

        button3.addActionListener(new ListenerButton3());

        background.setLayout(new BorderLayout());

        button1.addActionListener(new ListenerButton1());
        button2.addActionListener(new ListenerButton2());

        background.add(BorderLayout.SOUTH,buttonPanel);
        background.add(BorderLayout.CENTER,paper);

        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(BorderLayout.EAST,button2);
        buttonPanel.add(BorderLayout.WEST,panel);

        paper.setLayout(new BorderLayout());
        paper.add(myPoint);

        frame.setVisible(true);
    }
    class myPoint extends JPanel{
        int x;
        int y;
        @Override
        public void paintComponent(Graphics g){
            g.setColor(Color.black);
            g.fillRect(x,y,4,4);
        }
    }
    int[] x_es = new int[]{1,2,3,4};
    int[] y_es = new int[]{0,9,8,7};

    class ListenerButton3 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            flag = false;
        }
    }

    class ListenerButton1 implements ActionListener{
        int i = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            myPoint.repaint();
        }
    }
    class ListenerButton2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    boolean flag = true;

    public void draw(){
        int xd = x_es[3];
        int yd = y_es[3];
        int var;
        int i = 0;
        while (i < 10){
            var = (int)(Math.random() * 3);
            xd = (x_es[var] - xd) / 2;
            yd = (y_es[var] - yd) / 2;
            System.out.println(xd + " " + yd);
            i++;
        }
    }
}
