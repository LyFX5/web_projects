public class LunarPear extends Fruit implements BeLunar {

    public LunarPear(Colour colour, int size) {
        super(colour, size);
    }

    @Override
    public void makeHungry(Earthling owner) {
        if(owner.getName().equals("Neznaika")){
            owner.getHungry();
        }
    }
}
