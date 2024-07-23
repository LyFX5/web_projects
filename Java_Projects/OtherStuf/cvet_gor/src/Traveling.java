import java.util.ArrayList;

public class Traveling implements Talk_about_this {
    private String uvlekatelnost;
    public Traveling(String uvlekatelnost){
        this.uvlekatelnost = uvlekatelnost;
    }

    public Traveling(Citizen x, ArrayList<Malish_putesh_nik> y){
        uvlekatelnost = "увлекательное путешествие  " + x.toString()+"(и)" + "  и  " +"его друзей";
    }

    public Traveling(Citizen x,Citizen y){
        uvlekatelnost = "увлекательное путешествие  " + x.toString()+"(и)" + "  и  " + y.toString()+"(ей)";
    }

    public Traveling(Citizen x){
        uvlekatelnost = "увлекательное путешествие  " + x.toString();
    }
    public void start(){}
    public void finish() {
        System.out.println("На этом  " + uvlekatelnost + "  закончилось");
    }

    @Override
    public int hashCode(){
        return Integer.parseInt(uvlekatelnost);
    }

    @Override
    public String toString() {
        return uvlekatelnost + "путешествие";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Traveling other = (Traveling) obj;
        if (this.hashCode() != other.hashCode())
            return false;
        return true;
    }

    @Override
    public String to_be_discussed() {
        return (uvlekatelnost +"\n"+ "в " + Towns.ZELIONI_GOROD.toString() +"\n"+ Towns.ZELIONI_GOROD.pogoda);
    }
}

