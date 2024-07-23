public class Main {
    public static void main(String[] args){
        Database.setUP();
        Server server = new Server();
        server.start(args);
    }
}
