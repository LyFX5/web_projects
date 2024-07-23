import javax.swing.*;
import java.awt.*;

public class Point extends JPanel {
    @Override
    public void paintComponent(Graphics g){
        int x = (int)(Math.random()*350);
        int y = (int)(Math.random()*350);
        if(x<4){
            x = 5;
        }
        if(y<4){
            y = 5;
        }
        int width = 5;
        int height = 5;
        g.setColor(Color.black);
        g.fillRect(x,y,width,height);
    }
}
