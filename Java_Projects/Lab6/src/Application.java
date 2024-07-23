import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Application {
    private Date initializationDate = new Date();
    private DatagramSocket socket;
    private int port;
    private InetAddress IP;
    private boolean flag_forImport = false;
    private HashMap<Integer,LinkedBlockingDeque<Animal>> clients = new HashMap<>();
    Gson egson = new Gson();

    public void application(String s) {
        int server_port = Integer.parseInt(s);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        String[] command;
        try {
            socket = new DatagramSocket(server_port);
            byte[] b = new byte[1024];
            DatagramPacket rec = new DatagramPacket(b, b.length);
            while (true) {
                try {
                    socket.receive(rec);
                    byte[] data = rec.getData();
                    port = rec.getPort();
                    IP = rec.getAddress();
                    if(!clients.containsKey(port)){
                        clients.put(port,new LinkedBlockingDeque<Animal>());
                    }
                    command = new String(data, 0, rec.getLength()).split(" ");
                    String check = "";
                        if (command[0].equals("remove") || command[0].equals("add") || command[0].equals("add_if_max")) {
                            if(command.length == 2) {
                                check = command[1];
                            }else {
                                command[0] = "";
                            }
                        }
                    String element = check;
                    switch (command[0]) {
                        case ("import"):
                            myImport(rec);
                            break;
                        case ("remove_last"):
                            new Thread(()->{if (clients.get(port).size() > 0)
                                clients.get(port).removeLast();
                            else
                                sender("Unable to execute command! Collection is empty.",IP,port);}).start();
                            break;
                        case ("remove"):
                            new Thread(()->myRemove(element)).start();
                            break;
                        case ("add"):
                            new Thread(()->myAdd(element,IP,port)).start();
                            break;
                        case ("add_if_max"):
                            new Thread(()->addIfMax(element,IP,port)).start();
                            break;
                        case ("show"):
                            new Thread(this::show).start();
                            break;
                        case ("info"):
                            new Thread(()-> info(IP,port)).start();
                            break;
                        case ("load"):
                            if(new File("Client"+port+".xml").exists()) {
                                new Thread(() -> {
                                    try {
                                        readFile("Client" + port + ".xml");
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }).start();
                            }
                            else new Thread(() -> sender("File does not imported!", IP, port)).start();
                            break;
                        case ("exit"):
                                new Thread(() -> {
                                    try {
                                        close();
                                    }catch (IOException io){new Thread(()->sender("IOException",IP,port)).start();}
                                }
                                ).start();
                            break;
                        case("save"):
                            new Thread(() -> {
                                try {
                                    close();
                                }catch (IOException io){new Thread(()->sender("IOException",IP,port)).start();}
                            }
                            ).start();
                            break;
                        default:
                            new Thread(()->sender("The command entered is incorrect!\n" + "Example of valid command:\n" +
                                    "remove_last\n" + "remove {element}\n" + "add {element}\n" + "show\n" + "info\n" + "load\n" +
                                    "add_if_max {element}\n" + "exit\n",IP,port)).start();
                    }
                } catch (JsonSyntaxException e) {
                    new Thread(()->sender("The element is invalid format. Expected format XML!",IP,port)).start();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * method readFile
     * reads the objects from file
     * and fills with them LinkedList
     * the file format must be XML
     * and objects's format mast be XML.
     * @param s
     */
    private void readFile(String s) throws FileNotFoundException{
        String[] results;
        String n = "";
        int si = 0;
        double l = 0.0;
        clients.get(port).clear();
        File file = new File(s);
        Scanner scanner = new Scanner(file);
        try {
            while (scanner.hasNext()) {
                String line = scanner.nextLine().trim();
                if (line.contains("<Animal ")) {
                    results = line.split(" ");
                    for(String res: results){
                        if(res.contains("=")){
                            switch (res.split("=")[0]) {
                                case "name":
                                    n = res.split("=")[1].substring(1,res.split("=")[1].length()-1);
                                    break;
                                case "location":
                                    l = Double.parseDouble(res.split("=")[1].substring(1,res.split("=")[1].length()-3));
                                    break;
                                case "size":
                                    si = Integer.parseInt(res.split("=")[1].substring(1,res.split("=")[1].length()-1));
                                    break;
                            }
                        }
                    }
                    LinkedBlockingDeque<Animal> ls = clients.get(port);
                    ls.add(new Animal(n, si, l));
                    clients.replace(port,clients.get(port),ls);
                    ls = null;
                }
            }
        }catch(Exception e){
            new Thread(()->sender("The file contains invalid data. Expected format XML!",IP,port)).start();
        }
    }

    /**
     * method show
     * outputs in standard output stream
     * an LinkedList of homes
     * can be cosed from terminal by this way: show
     */
    private void show(){
        ArrayList<Animal> list = new ArrayList<>(clients.get(port));
        Collections.sort(list);
        clients.replace(port,clients.get(port),new LinkedBlockingDeque<>(list));
        sender(clients.get(port).toString(),IP,port);
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
    private void info(InetAddress IP, int port){
        String so = "Collection type: "+clients.get(port).getClass()+"\n"+"Element's class type: "+Animal.class+"\n"+
                "Date initialization: "+initializationDate+"\n"+"Collection size: "+clients.get(port).size();
        sender(so,IP,port);
    }

    /**
     * method close
     * stops program execution
     * and saves a state of LinkedList in file
     * file should be format XML
     * elements that will appeared it file will have format json
     * method can be cosed from terminal by this way: close
     * @throws IOException
     */
    /*private void close() throws IOException {
        if(clients.get(port).size() != 0) {
            String outputFile = "Client"+port+".xml";
            BufferedWriter pw = new BufferedWriter(new FileWriter(outputFile));
            pw.write("<?xml version= \"1.0\"encoding=\"utf-8\"?>\n");
            pw.write("<Animal>\n");
            clients.get(port).stream().forEach((x) -> {
                try {
                    pw.write("<Animal name=" + "\""+x.getName()+"\"" + " size=" +"\""+x.getSize()+"\"" +
                            " location=" + "\""+x.getLocation()+"\"" + "/>\n");
                }catch (IOException writeEx){ System.err.println("Output EXCEPTION during writing"); }
            });
            pw.write("<Animal/>");
            pw.close();
        }
    }*/
    private void close() throws IOException {
        String outputFile = "Client"+port+".xml";
        BufferedWriter pw = new BufferedWriter(new FileWriter(outputFile));
        pw.write("<?xml version= \"1.0\"encoding=\"utf-8\"?>\n");
        pw.write("<Animal>\n");
        clients.get(port).stream().forEach((x) -> {
            try {
                pw.write("<Animal name=" + "\""+x.getName()+"\"" + " size=" +"\""+x.getSize()+"\"" +
                        " location=" + "\""+x.getLocation()+"\"" + "/>\n");
            }catch (IOException writeEx){ System.err.println("Output EXCEPTION during writing"); }
        });
        pw.write("<Animal/>");
        pw.close();
    }

    public void myRemove(String element){
        Animal animal = null;
        try {
            animal = egson.fromJson(element, Animal.class);
        }catch (com.google.gson.JsonSyntaxException ex) {
            new Thread(()->sender("The strings describing the objects must be in the form of json!",IP,port)).start();
        }
        if (clients.get(port).contains(animal)){
            clients.get(port).remove(animal);
        }else {
            new Thread(()->sender("this element is not in collection!",IP,port)).start();
        }
    }


    /**
     * method addIfMax
     * add it's parameter at LinkedList of Animal
     * if it's value exceeds the value of the largest element of this collection
     * elements format must be json
     * can be cosed from terminal by this way: addIfMax
     * @param element
     */
    private void addIfMax(String element,InetAddress IP, int port){
        try {
            Animal animal = egson.fromJson(element, Animal.class);
            if (animal.getName() != null && animal.getSize() > 0 && animal.getLocation() > 0.0) {
                List<Animal> list = new ArrayList<>(clients.get(port));
                list.add(animal);
                Collections.sort(list);
                if (animal.equals(list.get(list.size() - 1))) {
                    clients.replace(port, clients.get(port), new LinkedBlockingDeque<>(list));
                }
            } else
                sender("Element's value is incorrect! Example of value:" + "\n" +
                        "{name:string value,size:integer value,location:double value}", IP, port);
        }catch (com.google.gson.JsonSyntaxException ex) {
            new Thread(()->sender("The strings describing the objects must be in the form of json!",IP,port)).start();
        }
    }

    /**
     * method myAdd
     * add it's parameter at LinkedList of Animal
     * elements format must be json
     * can be cosed from terminal by this way: add {element}
     * @param element
     */
    private void myAdd(String element,InetAddress IP,int port) {
        try {
            Animal animal = egson.fromJson(element, Animal.class);
            if (animal.getName() != null) {
                LinkedBlockingDeque<Animal> lis = new LinkedBlockingDeque<>(clients.get(port));
                lis.add(animal);
                clients.replace(port, clients.get(port), lis);
            } else
                sender("Enter Animal's name, pleas", IP, port);
        }catch (com.google.gson.JsonSyntaxException ex) {
            new Thread(()->sender("The strings describing the objects must be in the form of json!",IP,port)).start();
        }
    }
    synchronized private void myImport(DatagramPacket rec) {
        try {
            BufferedWriter buwr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Client" + port + ".xml")));
            while (true) {
                socket.receive(rec);
                byte[] in = rec.getData();
                String ins = new String(in, 0, rec.getLength());
                if (ins.equals("stop") || ins.equals("Exception")) break;
                buwr.write(ins);
                buwr.newLine();
            }
            buwr.close();
            flag_forImport = true;
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    synchronized private void sender(String str, InetAddress IP, int port){
        try {
            byte[]  nb = str.getBytes();
            DatagramPacket out = new DatagramPacket(nb, nb.length, IP, port);
            socket.send(out);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
