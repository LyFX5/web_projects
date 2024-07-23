public class Main {
    public static void main(String[] args) {
        Earthling Neznaika = new Earthling("Neznaika", Colour.BROWN);
        Neznaika.speak("Hello I am " + Neznaika.getName());
        LunarApple apple = new LunarApple(Colour.RED, 5);
        LunarPear pear = new LunarPear(Colour.YELLOW, 7);
        apple.makeHungry(Neznaika);
        pear.makeHungry(Neznaika);
        Neznaika.walk();
        Berry[] berries = new Berry[10];
        for (int i = 0; i < 10; i++){
            berries[i] = new Berry(Colour.RED,1);
        }
        Neznaika.see(berries[0]);
        if (Neznaika.see(berries[0]).equals("Lunar raspberry")){
            Neznaika.speak("This is lunar raspberry !!");
            for (int i = 0; i < 10; i++){
                berries[i] = new LunarRaspberry(berries[i].getColour(),berries[i].getSize());
            }
        }

        Neznaika.getHungry();

        Lunarian Fiks = new Lunarian("Fiks",Colour.BLACK);
        Fiks.speak("I am " + Fiks.getName() + ". I see " + Fiks.see(Neznaika));
        Fiks.setBlazer(new Clothes.Blazer(Colour.ORANGE));
        Fiks.setCap(new Clothes.Cap(Colour.ORANGE));
        Fiks.setPants(new Clothes.Pants(Colour.BLACK));
        Fiks.setSandals(new Clothes.Sandals(Colour.GRAY));
        Fiks.setAtribut("broom");
        Fiks.speak("I have the " + Fiks.getAtribut());

        Neznaika.getIntoTruble();
        try {
            Neznaika.walk();
        }catch (CantRunException c){
            System.out.println(c.getMessage());
        }
        Fiks.walk();
        Neznaika.speak("I see " + Neznaika.see(new LunarTomato(Colour.RED,2)));
        Neznaika.speak("I see " + Neznaika.see(new LunarСucumber(Colour.GREEN,1)));

        Lunarian[] lunarians = new Lunarian[3];

        for(int i = 0; i < 3; i++){
            lunarians[i] = new Lunarian(String.valueOf(i),Colour.BLACK);
            lunarians[i].water_the_beds();
        }

        int few = 5;

        Lunarian[] lunarians2 = new Lunarian[few];
        for(int i = 0; i < few; i++){
            lunarians2[i] = new Lunarian(String.valueOf(i),Colour.BLACK);
            try {
                lunarians2[i].peekUpFood(new LunarRaspberry(Colour.RED,1));
            } catch (NotRipeException e) {
                e.printStackTrace();
            }
        }

        String shorty = lunarians2[3].see(Fiks);
        lunarians2[3].speak("Эй, "  + shorty + ", опять грабителя изловил?");
    }
}
