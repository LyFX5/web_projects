package test;
import com.google.gson.Gson;
import javafx.beans.property.SimpleStringProperty;

public class Test {
    public static void main(String[] args){
        Gson gson = new Gson();
        /*SimpleStringProperty owner = new SimpleStringProperty(new String("xuy"));
        SimpleStringProperty names = new SimpleStringProperty("giv");
        String name;
        SimpleStringProperty size = new SimpleStringProperty("5");
        SimpleStringProperty location = new SimpleStringProperty("5");*/
        String a = gson.toJson(new Animal("edik","giv","5","8"));
        Animal am = gson.fromJson(a,Animal.class);
        System.out.println(a);
        System.out.println(am);
    }
}
