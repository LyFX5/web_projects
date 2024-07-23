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
        Fiks.setBlazer(new Blazer(Colour.ORANGE));
        Fiks.setCap(new Cap(Colour.ORANGE));
        Fiks.setPants(new Pants(Colour.BLACK));
        Fiks.setSandals(new Sandals(Colour.GRAY));
        Fiks.setAtribut("broom");
        Fiks.speak("I have the " + Fiks.getAtribut());
    }
}
