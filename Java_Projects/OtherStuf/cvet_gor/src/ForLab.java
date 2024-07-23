import java.io.*;
import com.google.gson.Gson;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This is class ForLab
 * that is intended for demonstration
 * working with collection in
 * interactive mode
 */
public class ForLab {

    /**
     * This field contains ArrayDeque of Homes
     */
    private ArrayDeque<Home> homes = new ArrayDeque();

    private Date initializationDate = new Date();

    private File sourceFile;

    private boolean flag = true;

    public Date getInitializationDate(){
        return initializationDate;
    }

    public ArrayDeque<Home> getHomes(){
        return homes;
    }

    public void communicate() { // for communication
        if(flag){System.out.println("Lab5");}
        String str;
        try {
            // чтение с консои
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            String[] strings;
            while (flag) {
                str = br.readLine();

                strings = str.split(" ");

                if (strings[0].equals("add") && strings.length == 2) {
                    try {
                        myAdd(new Gson().fromJson(strings[1], Home.class));
                        System.out.println("object is successfully added");
                    }catch (com.google.gson.JsonSyntaxException ex){
                        System.err.println("The string describing the object must be in the form of json!");
                    }
                } else {
                    if (strings[0].equals("remove_greater") && strings.length == 2) {
                        try {
                            remove_greater(new Gson().fromJson(strings[1], Home.class));
                        }catch (com.google.gson.JsonSyntaxException ex){
                            System.err.println("The string describing the object must be in the form of json!");
                        }
                    } else {
                        if (strings[0].equals("show")) {
                            show();
                        } else {
                            if (strings[0].equals("save")) {
                                saveState(sourceFile);
                            } else {
                                if (strings[0].equals("clear")) {
                                    clear();
                                } else {
                                    if (strings[0].equals("info")) {
                                        info();
                                    } else {
                                        if (strings[0].equals("remove") && strings.length == 2) {
                                            try {
                                                remove(new Gson().fromJson(strings[1], Home.class));
                                            }catch (com.google.gson.JsonSyntaxException ex){
                                                System.err.println("The string describing the object must be in the form of json!");
                                            }
                                        } else {
                                            if (strings[0].equals("close")) {
                                                saveState(sourceFile);
                                                flag = false;
                                            } else {
                                                System.err.println("The command is not maintained!" + "\n" + "There are all commands:" + "\n"
                                                        + "add; "+"remove_greater; "+"show; "+"save; "+"clear; "+"info; "+"remove; "+"close.");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // чтение с консоли
        }catch (IOException ioexc){
            System.err.println("Input EXCEPTION!");
            ioexc.printStackTrace();
        }
    } // end of method communication

    /**
    * method ReadFileAndFillArray
     * reads the objects from file
     * and fills with them ArrayDeque
     * the file format mast be CSV
     * and objects's format mast be json.
     * @param file
     */
    public void ReadFileAndFillArray(File file){
        sourceFile = file;
        Gson gson = new Gson();
        FileInputStream fis;
        InputStreamReader isr;
        BufferedReader buRe;
        String str;
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            buRe = new BufferedReader(isr);
            try {
                try {
                    while ((str = buRe.readLine()) != null){
                        myAdd(gson.fromJson(str,Home.class));
                    }
                }catch (com.google.gson.JsonSyntaxException ex) {
                    System.err.println("The strings in file describing the objects must be in the form of json!");
                    flag = false;
                }finally {
                    buRe.close();
                }
            }catch (IOException ioex){System.err.println("Input EXCEPTION!"); flag = false;}

        }catch (java.io.FileNotFoundException fnfex){System.err.println("File was not founded!"); flag = false;}
    } // end of method ReadFileAndFillArray

    /**
     * method saveState saves a state of ArrayDeque in file
     * files format should be CSV
     * elements that will appeared it file will have format json
     * method can be cosed from terminal by this way: save
     * @param file
     */
    public void saveState(File file){
        Gson gson = new Gson();
        FileOutputStream fos;
        OutputStreamWriter osw;
        BufferedWriter buWr;
        try {
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos);
            buWr = new BufferedWriter(osw);
            /*while (!homes.isEmpty()) {
                buWr.write(gson.toJson(homes.removeFirst()));
                buWr.newLine();
            }*/
            homes.stream()
                    .forEach((x) -> {
                        try {
                            buWr.write(gson.toJson(x));
                            buWr.newLine();
                        }catch (IOException writeEx){ System.err.println("Output EXCEPTION during writing"); }
                        });

            buWr.close();
        }catch (IOException buex){
            System.err.println("Output EXCEPTION");
            buex.printStackTrace();
        }
    } // end of method saveState

    /**
     * method myAdd
     * adds its parameter at ArrayDeque of Homes
     * can be cosed from terminal by this way: add element
     * element's format mast be json
     * @param home
     */
    public void myAdd(Home home){
        homes.addLast(home);
    }

    /**
     * method remove_greater
     * removes from ArrayDeque all Homes that have number bigger then parameter's number
     * can be cosed from terminal by this way: remove_greater element
     * element's format mast be json
     * after calling of this method homes in ArrayDeque became sorted by there numbers
     * @param greaterHome
     */
    public void remove_greater(Home greaterHome) {
        getHomes().stream()
                .filter(x -> x.compareTo(greaterHome) > 0)
                .forEach(x -> homes.remove(x));
    }

    /**
     * method show
     * outputs in standard output stream
     * an ArrayDeque of homes
     * can be cosed from terminal by this way: show
     */
    public void show() {
        System.out.println(getHomes());
    }

    /**
     * method clear
     * clears an ArrayDeque of homes
     * can be cosed from terminal by this way: clear
     */
    public void clear(){
        homes.clear();
    }

    /**
     * method info
     * outputs in standard output stream
     * collection class
     * element class
     * number of elements
     * initialization date
     * can be cosed from terminal by this way: info
     */
    public void info(){
        System.out.println("Collection class: " + getHomes().getClass()+ "\n"+ "Element class: " + Home.class + "\n"
                + "Number of elements: " + getHomes().size() + "\n" + "Initialization date: " + getInitializationDate());
    }

    /**
     * method remove
     * removes element by value from ArrayDeque
     * can be cosed from terminal by this way: remove element
     * element's format mast be json
     * after calling of this method homes in ArrayDeque became sorted by there numbers
     * @param home
     */
    public void remove(Home home){
        homes.remove(home);
    }

    //Команда import должна использовать
    // файл из файловой системы клиента (содержимое файла передается на сервер), load и save - сервера.
    public void importFile(String fileName) throws IOException{
        BufferedReader bure = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String string;

        SocketAddress sa = new InetSocketAddress("port",5000); // напиши какой надо
        DatagramChannel dch = DatagramChannel.open();
        dch.connect(sa);
        ByteBuffer bbuf;

        while (!(string = bure.readLine()).equals("")){
            bbuf = ByteBuffer.wrap(string.getBytes());
            dch.write(bbuf);
        }
    }
}
