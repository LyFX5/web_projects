import javax.sound.midi.*;
public class MiniMusicCmdLine {// first class
    public static void main(String[] args){
        MiniMusicCmdLine mini = new MiniMusicCmdLine();
        args = new String[]{"40", "70"};
        if(args.length < 2){
            System.out.println("Не забудьте аргументы для инструмента и ноты");
        }else{
            int instrument = Integer.parseInt(args[0]);
            int note = Integer.parseInt(args[1]);
            mini.play(instrument,note);
        }
    } // Конец main
    public void play(int instrument, int note){
        try{
            Sequencer player = MidiSystem.getSequencer();
            player.open();
            Sequence seq = new Sequence(Sequence.PPQ,4);
            Track track = seq.createTrack();

            MidiEvent event = null;

            ShortMessage first = new ShortMessage();
            first.setMessage(192,1,instrument,0);
            MidiEvent changeInstrument = new MidiEvent(first,1);
            track.add(changeInstrument);

            ShortMessage a = new ShortMessage();
            a.setMessage(144,1,note,100);
            MidiEvent noteOn = new MidiEvent(a,1);
            track.add(noteOn);

            ShortMessage b = new ShortMessage();
            b.setMessage(128,1,note,100);
            MidiEvent noteOff = new MidiEvent(b,16);
            track.add(noteOff);
            player.setSequence(seq);
            player.start();
        }catch (Exception ex){ex.printStackTrace();}
    }//Конец play
}//Конц class11
