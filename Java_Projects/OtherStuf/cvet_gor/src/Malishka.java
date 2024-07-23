public class Malishka extends Citizen {
    int l;
    public Malishka(String n,String nas, int l){
        super(n,nas);
        this.l = l;
    }
    public Malishka(){
        super();
    }
    public boolean likes_Znaika(){
        boolean check = false;
        if(l == 2){
            check = true;
        }
        return check;
    }

    @Override
    public String to_be_discussed() {
        if(likes_Znaika() == true){
            return ("likes Znaik");
        }else{
            return ("does not likes Znaik");
        }
    }
}
