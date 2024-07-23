import java.io.*;
import java.util.ArrayList;

public class Adder {

    private static final File file = new File("edik.txt"); // file

    public static ArrayList<String> firstArray = new ArrayList();

    public static ArrayList<String> secondArray = new ArrayList();

    public static void start(){

    }

    public static void saver(ArrayList<String> firstArray,ArrayList<String> secondArray){
        BufferedWriter buw = null;
        try {
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            buw = new BufferedWriter(osw);
        }catch (IOException iox){}
        for (int i = 0; i < firstArray.size(); i++) {
            try {
                buw.write(firstArray.get(i) + "Ⴀ" + secondArray.get(i));
                buw.newLine();
            } catch (IOException ex) { }
        }
        try {
            buw.close();
        }catch (IOException ioex){}
    }

    public static void ReadAndFil() {
        FileInputStream fis;
        InputStreamReader isr;
        BufferedReader bure;
        String string;
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            bure = new BufferedReader(isr);
            try{
                while ((string = bure.readLine()) != null){
                    String[] strings = string.split("Ⴀ");
                    firstArray.add(strings[0]);
                    secondArray.add(strings[1]);
                }
                bure.close();
            }catch (IOException ioex){}

        }catch (FileNotFoundException ioex){}
    }

}
