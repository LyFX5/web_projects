public class LunarApple extends Apple implements BeLunar {

    public LunarApple(Colour colour, int size) {
        super(colour, size);
    }

    @Override
    public void makeHungry(Earthling owner) {
        if(owner.getName().equals("Neznaika")){
            owner.getHungry();
        }
    }
}
