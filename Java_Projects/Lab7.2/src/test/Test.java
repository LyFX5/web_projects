package test;
import com.sun.javaws.util.JfxHelper;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args){
        Stream.of("европа","азия","африка","австралия","америка")
                .filter(s -> s.startsWith("а"))
                .limit(3)
                .map(s -> delfirst(s))
                .sorted()
                .forEachOrdered(System.out::println);
    }

    static String delfirst(String st){
        char[] ch = st.toCharArray();
        char[] chnew = new char[ch.length - 1];
        for (int i = 0; i < chnew.length; i++){
            chnew[i] = ch[i + 1];
        }
        return new String(chnew);
    }

    static void gui(){
        JFrame fr = new JFrame();
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Box p1 = Box.createVerticalBox();
        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(2,3));
        p1.add(new JLabel("5"));
        p1.add(new JLabel("6"));
        p2.add(new JLabel("7"));
        p1.add(new JLabel("8"));
        p2.add(new JLabel("9"));
        p2.add(p1);
        fr.getContentPane().add(BorderLayout.CENTER,p2);
        fr.setSize(500,500);
        fr.setVisible(true);
    }
}
