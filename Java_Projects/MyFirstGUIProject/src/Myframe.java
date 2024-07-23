import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Myframe {
    JFrame frame;
    JButton button;
    Viget vig;
    JLabel labe;

    public class button_listener implements ActionListener {
        int i = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            i++;
            frame.repaint();
            if (i % 2 == 0) {
                button.setText("I've been already clicked");
            } else {
                button.setText("click me");
            }
            labe.setText(String.valueOf(i));
        }
    }



    public void go(){
        frame = new JFrame();
        button = new JButton();
        vig = new Viget();
        labe = new JLabel();

        button.setText("click me");
        button_listener listener = new button_listener();
        button.addActionListener(listener);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER,vig);
        frame.getContentPane().add(BorderLayout.SOUTH,button);
        frame.getContentPane().add(BorderLayout.NORTH,labe);
        frame.setSize(400,400);
        frame.setVisible(true);
    }
}

