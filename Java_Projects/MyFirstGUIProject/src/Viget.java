import javax.swing.*;
import java.awt.*;

public class Viget extends JPanel {
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        int red = (int)(Math.random()*255);
        int green = (int)(Math.random()*255);
        int blue = (int)(Math.random()*255);
        Color start_color = new Color(red,green,blue);

        red = (int)(Math.random()*255);
        green = (int)(Math.random()*255);
        blue = (int)(Math.random()*255);
        Color end_color = new Color(red,green,blue);

        GradientPaint gradiet = new GradientPaint(70,70,start_color,150,150,end_color);

        g2.setPaint(gradiet);
        g2.fillOval(70,70,100,100);
    }
}
