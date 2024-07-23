import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FindeColor {
    public static void main(String args[]){
        foo();
    }

    public static void foo() {
        Robot robot = null;
        try {
            robot = new Robot();

        } catch (AWTException e) {

            e.printStackTrace();

        }

        Color color;

        boolean flag = true;

        while (flag){
            InputStreamReader reader = new InputStreamReader(System.in);
            BufferedReader bure = new BufferedReader(reader);
            String mas = "";
            try {
                mas = bure.readLine();
            }
            catch (IOException io){
                io.printStackTrace();
            }

            //System.out.println(mas);

            if (mas.equals("stop")){
                flag = false;
            }

            Point p = MouseInfo.getPointerInfo().getLocation();
            int X = p.x;
            int Y = p.y;

            color  = robot.getPixelColor(X, Y);

            System.out.println(color.toString());
        }
    }
}
