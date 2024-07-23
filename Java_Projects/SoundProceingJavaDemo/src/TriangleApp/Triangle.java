package TriangleApp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Triangle {
    public static void main(String[] args){
        Triangle triangle = new Triangle();
        triangle.set_up_GUI();
    }

    JTextField field;
    JButton button1;
    JButton button2;
    JButton button3;
    JPanel paintPanel;
    DemoPanel paper;
    myPoint newPoint;
    JFrame frame = new JFrame();

    public void set_up_GUI(){
        JPanel firstPanel = new JPanel();
        //JPanel background = new JPanel();
        paintPanel = new JPanel();

        JLabel label1 = new JLabel("Введите координаты точки");
        JLabel label2 = new JLabel("x y и нажмите enter.");
        JLabel label3 = new JLabel("Всего нужно выбрать");
        JLabel label4 = new JLabel("четыре точки.");
        Box extraBox = new Box(BoxLayout.Y_AXIS); // Пригодится

        BorderLayout layout1 = new BorderLayout();
        //frame.add(background);
       // background.setLayout(layout1);

       /* background*/frame.add(BorderLayout.SOUTH,firstPanel);
        /*background*/frame.add(BorderLayout.CENTER,paintPanel);

        Box labelBox = new Box(BoxLayout.Y_AXIS);
        field = new JTextField();
        ListenerForField forField = new ListenerForField();
        field.addActionListener(forField);
        labelBox.add(label1);
        labelBox.add(label2);
        labelBox.add(label3);
        labelBox.add(label4);
        labelBox.add(field);

        button3 = new JButton(" Замедлить");
        button2 = new JButton("  Ускорить   ");
        button1 = new JButton("  Старт          ");

        ListenerForStart forStart = new ListenerForStart();
        button1.addActionListener(forStart);

        Box buttonBox = new Box(BoxLayout.Y_AXIS);
        buttonBox.add(button1);
        buttonBox.add(button2);
        buttonBox.add(button3);

        BorderLayout layout2 = new BorderLayout();
        firstPanel.setLayout(layout2);

        firstPanel.add(BorderLayout.WEST,labelBox);
        firstPanel.add(BorderLayout.CENTER,buttonBox);
        firstPanel.add(BorderLayout.EAST,extraBox);

        BorderLayout layout3 = new BorderLayout();
        paintPanel.setLayout(layout3);
        paper = new DemoPanel();
        paintPanel.add(BorderLayout.CENTER,paper);

        BorderLayout layout4 = new BorderLayout();
        paper.setLayout(layout4);

        newPoint = new myPoint();
        newPoint.set_X_Y(0,0);
        paper.add(newPoint);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setVisible(true);
    }

    int[] x_es = new int[4];
    int[] y_es = new int[4];
    String [] strings_of_x_y = new String[4];

    public void readMassage(){
        for(int i = 0; i < 4; i++){
            String [] str = strings_of_x_y[i].split(" ");
            x_es[i] = Integer.parseInt(str[0]);
            y_es[i] = Integer.parseInt(str[1]);
        }
    } // и у меня есть готовые координаты

    class ListenerForField implements ActionListener{
        int i = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            if(i < 4){
                strings_of_x_y[i] = field.getText();
                field.setText("");
                i++;
            }
        }
    }

    class ListenerForStart implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            readMassage();
            //  теперь у меня есть массив x-ов и массив y-ов
            for(int i = 0; i < 4; i++){
                newPoint.set_X_Y(x_es[i],y_es[i]);
                newPoint.repaint();
            }
            //frame.repaint();
        }
    }

    public class DemoPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g){
            g.setColor(Color.yellow);
            g.fillRect(0,0,600,600);
        }
    }

    class myPoint extends JPanel { // точка
        int x;
        int y;
        public void set_X_Y(int x,int y){
            this.x = x;
            this.y = y;
        }
        @Override
        public void paintComponent(Graphics g){
            g.setColor(Color.black);
            g.fillRect(x,y,4,4);
        }

    }

}
