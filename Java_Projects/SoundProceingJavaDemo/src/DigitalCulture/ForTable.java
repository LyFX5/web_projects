package DigitalCulture;


import javenue.csv.*;
import java.io.*;
import java.util.*;


public class ForTable {

    HashSet<String> groups = new HashSet();

    public static void main(String[] args){
        ForTable ft = new ForTable();
        ft.counter(new File("C:\\Users\\Edik kek\\Desktop\\2_semestr\\ЦК\\Бакалавры_final.csv"));
        //ft.grouper(new File("C:\\Users\\Edik kek\\Desktop\\2_semestr\\ЦК\\Безопасности информационных технологий.csv"));
        //ft.reader(new File("C:\\Users\\Edik kek\\Desktop\\2_semestr\\ЦК\\Бакалавры_final.csv"));
    }
    File outfile = new File("C:\\Users\\Edik kek\\Desktop\\2_semestr\\ЦК\\Группы БИТ-а.csv");

    public void reader(File infile){
        BufferedReader buRe;
        BufferedWriter buWr;
        try {
            FileReader fileReader = new FileReader(infile);
            buRe = new BufferedReader(fileReader);

            FileWriter fileWriter = new FileWriter(outfile);
            buWr = new BufferedWriter(fileWriter);
            String string;
            for(int i = 0; i < 1395; i++){
                string = buRe.readLine();
                if((String.valueOf(string.charAt(1))).equals("Z")) {
                    buWr.write(string);
                    buWr.newLine();
                }
            }
            buRe.close();
            buWr.close();
        }catch (IOException ioex){ioex.printStackTrace();}
    }

    public void grouper(File infile){
        BufferedReader buRe;
        String[] strings;
        int sum = 0;
        int j = 0;
        try {
            FileReader fr = new FileReader(infile);
            buRe = new BufferedReader(fr);
            for (int i = 0; i < 143; i++){
                strings = buRe.readLine().split(";");
                if (strings[0].equals("\"N3147")){
                    sum = sum +Integer.parseInt(strings[17]);
                    j++;
                }
            }
            System.out.println("N3147" + ";" + sum/j);
            buRe.close();
        }catch (IOException io){io.printStackTrace();}
    } // end


    public void counter(File file){
        BufferedReader buRe;
        String[] strings;
        int j = 0;
        try {
            FileReader fileReader = new FileReader(file);
            buRe = new BufferedReader(fileReader);
            for(int i = 0; i < 1395; i++){
                strings = buRe.readLine().split(";");
                if(strings[6].equals("100")){
                    j++;
                }
            }
            buRe.close();
            System.out.println(j);
        }catch (IOException ioex){ioex.printStackTrace();}
    }
}
