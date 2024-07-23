package TriangleApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SerpinskiTriangle {
    public static void main(String[] args) {
        SerpinskiTriangle st = new SerpinskiTriangle();
        st.startGui();
    }

    JButton button = new JButton("many");
    myPoint myPoint = new myPoint();
    JButton button2 = new JButton("4");

    boolean flag = false;

    public void startGui(){
        JFrame frame = new JFrame();
        JPanel buttonPanel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        button.addActionListener(new ListenerButton());
        button2.addActionListener(new ListenerButton2());
        buttonPanel.setLayout(new BorderLayout());
        frame.add(BorderLayout.SOUTH,buttonPanel);
        buttonPanel.add(BorderLayout.WEST,button);
        buttonPanel.add(BorderLayout.EAST,button2);
        frame.getContentPane().add(myPoint);
        frame.setSize(500,500);
        frame.setVisible(true);
        System.out.println("go");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            if (br.readLine().equals("start")) {
                while (true){
                    setUpXY();
                    myPoint.repaint();
                    try{
                        Thread.sleep(20);
                    }catch (Exception ex){ex.printStackTrace();}
                    if(flag){break;}
                }
            }
        }catch (Exception ex){ex.printStackTrace();}
    }

    class ListenerButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
           flag = true;
        }
    }

    int i;
    int x;
    int y;
    class ListenerButton2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(i < 4){
                x = (int)(Math.random()*400);
                y = (int)(Math.random()*400);
                x_es[i] = x;
                y_es[i] = y;
                myPoint.repaint();
                i++;
            }
        }
    }
    int[] x_es = new int[4];
    int[] y_es = new int[4];

    class myPoint extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            g.setColor(Color.black);
            g.fillRect(x,y,1,1);
        }
    }
    public void setUpXY(){
        if((x_es.length == 4) && (y_es.length == 4)){
            int var = (int)(Math.random()*3);
            x = x + ((x_es[var] - x) / 2);
            y = y + ((y_es[var] - y) / 2);
        }else {
            System.out.println("arrays are empty");
        }
    }
}