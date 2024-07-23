import javax.swing.*;
import java.awt.*;

public class Animation {
    JFrame frame;
    My_oval oval;
    int x = 3;

    public void go(){
        frame = new JFrame();
        oval = new My_oval();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.getContentPane().add(oval);
        frame.setVisible(true);
        repaintator();
    }

    public void repaintator(){
        for (int i=0; i < 228; i++){
            x++;
            frame.repaint();
            try {
                Thread.sleep(10);
            }catch (InterruptedException in_ex){
                System.out.println("Ошибка");
            }
        }
    }

    public class My_oval extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            g.setColor(Color.orange);
            g.fillOval(x,x,30,30);
        }
    }
}
