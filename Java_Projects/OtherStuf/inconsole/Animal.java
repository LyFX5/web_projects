
import java.io.Serializable;
import java.util.Date;

public class Animal extends Essence implements Serializable, Comparable<Animal>{

    @Override
    public int compareTo(Animal a) {
        return this.name.compareTo(a.name);
    }
    private int age;
    private String name;
    private int size;
    private int power;
    private Date dateOfAppearing;
    private double location;

    public double getLocation(){
        return location;
    }

    public void setLocation(double location){
        this.location = location;
    }

    public Date getDateOfAppearing(){
        return dateOfAppearing;
    }

    public Animal(String name, int size, double location){
        this.name = name;
        this.size = size;
        this.location = location;
        dateOfAppearing = new Date();
    }
    public Animal(){dateOfAppearing = new Date();}

    public String firstUpperCase(String s){
        return s.substring(0,1).toUpperCase()+s.substring(1);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Date getDate(){return dateOfAppearing;}

    @Override
    public boolean equals(Object obj) {
        Animal animal = (Animal) obj;
        boolean flag = false;
        if (obj.getClass() != this.getClass()) {
            return false;
        } else if (this.name.equals(animal.name)) {
            flag = true;
        }
        return flag;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime*result + age;
        result = prime*result + power;
        result = prime*result + Integer.parseInt(name);
        result = prime*result + size;
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
