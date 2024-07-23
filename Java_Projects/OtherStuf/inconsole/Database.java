import java.sql.*;
import java.util.LinkedList;
import java.io.File;
import java.util.concurrent.LinkedBlockingDeque;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


public class Database {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/labdb";
    static final String USER = "sonia";
    static final String PASS = "temqa";
    static Connection connection = null;
    static Gson gson = new Gson();

    public static void setUP(){

        System.out.println("Testing connection to PostgreSQL JDBC");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");

        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }
    }

    public static int createTable(){
        int rs = 0;
        try {
            Statement stmt = connection.createStatement();
            String str = "CREATE TABLE usersnew(login VARCHAR, password chkpass);";
            rs = stmt.executeUpdate(str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static void putinUser(String login, String password){
        try {
            Statement stmt = connection.createStatement();
            String str = "INSERT INTO users(login, password) VALUES" +"('"+login+"', '"+password+"');";
            stmt.executeUpdate(str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void putoutUser(){}

    public static boolean containsUser(String login, String password){
        ResultSet rs = null;
        boolean check = false;
        try {
            Statement stmt = connection.createStatement();
            String str = "SELECT login, password FROM users WHERE login = '" + login + "' and password = '" + password + "'";
            rs = stmt.executeQuery(str);
            if (rs.next()){
                check = true;
            }else {check = false;}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    public static boolean containsLogin(String login){
        ResultSet rs = null;
        boolean check = false;
        try {
            Statement stmt = connection.createStatement();
            String str = "SELECT login FROM users WHERE login = '" + login + "'";
            rs = stmt.executeQuery(str);
            if (rs.next()){
                check = true;
            }else {check = false;}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    public static void putinAnimal(String login, Animal animal){
        String anim = gson.toJson(animal);
        String str = "INSERT INTO animals(owner, animal) VALUES" +"('"+login+"', '"+anim+"');";
        try{
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(str);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void putoutAnimal(String login){
        String sqlStr = "DELETE FROM animals WHERE owner = '" + login + "'";
        try{
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sqlStr); // Выполняем запрос
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static LinkedBlockingDeque<Animal> getAnimalsOfUser(String login){
        LinkedBlockingDeque<Animal> animals = new LinkedBlockingDeque<>();
        String sqlStr = "SELECT animal FROM animals WHERE owner = '" + login + "'";
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStr); // Выполняем запрос
            while (rs.next()) {
                String anim = rs.getString("animal");
                Animal animal = gson.fromJson(anim,Animal.class);
                animals.add(animal);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        putoutAnimal(login);
        return animals;
    }

    public static void containsAnimal(){}

    public static void showUsers(){
        try{
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT login, password FROM users"); // Выполняем запрос
            while (rs.next()) {
                String log = rs.getString("login");  // получаем результат
                String pas = rs.getString("password");
                System.out.println(log + " " + pas);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static LinkedBlockingDeque<String> showAnimals(){
        LinkedBlockingDeque<String> strings = new LinkedBlockingDeque<>();
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT owner, animal FROM animals"); // Выполняем запрос
            while (rs.next()) {
                String owner = rs.getString("owner");  // получаем результат
                String animal = rs.getString("animal");
                strings.add(owner+" "+animal);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return strings;
    }
}
