import javax.sound.midi.*;
public class MiniMinimusicApp {
    public static void main(String[] args){
        MiniMinimusicApp mini = new MiniMinimusicApp();
        mini.play();
    }
    public void play(){
        try {
            Sequencer player = MidiSystem.getSequencer();// 1
            player.open();// 1
            Sequence seq = new Sequence(Sequence.PPQ, 4);//2
            Track track = seq.createTrack();//3
            ShortMessage a = new ShortMessage();//4
            a.setMessage(144,1,44,100);//4
            MidiEvent noteOn = new MidiEvent(a,1);//4
            track.add(noteOn);//4

            ShortMessage b = new ShortMessage();//4
            b.setMessage(128,1,44,100);//4
            MidiEvent noteOff = new MidiEvent(b,16);//4
            track.add(noteOff);//4

            player.setSequence(seq);

            player.start();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }// close play
}// close class
