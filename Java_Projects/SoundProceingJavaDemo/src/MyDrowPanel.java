import javax.swing.*;
import java.awt.*;
import java.lang.management.MemoryManagerMXBean;

public class MyDrowPanel extends JPanel {
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        int red1 = (int)(Math.random()*255);
        int green1 = (int)(Math.random()*255);
        int blue1 = (int) (Math.random()*255);

        int red2 = (int)(Math.random()*255);
        int green2 = (int)(Math.random()*255);
        int blue2 = (int) (Math.random()*255);

        Color startColor = new Color(red1,green1,blue1);
        Color endColor = new Color(red2,green2,blue2);

        GradientPaint gradient = new GradientPaint(70,70,startColor,150,150,endColor);
        g2d.setPaint(gradient);
        g2d.fillOval(70,70,100,100);
        //Image image = new ImageIcon("C:\\Users\\Edik kek\\Desktop\\filosofia\\Main.jpg").getImage();
        //g.drawImage(image,3,4,this);
    }
}
