public class Malish_putesh_nik extends Malish implements Traveler {
    public Malish_putesh_nik(String name, String nastroenie){
        super(name,nastroenie);
    }
    public Malish_putesh_nik(){
            super();
    }
    @Override
    public void travel() {

    }

    @Override
    public boolean be_brave() {
        int i = (int) Math.random()*100;
        boolean check = false;
        if(nastroenie == "хорошее" & (i > 0 & i < 91)){
            check = true;
        }
        return check;
    }

    @Override
    public boolean be_interested_in() {
        boolean check = false;
        int i = (int) Math.random()*100;
        if(name == "Знайка" || (nastroenie == "хорошее" & (i > 0 & i < 91)) ){
            check = true;
        }
        return check;
    }

    @Override
    public String tell_about() {
            Traveling memory = new Traveling("увлекательное путешествие");
            return ("Я расскажу про " + memory.to_be_discussed());
    }


}
