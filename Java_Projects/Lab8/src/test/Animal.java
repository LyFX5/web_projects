package test;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.util.Date;

public class Animal extends Essence implements Serializable, Comparable<Animal>{

    @Override
    public int compareTo(Animal a) {
        return this.name.compareTo(a.name);
    }

    private SimpleStringProperty owner;
    private SimpleStringProperty names;
    private String name;
    private SimpleStringProperty size;
    private SimpleStringProperty location;
    private Date dateOfAppearing;


    public Animal(String owner, String name, String size, String location){
        this.name = name;
        this.owner = new SimpleStringProperty(owner);
        this.names = new SimpleStringProperty(name);
        this.size = new SimpleStringProperty(size);
        this.location = new SimpleStringProperty(location);
        dateOfAppearing = new Date();
    }
    public Animal(){dateOfAppearing = new Date();

    }

    public String firstUpperCase(String s){
        return s.substring(0,1).toUpperCase()+s.substring(1);
    }

    public String getSize() {
        return size.get();
    }

    public void setSize(String size){
        this.size.set(size);
    }
    public String getLocation(){
        return location.get();
    }

    public void setLocation(String location){this.location.set(location);}

    public String getOwner() {
        return owner.get();
    }

    public void setOwner(String owner){
        this.owner.set(owner);
    }

    public String getNames() {
        return names.get();
    }

    public void setNames(String names) {
        this.names.set(names);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Animal &&
                ((Animal) obj).name.equals(name);
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime*result + Integer.parseInt(name);
        return result;
    }

    @Override
    public String toString() {
        return firstUpperCase(name)+"{"+
                "name: "+name+
                ",location: "+location+
                ",size: "+size+
                ",dateOfAppearing: "+dateOfAppearing+
                "}";
    }
}
