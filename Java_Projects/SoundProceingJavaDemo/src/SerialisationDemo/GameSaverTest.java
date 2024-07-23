package SerialisationDemo;

import java.io.*;

public class GameSaverTest {
    public static void main(String[] args){
        /*GameCharacter elf = new GameCharacter(50,"Эльф", new String[]{"лук", "мечь", "кастет"});
        GameCharacter trol = new GameCharacter(200,"троль",new String[] {"голые руки","большой топор"});
        GameCharacter mag = new GameCharacter(120,"маг",new String[] {"заклинания","невидимсть"});
        // представьте код который мог бы изменить состояния объектов

        try {
            FileOutputStream fos = new FileOutputStream("C:\\Users\\Edik kek\\Desktop\\Java_pr\\study_sound\\src\\SerialisationDemo\\GameCharacters.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(elf);
            oos.writeObject(trol);
            oos.writeObject(mag);
            oos.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        elf = null;
        trol = null;
        mag = null;*/

        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\Edik kek\\Desktop\\Java_pr\\study_sound\\src\\SerialisationDemo\\GameCharacters.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            GameCharacter elf = (GameCharacter) ois.readObject();
            GameCharacter trol = (GameCharacter) ois.readObject();
            GameCharacter mag = (GameCharacter) ois.readObject();
            ois.close();
            System.out.println("тип эльфа " + elf.getType());
            System.out.println("тип троля " + trol.getType());
            System.out.println("тип мага " + mag.getType());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
