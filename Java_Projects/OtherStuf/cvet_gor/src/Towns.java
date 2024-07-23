import java.util.*;
public enum Towns {
    CVETOCHI_GOROD {
        public String kol_vo_cvetov = "Очень много цветов";
        String name = "Цветочный город";
        @Override
        public void builder() {
            super.builder();
            ArrayList<Home> Ulica_kolokolchikov = super.getPlaces().get(5);
            Domik_Znaiki = Ulica_kolokolchikov.get(3);
            Domik_Znaiki.setOwner(Znaika);
            Domik_Znaiki.setKey(1);
            Znaika.setHomeKey(1);
            Domik_Znaiki.setStreet("колокольчиков");
        }
        @Override
        public String toString(){
            String domov = String.valueOf(street.size());
            String ulic = String.valueOf(places.size());
            return (name + "\n" + "здесь " + ulic + " улиц " + "\n" + "и " + domov + " домов");
        }
    },

    ZELIONI_GOROD {
        String name = "Зелёный город";
        public void podvergat_opastnosti() {  }


        @Override
        public String toString(){
            String domov = String.valueOf(street.size());
            String ulic = String.valueOf(places.size());
            return (name + "\n" + "здесь " + ulic + " улиц " + "\n" + "и " + domov + " домов");
        }

        @Override
        public void builder() {
            super.builder();
        }
    };
    Towns(){
        this.builder();
        setNews();
    }
    public ArrayList<String> news = new ArrayList();

    public void setNews() {
                news.add("Вернулись Знайка и его друзья");
    }

    public Malish_putesh_nik Znaika = new Malish_putesh_nik("Знайка","хорошее");
    public ArrayList<Malish_putesh_nik> Druzia_Znaiki = new ArrayList();
    public void setDruzia_Znaiki(){
        Druzia_Znaiki.add(0,new Malish_putesh_nik("Пончик","хорошее"));
        Druzia_Znaiki.add(1,new Malish_putesh_nik("Незнайка","хорошее"));
        Druzia_Znaiki.add(2,new Malish_putesh_nik("ещё кто то","плохое"));
    }
    protected Home Domik_Znaiki;
    protected Date time = Nature.setTime();
    protected String pogoda = Nature.setWeather();

    public String getPogoda() {
        return pogoda;
    }

    public Date gettime() {
        return time;
    }

    protected ArrayList<Home> street = new ArrayList();
    protected ArrayList<ArrayList<Home>> places = new ArrayList();
    public ArrayList<Citizen> jiteli = new ArrayList();

    public ArrayList<String> getNews() {
        return news;
    }

    public ArrayList<Home> getStreet() {
        return street;
    }

    public ArrayList<ArrayList<Home>> getPlaces() {
        return places;
    }

    public void builder() {
        int i[] = {0,1,2,3,4,5,6,7,8,9};
        for(int j :i){
            for (int k : i){
                street.add(k,new Home(k));
                if( k % 2 == 0) {
                    jiteli.add(new Malish());
                }else{jiteli.add(new Malishka());}
            }
            places.add(j,street);

        }
    }

}
