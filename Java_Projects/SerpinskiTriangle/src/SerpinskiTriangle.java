import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SerpinskiTriangle {
    public static void main(String[] args) {
        SerpinskiTriangle st = new SerpinskiTriangle();
        st.startGui();
    }

    JButton button = new JButton("stop");
    myPoint myPoint = new myPoint();
    JButton button2 = new JButton("set up point");
    JButton button3 = new JButton("start");

    int F = 0;

    public void startGui(){
        JFrame frame = new JFrame();
        JPanel buttonPanel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        button.addActionListener(new ListenerButton());
        button2.addActionListener(new ListenerButton2());
        button3.addActionListener(new ListenerButton3());
        buttonPanel.setLayout(new BorderLayout());
        frame.add(BorderLayout.SOUTH,buttonPanel);
        buttonPanel.add(BorderLayout.WEST,button);
        buttonPanel.add(BorderLayout.EAST,button2);
        buttonPanel.add(BorderLayout.CENTER,button3);
        frame.getContentPane().add(myPoint);
        frame.setSize(500,500);
        frame.setVisible(true);
        while (F != 2){
            button2.setText("set up point"); // мега кастыль
            if (F == 1) {
                setUpXY();
                myPoint.repaint();
                try {
                    Thread.sleep(10);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class ListenerButton3 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            F = 1;
        }
    }

    class ListenerButton implements ActionListener{ // for stop
        @Override
        public void actionPerformed(ActionEvent e) {
            if(F == 1){
                F = 2;
            }
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