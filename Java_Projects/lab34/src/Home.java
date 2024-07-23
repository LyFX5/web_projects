import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Home implements Talk_about_this,Comparable<Home>, Serializable {

    public Home(){
        dateOfAppearing = new Date();
    }

    @Override
    public int compareTo(Home otherHome) {
        return (int)Math.signum((double)(this.number - otherHome.number));
    }

    private String street;
    private int number;
    transient private Citizen owner;
    private int key;
    private String ownerName;
    private Date dateOfAppearing;

    public Date getDateOfAppearing(){
        return dateOfAppearing;
    }

    public int getKey(){
        return key;
    }
    public void setKey(int key){
        this.key = key;
    }

    public boolean openTheDoor(int key) throws NotOwnerException{
        if(this.key != key){throw new NotOwnerException();}
        return (this.key == key);
    }

    public Home(int number){
        this.number = number;
        this.owner = new Malish();
        ownerName  = owner.name;
    }
    public int getNumber(){
        return number;
    }

    public void setOwner(Citizen owner) {
        this.owner =  owner;
    }

    public Citizen  getOwner() {
        return owner;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        String street = this.street;
        if(street == null){street = "empty";}
        return street;
    }

    public boolean anybody_home(){
        boolean check = false;
        if(number != 0){
            check = true;
        }
        return check;
    }

    @Override
    public boolean equals(Object obj) {
        Home other = (Home) obj;
        boolean check = false;
        if (obj.getClass() != this.getClass()) {
            return false;
        } else if (this.getNumber() == other.getNumber()) {
            check = true;
        }
        return check;
    }

    @Override
    public int hashCode(){
        return number - Integer.parseInt(owner.toString());
    }

    @Override
    public String toString(){
        String s = "";
        if(street != null){s = "Street's name " + street;}
        String ownerName = "citizen";
        if(this.owner != null){ownerName = this.owner.toString();}
        return ( s + " Home number " + number + " home owner is " + ownerName + ". Date of appearing is " + dateOfAppearing +"\n");
    }

    @Override
    public String to_be_discussed() {
        return (toString());
    }

}
