import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class MiniMusicAp2  {
    public static void main(String[] args){
        MiniMusicAp2 mini = new MiniMusicAp2();
        mini.go();
    }

    static JFrame frame = new JFrame("Мой первый музыкальный клип");
    static My_panel panel;

    public void set_up_GUI(){
        panel = new My_panel();
        frame.setContentPane(panel);
        frame.setBounds(30,30,300,300);
        frame.setSize(300,300);
        frame.setVisible(true);
    }

    public class My_panel extends JPanel implements ControllerEventListener{ //реализуем интерфейс слушателя событий ControllerEvent

        private boolean msg =  false;

        @Override
        public void controlChange(ShortMessage event) { // Переопределяем метод обратной связи
            msg = true;
            repaint();
        }

        @Override
        public void paintComponent(Graphics g){ // Метод вызываемый графической системмой при создании графического объекта
            if(msg){

                int re = (int)(Math.random()*255);
                int gr = (int)(Math.random()*255);
                int bl = (int)(Math.random()*255);
                g.setColor(new Color(re,gr,bl));

                int ht = (int)((Math.random()*120) + 10);
                int wt = (int)((Math.random()*120) + 10);
                int x = (int)((Math.random()*40)+10);
                int y = (int)((Math.random()*40)+10);

                g.fillRect(x,y,wt,ht);
                msg = false;
            }
        }
    }

    public MidiEvent MakeEvent(int command, int chanel, int data1,int data2,int tick){ // Метод для упрощения создания MIDI-сбытия
        MidiEvent event = null; // Объявляем MIDI-событие
        try {
            ShortMessage message = new ShortMessage(); // Создаём сообщение
            message.setMessage(command,chanel,data1,data2); // Настраиваем сообщение
            event = new MidiEvent(message,tick); // Реализуем событие указывая ему на сообщение и на такт на котором это сообщение нужно прочесть
        }catch (Exception ex){}
        return event; // Возвращаем готовое сообщение
    }

    public void go(){
        set_up_GUI();
        try {
            Sequencer sequencer = MidiSystem.getSequencer(); // Получаем синтезатор который будет проигрывать последовательность
            sequencer.open(); // открываем его

            int[] eventsIWant = {127}; // Список событий ControllerEvent которые которые нам нужны
            sequencer.addControllerEventListener(panel,eventsIWant); // Регистрируем слушателя (это граффический объект) и передаём сиписок

            Sequence seq = new Sequence(Sequence.PPQ,4); // Создаём новую последовательность для синтезатора
            Track track = seq.createTrack(); // Добавляем новый трек в последовательность


            for(int i = 5; i < 60; i+= 4){ //Заполняем трек событиями
                track.add(MakeEvent(144,1,i,100,i));

                track.add(MakeEvent(176,1,127,0,i)); // Добавляем наше собственное событе.
                // 176 означает что тип события ControllerEvent. Оно ничего не будет делать. мы вставляем его лишь для того чтобы
                // иметь возможность реагировать на возпроизведение каждой ноты

                track.add(MakeEvent(128,1,i,100,i+2));
            }

            sequencer.setSequence(seq); // Ставим последовательность в синтезатор
            sequencer.setTempoInBPM(220); // Устанавливаем темп
            sequencer.start(); // Запускаем синтезатор

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


}
