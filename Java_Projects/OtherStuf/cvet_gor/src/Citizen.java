import java.io.Serializable;
public abstract class Citizen implements Talk_about_this,Comparable<Citizen>, Serializable {
    @Override
    public int compareTo(Citizen citizen) {
        return this.name.compareTo(citizen.getName());
    }

    protected int HomeKey;

    public int getHomeKey() {
        return HomeKey;
    }

    public void setHomeKey(int key) {
        HomeKey = key;
    }

    public String name;

    public String getName() {
        return this.name;
    }

    protected String nastroenie;

    public Citizen(String name, String nastroenie) {
        this.name = name;
        this.nastroenie = nastroenie;
    }

    public Citizen() {
        name = "Citizen";
        nastroenie = "Good";
    }

    public String speek(String word) {
        String otvet = "Привет";
        if (word == "привет") {
            otvet = "как дела?";
        }
        return otvet;
    }

    public void walk() {
        //==========
    }

    public static void listen() {
        System.out.println("Расскажите что нибудь " + "\n");
    }

    public void look() {
        //===================
    }

    public static Home sobiratsa_vmeste(Home mesto) {
        System.out.println("");
        return mesto;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Integer.parseInt(name);
        result = prime * result + Integer.parseInt(nastroenie);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        /*if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Citizen other = (Citizen) obj;
        if (this.hashCode() != other.hashCode())
            return false;
        return true;*/
        Citizen citizen = (Citizen)obj;
        boolean check = false;
        if( (this.getClass() == citizen.getClass()) && (this.name == citizen.name)){ check = true; }
        return check;
    }

}

