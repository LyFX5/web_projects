public class Malish extends Citizen {
    public Malish(String name,String nastrenie){
        super(name,nastrenie);
    }
    public Malish(){
        super();
    }

    @Override
    public String to_be_discussed() {
        return (toString());
    }
}
