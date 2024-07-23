public class Server {
    public static void main(String[] args) {
        Application app = new Application();
        if(args.length == 0)
            System.err.println("Enter port!");
        else
            app.application(args[0]);
    }
}
