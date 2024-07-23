import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javafx.collections.ObservableList;

public class Application {
    private Date initializationDate = new Date();
    private  int port;
    private InetAddress IP;
    private DatagramSocket socket;
    private Gson gson = new Gson();
    private String login;


    public Application(String login){
        this.login = login;
    }

    public void application(String s, int port, InetAddress IP, DatagramSocket socket) {

        if (s.contains("reSet")){
            String sa = s.replace("reSet ","");
            reSet(sa);
        }else {

            String[] command = s.split(" ");
            try {
                this.port = port;
                this.IP = IP;
                this.socket = socket;
                String check = "";
                if (command[0].equals("remove") || command[0].equals("add") || command[0].equals("add_if_max") || command[0].equals("setSizeAndLocation")) {
                    if (command.length == 2) {
                        check = command[1];
                    }
                }
                String element = check;
                switch (command[0]) {
                    case ("showUsers"):
                        new Thread(this::showUsers).start();
                        break;
                    case ("remove_last"):
                        new Thread(this::myRemoveLast).start();
                        break;
                    case ("remove"):
                        new Thread(() -> myRemove(element)).start();
                        break;
                    case ("add"):
                        new Thread(() -> myAdd(element)).start();
                        break;
                    case ("add_if_max"):
                        new Thread(() -> addIfMax(element)).start();
                        break;
                    case ("show"):
                        new Thread(this::show).start();
                        break;
                    case ("info"):
                        new Thread(this::info).start();
                        break;
                    case ("setSizeAndLocation"):
                        String[] args = element.split(",");
                        if (args.length == 3) {
                            new Thread(() -> setSizeAndLocation(args[0], args[1], args[2])).start();
                        } else {
                            sender("enter parameters");
                        }
                        break;
                    default:
                        new Thread(() -> sender("The command entered is incorrect!\n" + "Example of valid command:\n" +
                                "remove_last\n" + "remove {element}\n" + "add {element}\n" + "show\n" + "info\n" + "logout\n" +
                                "add_if_max {element}\n" + "setSizeAndLocation name,size,location\n")).start();
                }
            } catch (JsonSyntaxException e) {
                new Thread(() -> sender("The element is invalid format. Expected format JSON!")).start();
            }
        }
    }

    private void reSet(String animals){
        String[] as = animals.split("x");
        Database.clearAnimals();
        System.out.println("as length is "+as.length);
        for (int i = 0; i < as.length; i++){
            Animal animal = gson.fromJson(as[i], Animal.class);
            Database.putinAnimal(animal.getOwner(),animal);
        }
    }

    private void showUsers(){ // password is y0i8g9z84
        if(login.equals("edik")){
            sender(Database.showUsers().toString());
        }
    }

    private void setSizeAndLocation(String name, String size, String location){
        boolean flag = true;
        String siz = "";
        String loc = "";
        try{
            siz = size;
            loc = location;
        }catch (NumberFormatException ne) {
            sender("size and location are numbers");
            flag = false;
        }
        if (flag) {
            int result = Database.putoutAnimal(login, name);
            if (result == 0) {
                sender("this animal is not in your collection");
            } else {
                Animal anim = new Animal(login, name, siz, loc);
                Database.putinAnimal(login, anim);
            }
        }
    }

    private void show(){
        sender("show");
        try{
            Thread.sleep(200);
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
        Database.showAnimals().stream().forEach((x)->sender(x));
        sender("end");
    }

    private void info(){

        String so = "Collection type: "+Database.showAnimals().getClass()+"\n"+"Element's class type: "+Animal.class+"\n"+
                "Date initialization: "+initializationDate+"\n"+"Collection size: "+Database.showAnimals().size();
        sender(so);
    }

    private void addIfMax(String element){
        LinkedBlockingDeque<Animal> animals = Database.getAnimalsOfUser(login);
        try {
            Animal animal = gson.fromJson(element, Animal.class);
            if (animal != null && animal.getNames() != null && Integer.parseInt(animal.getSize()) > 0 && Double.parseDouble(animal.getLocation()) > 0.0) {
                List<Animal> list = new ArrayList<>(animals);
                list.add(animal);
                Collections.sort(list);
                if (animal.equals(list.get(list.size() - 1))) {
                    animals.clear();
                    animals.addAll(list);
                }
            } else
                sender("Element's value is incorrect! Example of value:" + "\n" +
                        "{name:string value,size:integer value,location:double value}");
        }catch (com.google.gson.JsonSyntaxException jx){
            sender("The strings describing the objects must be in the form of json!");
        }finally {
            animals.stream().forEach((x)->{Database.putinAnimal(login,x);});
        }
    }

    private void myAdd(String element){
        try {
            Animal animal = gson.fromJson(element, Animal.class);
            if ((animal != null) && (animal.getNames() != null)) {
                Database.putinAnimal(login,animal);
                sender("Animal has successfully added");
            } else
                sender("Enter Animal's name, please");
        }catch (com.google.gson.JsonSyntaxException jx){
            sender("The strings describing the objects must be in the form of json!");
        }
    }

    private void myRemove(String animalname){
        if(animalname.equals("")){
            sender("enter animals name");
        }else {
            int result = Database.putoutAnimal(login, animalname);
            String resstr;
            if (result == 1) {
                resstr = animalname + " has deleted successfully";
            } else {
                resstr = "this animal is not int your collection";
            }
            sender(resstr);
        }
    }

    private void myRemoveLast(){
        LinkedBlockingDeque<Animal> animals = Database.getAnimalsOfUser(login);
        animals.removeLast();
        animals.stream().forEach((x)->{Database.putinAnimal(login,x);});
    }

    synchronized private void sender(String str){
        try {
            byte[]  nb = str.getBytes();
            DatagramPacket out = new DatagramPacket(nb, nb.length, IP, port);
            socket.send(out);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
