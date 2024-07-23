import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class GUIclass {

    String password = "W790pox";
    File file = new File("parole.txt");

    JFrame frame1;
    TextArea textArea1;
    JButton button1;

    JFrame frame2;
    TextArea textArea2;

    void setUPfirst(){
        frame1 = new JFrame();
        frame1.setTitle("Password kipper");

        textArea1 = new TextArea();

        button1 = new JButton("Fuck my ass");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getPasswords();
            }
        });

        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.getContentPane().add(BorderLayout.CENTER,textArea1);
        frame1.getContentPane().add(BorderLayout.SOUTH,button1);
        frame1.setBounds(500,200,400,400);
        frame1.setVisible(true);
    }

    void getPasswords(){
        String text = textArea1.getText();
        if(text.equals(password)){
            frame1.setVisible(false);
            setUPsecond();
        }

    }

    void setUPsecond(){
        frame2 = new JFrame();
        frame2.setTitle("Passwords");

        textArea2 = new TextArea();

        textArea2.setText(deserPaswords());

        frame2.getContentPane().add(BorderLayout.CENTER,textArea2);
        frame2.setBounds(500,200,500,700);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame2.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {

                save();

                frame2.dispose();
                System.exit(0);
            }
        });
    }

    void save(){
        String paswords = textArea2.getText();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(paswords);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    String deserPaswords(){
        String paswords = "";
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            paswords = (String) ois.readObject();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return paswords;
    }
}
