import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Triangle {
    public static void main(String[] args){
        Triangle tr = new Triangle();
        tr.set_up_gui();
    }

    JFrame frame;
    Point point;
    JButton button;
    Button_listener listener;
    JPanel mainPanel;

    public void set_up_gui(){
        frame  = new JFrame("Треугольник");
        button = new JButton();
        point = new Point();
        mainPanel = new JPanel();
        BorderLayout layout = new BorderLayout();
        mainPanel.setLayout(layout);
        mainPanel.add(point);
        listener = new Button_listener();
        button.addActionListener(listener);
        frame.setSize(500,500);
        frame.setBounds(25,25,400,400);
        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
        frame.getContentPane().add(BorderLayout.SOUTH,button);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    class Button_listener implements ActionListener{
        int i = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            if(i < 3) {
                point.repaint();
                i++;
            }
        }
    }
}