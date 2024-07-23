public class LunarRaspberry extends Raspberry implements BeLunar{

    public LunarRaspberry(Colour colour, int size) {
        super(colour, size);
    }

    @Override
    public void makeHungry(Earthling owner) {
        if(owner.getName().equals("Neznaika")){
            owner.getHungry();
        }
    }
}
