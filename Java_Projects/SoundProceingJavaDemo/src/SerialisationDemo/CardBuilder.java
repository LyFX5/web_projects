package SerialisationDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CardBuilder {

    JFrame frame;
    JPanel background;
    JPanel questionPanel;
    JPanel answerPanel;
    JTextArea questionField;
    JTextArea answerField;
    JButton saveQuestion;
    JButton saveAnswer;
    Card nextCard;

    public void setUpGUI(){
        frame = new JFrame();
        background = new JPanel();
        questionPanel = new JPanel();
        answerPanel = new JPanel();
        questionField = new JTextArea();
        answerField = new JTextArea();

        saveQuestion = new JButton("Save Question");
        ButtonForQuestionListener BQL = new ButtonForQuestionListener();
        saveQuestion.addActionListener(BQL);

        saveAnswer = new JButton("Save Answer");
        ButtonForAnswerListener BAL = new ButtonForAnswerListener();
        saveAnswer.addActionListener(BAL);

        BorderLayout backgroundLayout = new BorderLayout();
        background.setLayout(backgroundLayout);

        frame.add(BorderLayout.CENTER,background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        background.add(BorderLayout.NORTH,questionPanel);
        background.add(BorderLayout.SOUTH,answerPanel);

        BorderLayout northPanelLayout = new BorderLayout();
        BorderLayout southPanelLayout = new BorderLayout();
        questionPanel.setLayout(northPanelLayout);
        answerPanel.setLayout(southPanelLayout);

        questionPanel.add(BorderLayout.NORTH,questionField);
        questionPanel.add(BorderLayout.SOUTH,saveQuestion);

        answerPanel.add(BorderLayout.NORTH,answerField);
        answerPanel.add(BorderLayout.SOUTH,saveAnswer);

        frame.setSize(500,500);
        frame.setVisible(true);
    }

    public void saveText(String question_or_answer, String string){
        try {
            FileWriter writer = null;
            if(question_or_answer == "q") {
                writer = new FileWriter("C:\\Users\\Edik kek\\Desktop\\Java_pr\\study_sound\\src\\SerialisationDemo\\Question.txt");
            }
            if(question_or_answer == "a"){
                writer = new FileWriter("C:\\Users\\Edik kek\\Desktop\\Java_pr\\study_sound\\src\\SerialisationDemo\\Answer.txt");
            }
            writer.write(string);
            writer.close();
        }catch (IOException ioex){
            ioex.printStackTrace();
        }
    }

    class ButtonForQuestionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String question = questionField.getText();
            saveText("q",question);
            questionField.setText("");
        }
    }

    class ButtonForAnswerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String answer = answerField.getText();
            saveText("a",answer);
            answerField.setText("");
        }
    }
}
