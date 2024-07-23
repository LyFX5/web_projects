import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.*;
public class ForGUI {
    JTextArea firstText = new JTextArea(10,10);
    JTextArea secondText = new JTextArea(10,10);
    JLabel askingLabel = new JLabel("Выберите операцию");
    JButton save = new JButton("Найти файл"); // 4
    JButton clear = new JButton(" Удалить "); // 3
    JButton add = new JButton(" Добавить "); // 1
    JButton search = new JButton(" Искать "); // 2
    public void setUpGUI(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //==========================
        JPanel background = new JPanel();
        frame.add(BorderLayout.CENTER, background);
        BorderLayout mainLayout = new BorderLayout();
        background.setLayout(mainLayout);

        JPanel pult = new JPanel();
        pult.setLayout(new BorderLayout());

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel,BoxLayout.X_AXIS));

        firstText.setLineWrap(true);
        firstText.setWrapStyleWord(true);
        secondText.setLineWrap(true);
        secondText.setWrapStyleWord(true);

        JScrollPane scrollerFirst = new JScrollPane(firstText);
        scrollerFirst.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollerFirst.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JScrollPane scrollerSecond = new JScrollPane(secondText);
        scrollerSecond.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollerSecond.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        textPanel.add(scrollerFirst);
        textPanel.add(scrollerSecond);

        background.add(BorderLayout.NORTH,textPanel);
        background.add(BorderLayout.CENTER,pult);
        // добавил текстовые поля и пульт управления

        JPanel buttonAndLabelPanel = new JPanel();
        buttonAndLabelPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
        buttonPanel.add(add);
        buttonPanel.add(search);
        buttonPanel.add(save);
        buttonPanel.add(clear);

        buttonAndLabelPanel.add(BorderLayout.NORTH,askingLabel);
        buttonAndLabelPanel.add(BorderLayout.CENTER,buttonPanel);

        pult.add(BorderLayout.NORTH,buttonAndLabelPanel);

        //===============
        add.addActionListener(new ListenerForAdd());
        search.addActionListener(new ListenerForSearch());
        save.addActionListener(new ListenerForSave());

        //=========================
        frame.setSize(500,500);
        frame.setVisible(true);
    }

    public class ListenerForAdd implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if((!firstText.getText().equals("")) && (!secondText.getText().equals(""))){
                if(!Adder.firstArray.contains(firstText.getText()) & !Adder.secondArray.contains(secondText.getText())) {
                    Adder.firstArray.add(firstText.getText());
                    Adder.secondArray.add(secondText.getText());
                    firstText.setText("");
                    secondText.setText("");
                }else {
                    askingLabel.setText("соответствие уже существует");
                }
            }else {
                askingLabel.setText("Нельзя добавить пустое соответствие");
            }
        }
    }

    public void getTextByIndex(JTextArea fir, JTextArea sec){ // первый не ноль второй ноль
        String str = fir.getText();
        int indexStrInFir = Adder.firstArray.indexOf(str);
        int indexStrInSec = Adder.secondArray.indexOf(str);
        if (indexStrInFir != -1){
            sec.setText(Adder.secondArray.get(indexStrInFir));
        }else {
            if (indexStrInSec != -1) {
                sec.setText(Adder.firstArray.get(indexStrInSec));
            }else { askingLabel.setText("нет соответствия с таким элементом"); }
        }
    }

    public class ListenerForSearch implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if((!firstText.getText().equals("")) & (secondText.getText().equals(""))){
                getTextByIndex(firstText,secondText);
            }
            if((firstText.getText().equals("")) & (!secondText.getText().equals(""))){
                getTextByIndex(secondText,firstText);
            }
            if (((firstText.getText().equals("")) & (secondText.getText().equals("")))){
                askingLabel.setText("Какое соответствие искать?");
            }
            if (((!firstText.getText().equals("")) & (!secondText.getText().equals("")))){
                askingLabel.setText("Какое соответствие искать?");
            }
        }
    }

    public class ListenerForSave implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
           Adder.saver(Adder.firstArray,Adder.secondArray);
        }
    }
}
