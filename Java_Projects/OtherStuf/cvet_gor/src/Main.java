import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // При запуске приложения коллекция должна автоматически заполняться значениями из файла.
        // Имя файла должно передаваться программе с помощью аргумента командной строки.

        if(args.length == 0){
            System.out.println("Specify the file,please.");
        }else {
            File file = new File(args[0]);
            ForLab lab = new ForLab();
            lab.ReadFileAndFillArray(file);
            lab.communicate();
        }
    }
}