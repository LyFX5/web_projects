import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Application {
    private Date initializationDate = new Date();
    private  int port;
    private InetAddress IP;
    private DatagramSocket socket;
    private Gson gson = new Gson();
    private LinkedBlockingDeque<Animal> animals = new LinkedBlockingDeque<>();
    private String login;


    public Application(String login){
        this.login = login;
        animals = Database.getAnimalsOfUser(login);
    }

    public void application(String s, int port, InetAddress IP, DatagramSocket socket) {
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                try {
                    close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        String[] command = s.split(" ");
                try {
                    this.port = port;
                    this.IP = IP;
                    this.socket = socket;
                    String check = "";
                    if(command[0].equals("remove")||command[0].equals("add")||command[0].equals("add_if_max")){
                        check = command[1];
                    }
                    String element = check;
                    switch (command[0]) {
                        case ("remove_last"):
                            new Thread(()->{if (animals.size() > 0)
                                animals.removeLast();
                            else
                                sender("Unable to execute command! Collection is empty.");}).start();
                            break;
                        case ("remove"):
                            new Thread(()->myRemove(element)).start();
                            break;
                        case ("add"):
                            new Thread(()->myAdd(element)).start();
                            break;
                        case ("add_if_max"):
                            new Thread(()->addIfMax(element)).start();
                            break;
                        case ("show"):
                            new Thread(()->show(animals)).start();
                            break;
                        case ("info"):
                            new Thread(this::info).start();
                            break;
                        case ("showAll"):
                            new Thread(this::showAll).start();
                            break;
                        case("save"):
                            new Thread(()->{
                                try{
                                    close();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                            }).start();
                            break;
                        default:
                            new Thread(()->sender("The command entered is incorrect!\n" + "Example of valid command:\n" +
                                    "remove_last\n" + "remove {element}\n" + "add {element}\n" + "show\n" + "info\n" + "load\n" +
                                    "add_if_max {element}\n" + "import\n"+"save\n")).start();
                    }
                } catch (JsonSyntaxException e) {
                    new Thread(()->sender("The element is invalid format. Expected format JSON!")).start();
                }
    }

    private void show(LinkedBlockingDeque<Animal> a){
        ArrayList<Animal> list = new ArrayList<>(a);
        Collections.sort(list);
        sender(list.toString());
    }

    private void showAll(){
        LinkedBlockingDeque<String> strings = Database.showAnimals();
        for(String string: strings){
            sender(string);
        }
        for(Animal string: animals){
            sender(string.toString()
            );
        }
    }

    private void info(){
        String so = "Collection type: "+animals.getClass()+"\n"+"Element's class type: "+Animal.class+"\n"+
                "Date initialization: "+initializationDate+"\n"+"Collection size: "+animals.size();
        sender(so);
    }

    private void close() throws IOException { // надо что бы работал только тогда когда потрт прекращает посылать сигнал
         animals.stream().forEach((x)->{
             Database.putinAnimal(login,x);
         });
    }

    private void addIfMax(String element){
        try {
            Animal animal = gson.fromJson(element, Animal.class);
            if (animal.getName() != null && animal.getSize() > 0 && animal.getLocation() > 0.0) {
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
        }
    }

    private void myAdd(String element){
        try {
            Animal animal = gson.fromJson(element, Animal.class);
            if (animal.getName() != null) {
                animals.add(animal);
            } else
                sender("Enter Animal's name, please");
        }catch (com.google.gson.JsonSyntaxException jx){
            sender("The strings describing the objects must be in the form of json!");
        }
    }

    private void myRemove(String element){
        Animal animal = null;
        try {
            animal = gson.fromJson(element, Animal.class);
        }catch (com.google.gson.JsonSyntaxException ex) {
            sender("The strings describing the objects must be in the form of json!");
        }
        if (animals.contains(animal)){
            animals.remove(animal);
        }else {
            sender("permission denate or in your collection tere is no such element");
        }
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
