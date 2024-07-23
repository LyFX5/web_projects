package BeatBox;
import javax.swing.*;
import javax.sound.midi.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
public class MyBeatBox {
    JFrame frame;
    JPanel mainPanel;
    ArrayList<JCheckBox> checkBoxes;
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    String[] instrumentsNames = {"Bass Drum","Closed Hi-Hat", "Open Hi-Hat",
            "Acoustic Snare", "Crash Cymbal","Hand Clap","High Tom",
            "Hi Bongo","Maracas","Whistle","Low Conga",
            "Cowbell","Vibraslap","Low-mid Tom","High Agogo","Open Hi Conga"};
    int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};

    public void set_up_GUI(){
        frame = new JFrame("Cyber BeatBox");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        checkBoxes = new ArrayList<JCheckBox>();

        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        JButton start = new JButton("start");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        JButton stop = new JButton("stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        JButton upTempo = new JButton("Temp up");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Temp down");
        downTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for(int i = 0; i < 16; i++){
            nameBox.add(new Label(instrumentsNames[i]));
        }

        background.add(BorderLayout.EAST,buttonBox);
        background.add(BorderLayout.WEST,nameBox);

        frame.getContentPane().add(background);

        GridLayout grid = new GridLayout(16,16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER,mainPanel);

        for(int i = 0; i < 256; i++){
            JCheckBox ch = new JCheckBox();
            ch.setSelected(false);
            checkBoxes.add(ch);
            mainPanel.add(ch);
        }

        set_up_MIDI();

        frame.setBounds(50,50,300,300);
        frame.pack();
        frame.setVisible(true);
    }

    public void set_up_MIDI(){
        try{
         sequencer = MidiSystem.getSequencer();
         sequencer.open();
         sequence = new Sequence(Sequence.PPQ,4);
         track = sequence.createTrack();
         sequencer.setTempoInBPM(120);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void buildTrack_and_start(){
        int[] track_list = null; // Чтобы хранить значения для каждого инструмента на все 16 тактов
        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for(int i = 0; i < 16; i++){ // Проходимся по инструментам
            track_list = new int[16]; // Делаем это для каждого из 16 инструментов

            int key = instruments[i]; // Создаём клавишу которая представляет инструмент. Массив содержит MIDI-числа.

            for (int j = 0; j < 16; j++){ // Делаем это для каждого такта текущего ряда(текущего инструмента)
                JCheckBox jch = (JCheckBox) checkBoxes.get(j + (16 * i));
                if (jch.isSelected()){   // Если флажок установлен
                    track_list[j] = key; // то помещаем значение клавиши в текущую ячейку массива(ячейку которая представляет такт).
                }else {                  // Если нет то инструмент не должен играть в этом такте
                    track_list[j] = 0;   // поэтому присваеваем ей ноль
                }
            } // Закрываем внутрений цикл

            makeTracks(track_list); // Для этого инструмента и для всех 16 тактов
            track.add(makeEvent(176,1,127,0,16)); // создаём MIDI-события и добавляем их на дорожку
        } // закрываем внешний цикл

        track.add(makeEvent(192,9,1,0,15)); // Мы всегда должни быть уверены что событие на такте 16 существует(они идут от 0 до 15)
        // Иначе BeatBox может не пройти все 16 тактов перед тем как заново начнёт последовательность
        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY); // Позволяет задать колличество повторений цикла
            // или как в этом случае непрерывный цикл
            sequencer.start();
            sequencer.setTempoInBPM(120);
            // теперь мы проигрываем мелодию
        }catch (Exception ex){ex.printStackTrace();}
    } // закрываем метод buildTrack_and_start()

    public class MyStartListener implements ActionListener{ // Слушатель для кнопки start
        @Override
        public void actionPerformed(ActionEvent e) {
            buildTrack_and_start();
        }
    }// закрываем внутренний класс

    public class MyStopListener implements ActionListener{ // слушатель для кнопки stop
        @Override
        public void actionPerformed(ActionEvent e) {
            sequencer.stop();
        }
    } // закрываем внутрений класс

    public class MyUpTempoListener implements ActionListener{ // слушатель для Tempo up
        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(tempoFactor * 1.03));
        }
    } // закрываем

    // по умолчанию темп синтезатора 1.0. Теперь щелчком мыши можно увеличить его на 3% и уменьшить  на 3%

    public class MyDownTempoListener implements ActionListener{ // слушатель Down Tempo
        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(tempoFactor * 0.97));
        }
    } // закрываем

    public void makeTracks(int[] list){ // Метод создаёт события для одного инструмента за каждый проход цикла для всех 16 тактов.
        for(int i = 0; i < 16; i++){ // Можно получить int[] для какого-либо инструмента и каждый элемент массива будет
            int key = list[i]; //содержать либо клавишу этого инструмента либо ноль.
            if(key != 0){ // Если ноль то инструмент не должен играть на текущем такте.
                // Иначе нужно создать событие и добавить его в дорожку.
                track.add(makeEvent(144,9,key,100,i)); // Создаём события включения
                track.add(makeEvent(128,9,key,100,i+1)); // и выключения и добавляем их в дорожку
            }
        }
    }

    public MidiEvent makeEvent(int command,int chanel,int data1,int data2,int tick){ // Метод для упрощения создания MIDI-событий
        MidiEvent event = null;
        try{
            ShortMessage massage = new ShortMessage();
            massage.setMessage(command,chanel,data1,data2);
            event = new MidiEvent(massage,tick);
        }catch (Exception ex){ex.printStackTrace();}
        return event;
    }
}
